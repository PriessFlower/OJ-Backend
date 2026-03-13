package com.zt.codesandbox.util;

import com.zt.codesandbox.model.ExecuteMessage;
import lombok.extern.slf4j.Slf4j;

import java.io.*;

@Slf4j
public class ProcessUtils {

    public static ExecuteMessage compileCode(Process compileProcess) {
        ExecuteMessage executeMessage = new ExecuteMessage();
        try {
            // 先读取输出流（防止死锁）
            String message = readStream(compileProcess.getInputStream());
            String errorMessage = readStream(compileProcess.getErrorStream());

            int exitValue = compileProcess.waitFor();
            executeMessage.setExitValue(exitValue);

            if (exitValue == 0) {
                executeMessage.setMessage(message);
            } else {
                executeMessage.setMessage(message);
                executeMessage.setErrorMessage(errorMessage);
            }
        } catch (Exception e) {
            log.error("编译进程执行失败", e);
            executeMessage.setErrorMessage(e.getMessage());
        } finally {
            compileProcess.destroy();
        }
        return executeMessage;
    }

    public static ExecuteMessage runCode(Process runProcess, String args) {
        ExecuteMessage executeMessage = new ExecuteMessage();
        long start = System.currentTimeMillis();

        try {
            // 1. 如果有输入参数，先写入输入流并立刻关闭
            if (args != null) {
                try (OutputStream outputStream = runProcess.getOutputStream();
                     OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream)) {
                    outputStreamWriter.write(args + "\n");
                    outputStreamWriter.flush();
                }
            }

            // 2. 立即读取正常输出和错误输出（必须在 waitFor 之前读完，防止缓冲区满导致死锁）
            String message = readStream(runProcess.getInputStream());
            String errorMessage = readStream(runProcess.getErrorStream());

            // 3. 等待进程结束
            runProcess.waitFor();
            long end = System.currentTimeMillis();

            executeMessage.setTime(end - start);
            executeMessage.setMessage(message);
            executeMessage.setErrorMessage(errorMessage);

        } catch (Exception e) {
            log.error("运行进程执行失败", e);
            executeMessage.setErrorMessage(e.getMessage());
        } finally {
            runProcess.destroy();
        }
        return executeMessage;
    }

    /**
     * 提取的公共读取流方法
     */
    private static String readStream(InputStream inputStream) throws IOException {
        if (inputStream == null) {
            return "";
        }
        StringBuilder result = new StringBuilder();
        // try-with-resources 会自动关闭 BufferedReader 和 InputStream，无需在 finally 中手动处理
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = reader.readLine()) != null) {
                result.append(line);
            }
        }
        return result.toString();
    }


	private static void setTime(ExecuteMessage executeMessage, Process process) throws InterruptedException {
		long start = 0L;
		long end = 0L;
		if (process.isAlive()) {
			start = System.currentTimeMillis();
			process.waitFor();
			end = System.currentTimeMillis();
		}
		executeMessage.setTime((end - start));
	}

	private static void inputArgs(Process runProcess, String args, OutputStream outputStream,
			OutputStreamWriter outputStreamWriter) throws IOException {
		outputStream = runProcess.getOutputStream();

		outputStreamWriter = new OutputStreamWriter(outputStream);

		outputStreamWriter.write(args + "\n");
		outputStreamWriter.flush();
	}

	private static void setRunResult(Process runProcess, ExecuteMessage executeMessage, InputStream inputStream,
			BufferedReader bufferedReader, InputStream errorInputStream, BufferedReader errorBufferReader)
			throws IOException {
		inputStream = runProcess.getInputStream();
		bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
		StringBuilder runOutputStringBuilder = new StringBuilder();
		String runOutputLine;
		while ((runOutputLine = bufferedReader.readLine()) != null) {
			runOutputStringBuilder.append(runOutputLine);
		}
		executeMessage.setMessage(runOutputStringBuilder.toString());

		errorInputStream = runProcess.getErrorStream();
		bufferedReader = new BufferedReader(new InputStreamReader(errorInputStream));
		StringBuilder errorRunStringBuilder = new StringBuilder();
		String errorRunOutputLine;
		while ((errorRunOutputLine = bufferedReader.readLine()) != null) {
			errorRunStringBuilder.append(errorRunOutputLine);
		}
		executeMessage.setErrorMessage(errorRunStringBuilder.toString());
	}

}
