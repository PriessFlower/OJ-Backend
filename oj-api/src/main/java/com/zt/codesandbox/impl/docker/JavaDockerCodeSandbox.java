package com.zt.codesandbox.impl.docker;

import com.zt.codesandbox.impl.template.CodeSandboxTemplate;
import com.zt.codesandbox.model.ExecuteCodeRequest;
import com.zt.codesandbox.model.ExecuteCodeResponse;
import com.zt.codesandbox.model.ExecuteMessage;
import com.zt.codesandbox.util.LanguageCmdUtils;
import com.zt.codesandbox.util.ProcessUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.File;

@Slf4j
@Service
public class JavaDockerCodeSandbox extends CodeSandboxTemplate {

	public JavaDockerCodeSandbox() {
		super();
		super.GLOBAL_CODE_FILE_NAME = "Main.java";
		super.ImageName = "eclipse-temurin:21-jdk-alpine";
		super.volumnName = "/java";
	}

	@Override
	public ExecuteMessage compileFile(File userCodeFile) throws Exception {
		String compileCmd = LanguageCmdUtils.getDockerCompileCMD(containerId, volumnName, GLOBAL_CODE_FILE_NAME, "java");
		Process compileProcess = Runtime.getRuntime().exec(compileCmd);
		ExecuteMessage executeMessage = ProcessUtils.compileCode(compileProcess);
		if (executeMessage.getExitValue() != 0) {
			log.error("编译失败:" + executeMessage.getErrorMessage());
			throw new RuntimeException("编译错误：\n错误的原因是：" + executeMessage.getErrorMessage());
		}
		return executeMessage;
	}

	@Override
	public ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest) {
		return super.executeCode(executeCodeRequest);
	}

}
