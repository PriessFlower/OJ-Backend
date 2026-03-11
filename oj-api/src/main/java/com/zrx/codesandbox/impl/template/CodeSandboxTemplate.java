package com.zrx.codesandbox.impl.template;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.async.ResultCallback;
import com.github.dockerjava.api.command.CreateContainerCmd;
import com.github.dockerjava.api.command.CreateContainerResponse;
import com.github.dockerjava.api.command.StatsCmd;
import com.github.dockerjava.api.exception.NotModifiedException;
import com.github.dockerjava.api.model.Bind;
import com.github.dockerjava.api.model.HostConfig;
import com.github.dockerjava.api.model.Statistics;
import com.github.dockerjava.api.model.Volume;
import com.github.dockerjava.core.DefaultDockerClientConfig;
import com.github.dockerjava.core.DockerClientBuilder;
import com.github.dockerjava.core.DockerClientConfig;
import com.github.dockerjava.httpclient5.ApacheDockerHttpClient;
import com.github.dockerjava.transport.DockerHttpClient;
import com.zrx.codesandbox.CodeSandBox;
import com.zrx.codesandbox.enums.QuestionExecuteResultEnum;
import com.zrx.codesandbox.model.ExecuteCodeRequest;
import com.zrx.codesandbox.model.ExecuteCodeResponse;
import com.zrx.codesandbox.model.ExecuteMessage;
import com.zrx.codesandbox.model.JudgeInfo;
import com.zrx.codesandbox.threads.TimeManagerThread;
import com.zrx.codesandbox.util.CgroupsMemoryUtils;
import com.zrx.codesandbox.util.LanguageCmdUtils;
import com.zrx.codesandbox.util.ProcessUtils;
import lombok.extern.slf4j.Slf4j;

import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
public abstract class CodeSandboxTemplate implements CodeSandBox {

	public static final String GLOBAL_CODE_DIR_NAME = "tmpCode";

	protected String GLOBAL_CODE_FILE_NAME;

	public static final String SECURITY_MANAGER_CLASSNAME;

	public static final int TIME_OUT = 5000;

	public static final String SECURITY_MANAGER_PATH;

	private static String globalCodePathName;

	private String userCodeParentPath;

	private String userCodePath;

	protected String language;

	protected String containerId;

	protected DockerClient dockerClient;

	protected String ImageName;

	protected String volumnName;

	protected volatile Long memory;

	static {
		String property = System.getProperty("user.dir");
		SECURITY_MANAGER_PATH = property + File.separator + "src/main/resources/security";
		SECURITY_MANAGER_CLASSNAME = "OjSecurityManager";
	}

	public ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest) {
		language = executeCodeRequest.getLanguage();
		List<String> inputList = executeCodeRequest.getInputList();
		String code = executeCodeRequest.getCode();
		File userCodeFile = saveCodeFile(code);

		ExecuteMessage compileExecuteMessage = null;
		List<ExecuteMessage> runExecuteMessageList = null;
        StatsCmd statsCmd = null;

        //需要用finally清理资源！！！
		try {
			compileExecuteMessage = compileFile(userCodeFile);
			startDocker();
			createContainer();
			runContainer();
			statsCmd = calculateMemory();
//			while (memory < 0) 不适用忙等待，cpu占用率太高
//				;
            int waitCount = 0;
            while(memory < 0 && waitCount < 30){//为了确保拿到docker监控的数据，需要等待一会
                Thread.sleep(100); //每次休眠100ms，让出CPU
                waitCount++;
            }
			runExecuteMessageList = runFile(inputList);

			// cgroups 读到峰值内存则覆盖 Stats 的值（Linux 生产环境更准确）
			long cgroupsMemory = CgroupsMemoryUtils.getMaxMemoryUsage(containerId);
			if (cgroupsMemory > 0) {
				log.debug("从 cgroups 读取到峰值内存: {} bytes", cgroupsMemory);
				for (ExecuteMessage msg : runExecuteMessageList) {
					msg.setMemory(cgroupsMemory);
				}
			}

			stopContainer();
			removeContainer(statsCmd);
		} catch (Exception e) {
            log.error("代码沙箱执行过程中发生致命异常:", e);
			return getErrorResponse(e);
		} finally {
            try {
                if (containerId != null) {
                    stopContainer();
                    if (statsCmd != null) {
                        removeContainer(statsCmd);
                    } else {
                        removeContainer(); // 调用那个不带参数的 removeContainer
                    }
                }
            } catch (Exception e) {
                log.error("清理 Docker 容器失败, containerId={}", containerId, e);
            }
        }

		ExecuteCodeResponse executeCodeResponse = getOutputResponse(runExecuteMessageList);

		boolean isDeleteFile = clearFile(userCodeFile);

		if (!isDeleteFile) {
			log.error("delete file error,userCodeFilePath={}", userCodeFile.getAbsolutePath());
		}
		return executeCodeResponse;
	}

	public void startDocker() {
        // 1. 获取当前操作系统名称并转为小写
        String osName = System.getProperty("os.name").toLowerCase();

        // 2. 动态判断并设置 Docker Host
        String dockerHost = "unix:///var/run/docker.sock"; // 默认给 Linux/Mac 使用的 Unix Socket
        if (osName.contains("win")) {
            dockerHost = "npipe:////./pipe/docker_engine"; // 如果是 Windows，覆盖为管道连接
        }

        // 3. 将动态获取的 dockerHost 传入配置
        DockerClientConfig config = DefaultDockerClientConfig.createDefaultConfigBuilder()
                .withDockerHost(dockerHost)
                // 禁用TLS验证（测试环境）
                .withDockerTlsVerify(false)
                .build();

        DockerHttpClient httpClient = new ApacheDockerHttpClient.Builder()
                .dockerHost(config.getDockerHost())
                .sslConfig(config.getSSLConfig())
                .build();

        dockerClient = DockerClientBuilder.getInstance(config)
                .withDockerHttpClient(httpClient)
                .build();
	}

	public void createContainer() {
		CreateContainerCmd containerCmd = dockerClient.createContainerCmd(ImageName);
		HostConfig hostConfig = new HostConfig();
		hostConfig.setBinds(new Bind(userCodeParentPath, new Volume(volumnName)));
		hostConfig.withCpuCount(1L);
		hostConfig.withMemorySwap(0L);
		hostConfig.withMemory(256 * 1000 * 1000L);
		CreateContainerResponse createContainerResponse = containerCmd.withHostConfig(hostConfig)
			.withNetworkDisabled(true)
			.withAttachStderr(true)
			.withAttachStdin(true)
			.withAttachStdout(true)
			.withTty(true)
            .withCmd("tail", "-f", "/dev/null")
			.exec();
		String responseId = createContainerResponse.getId();
		containerId = responseId;
	}

	public void runContainer() {
		dockerClient.startContainerCmd(containerId).exec();
	}

	public StatsCmd calculateMemory() {
		memory = Long.MIN_VALUE;
		StatsCmd statsCmd = dockerClient.statsCmd(containerId);
        ResultCallback<Statistics> statisticsResultCallback = new ResultCallback<Statistics>() {
            @Override
            public void onStart(Closeable closeable) {
            }

            @Override
            public void onNext(Statistics statistics) {
                System.out.println("内存占用：" + statistics.getMemoryStats().getUsage());
                memory = Math.max(statistics.getMemoryStats().getUsage(), memory);
            }

            @Override
            public void close() throws IOException {
            }

            @Override
            public void onError(Throwable throwable) {
            }

            @Override
            public void onComplete() {
            }
        };
		statsCmd.exec(statisticsResultCallback);
		return statsCmd;
	}

    public void stopContainer() {
        try {
            if (containerId != null) {
                dockerClient.stopContainerCmd(containerId).exec();
            }
        } catch (NotModifiedException e) {} catch (Exception e) {
            log.error("停止容器失败, containerId: {}", containerId, e);
        }
    }

	public void removeContainer(StatsCmd statsCmd) {
		try {
			Runtime.getRuntime().exec(String.format("docker rm -f %s", containerId));
			// dockerClient.removeContainerCmd(containerId).exec();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
		statsCmd.close();
	}

	public void removeContainer() {
		try {
			// Process exec = Runtime.getRuntime().exec(String.format("docker rm -f %s",
			// containerId));
			dockerClient.removeContainerCmd(containerId).exec();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public File saveCodeFile(String code) {
		String userDir = System.getProperty("user.dir");
		globalCodePathName = userDir + File.separator + GLOBAL_CODE_DIR_NAME;
		if (!FileUtil.exist(globalCodePathName)) {
			FileUtil.mkdir(globalCodePathName);
		}
		userCodeParentPath = globalCodePathName + File.separator + UUID.randomUUID();
		userCodePath = userCodeParentPath + File.separator + GLOBAL_CODE_FILE_NAME;
		File userCodeFile = FileUtil.writeString(code, userCodePath, StandardCharsets.UTF_8);
		return userCodeFile;
	}

	public ExecuteMessage compileFile(File userCodeFile) throws Exception {
		String compileCmd = LanguageCmdUtils.getCompileCMD(userCodeFile.getAbsolutePath(), userCodeParentPath,
				language);
		if (compileCmd == null)
			return null;
		try {
			Process compileProcess = Runtime.getRuntime().exec(compileCmd);
			ExecuteMessage executeMessage = ProcessUtils.compileCode(compileProcess);
			if (executeMessage.getExitValue() != 0) {
				log.error("编译失败:" + executeMessage.getErrorMessage());
				throw new RuntimeException("编译错误：\n错误的原因是：" + executeMessage.getErrorMessage());
			}
			return executeMessage;
		}
		catch (Exception e) {
			throw e;
		}
	}

	public List<ExecuteMessage> runFile(List<String> inputList) throws IOException {
		long startMemory = memory;
		System.out.println("开始的内存---" + memory);
		List<ExecuteMessage> executeMessagesList = new ArrayList<>();
		if (inputList == null) {
			Runtime runtime = Runtime.getRuntime();
			String runCmd = LanguageCmdUtils.getRunCMD(volumnName, language, containerId);
			System.out.println(runCmd);
			Process runProcess = runtime.exec(runCmd);
			ExecuteMessage executeMessage = null;
			TimeManagerThread timeManagerThread = new TimeManagerThread(TIME_OUT);
			timeManagerThread.setProcess(runProcess);
			timeManagerThread.start();
			executeMessage = ProcessUtils.runCode(runProcess, null);
			executeMessage.setMemory(memory);
			executeMessagesList.add(executeMessage);
			if (timeManagerThread.IsTime_out()) {
				throw new RuntimeException("超时异常");
			}
			timeManagerThread.interrupt();
		}
		else {
			for (String inputArgs : inputList) {
				try {
					Runtime runtime = Runtime.getRuntime();
					String runCmd = LanguageCmdUtils.getRunCMD(volumnName, language, containerId);
					System.out.println(runCmd);
					Process runProcess = runtime.exec(runCmd);
					ExecuteMessage executeMessage = null;
					TimeManagerThread timeManagerThread = new TimeManagerThread(TIME_OUT);
					timeManagerThread.setProcess(runProcess);
					timeManagerThread.start();
					executeMessage = ProcessUtils.runCode(runProcess, inputArgs);
					System.out.println("结束的进程-----" + memory);
					executeMessage.setMemory(memory);
					executeMessagesList.add(executeMessage);
					if (timeManagerThread.IsTime_out()) {
						stopContainer();
						removeContainer();
						throw new RuntimeException("超时异常");
					}
					timeManagerThread.interrupt();
				}
				catch (Exception e) {
					throw e;
				}
			}
		}
		return executeMessagesList;
	}

	public ExecuteCodeResponse getOutputResponse(List<ExecuteMessage> executeMessagesList) {
		ExecuteCodeResponse executeCodeResponse = new ExecuteCodeResponse();
		JudgeInfo judgeResult = new JudgeInfo();
		List<String> outputList = new ArrayList<>();
		long maxTime = 0L;
		long maxMemory = 0L;
		for (ExecuteMessage executeMessage : executeMessagesList) {
			String errorMessageStr = executeMessage.getErrorMessage();
			if (StrUtil.isNotBlank(errorMessageStr)) {

                log.error("【Docker容器运行真实报错】: {}", errorMessageStr);

				executeCodeResponse.setMessage(QuestionExecuteResultEnum.RunException.getText());
				executeCodeResponse.setStatus(QuestionExecuteResultEnum.RunException.getValue());
				break;
			}
			Long time = executeMessage.getTime();
			Long memory = executeMessage.getMemory();
			maxTime = Math.max(time, maxTime);
			maxMemory = Math.max(maxMemory, memory);
			outputList.add(executeMessage.getMessage());
		}
		if (outputList.size() == executeMessagesList.size()) {
			executeCodeResponse.setMessage(QuestionExecuteResultEnum.ExecuteSuccess.getText());
			executeCodeResponse.setStatus(2);
		}
		judgeResult.setMemory(maxMemory / 1024 / 8);
		judgeResult.setTime(maxTime);
		executeCodeResponse.setOuputList(outputList);
		executeCodeResponse.setJudgeInfo(judgeResult);
		return executeCodeResponse;
	}

	public boolean clearFile(File userCodeFile) {
		if (userCodeFile.getParentFile() != null) {
			boolean del = FileUtil.del(userCodeParentPath);
			System.out.println("消除:" + (del ? "成功" : "失败"));
			return del;
		}
		return true;
	}

	public ExecuteCodeResponse getErrorResponse(Throwable e) {
		ExecuteCodeResponse executeCodeResponse = new ExecuteCodeResponse();
		executeCodeResponse.setOuputList(new ArrayList<>());
		JudgeInfo judgeInfo = new JudgeInfo();
		judgeInfo.setMessage(e.getMessage());
		if (memory != null && memory > 0) {
			judgeInfo.setMemory(memory / 1024 / 8);
		}
		judgeInfo.setMessage(desensitizationStr(e.getMessage()));
        String errorMessage = e.getMessage() == null ? "" : e.getMessage();
        if (errorMessage.contains("编译")) {
            executeCodeResponse.setMessage(QuestionExecuteResultEnum.CompileFailure.getText());
            executeCodeResponse.setStatus(QuestionExecuteResultEnum.CompileFailure.getValue());
        }
        else if (errorMessage.contains("超时")) {
            executeCodeResponse.setMessage(QuestionExecuteResultEnum.OutOfTimeException.getText());
            executeCodeResponse.setStatus(QuestionExecuteResultEnum.OutOfTimeException.getValue());
        }
        else {
            executeCodeResponse.setMessage(QuestionExecuteResultEnum.RunException.getText());
            executeCodeResponse.setStatus(QuestionExecuteResultEnum.RunException.getValue());
        }
		executeCodeResponse.setJudgeInfo(new JudgeInfo());
		return executeCodeResponse;
	}

	public String desensitizationStr(String information) {
		if (information != null) {
			information = information.replace(userCodePath, "");
			information = information.replace(userCodeParentPath, "");
		}
		return information;
	}

}
