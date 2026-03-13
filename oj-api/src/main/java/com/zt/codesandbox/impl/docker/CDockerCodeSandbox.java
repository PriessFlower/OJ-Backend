package com.zt.codesandbox.impl.docker;

import com.zt.codesandbox.impl.template.CodeSandboxTemplate;
import com.zt.codesandbox.model.ExecuteCodeRequest;
import com.zt.codesandbox.model.ExecuteCodeResponse;
import com.zt.codesandbox.model.ExecuteMessage;
import com.zt.codesandbox.util.LanguageCmdUtils;
import com.zt.codesandbox.util.ProcessUtils;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.util.Arrays;
import java.util.List;

@Slf4j
public class CDockerCodeSandbox extends CodeSandboxTemplate {

	List<String> blackList = Arrays.asList("", "");

	public CDockerCodeSandbox() {
		super();
		super.GLOBAL_CODE_FILE_NAME = "main.c";
		super.ImageName = "gcc:latest";
		super.volumnName = "/c";
	}

	@Override
	public ExecuteMessage compileFile(File userCodeFile) throws Exception {
		String compileCmd = LanguageCmdUtils.getDockerCompileCMD(containerId, volumnName, GLOBAL_CODE_FILE_NAME, "c");
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
