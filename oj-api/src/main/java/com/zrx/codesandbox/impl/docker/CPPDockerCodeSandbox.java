package com.zrx.codesandbox.impl.docker;

import com.zrx.codesandbox.impl.template.CodeSandboxTemplate;
import com.zrx.codesandbox.model.ExecuteCodeRequest;
import com.zrx.codesandbox.model.ExecuteCodeResponse;
import com.zrx.codesandbox.model.ExecuteMessage;
import com.zrx.codesandbox.util.LanguageCmdUtils;
import com.zrx.codesandbox.util.ProcessUtils;
import lombok.extern.slf4j.Slf4j;

import java.io.File;

@Slf4j
public class CPPDockerCodeSandbox extends CodeSandboxTemplate {

	public CPPDockerCodeSandbox() {
		super();
		super.GLOBAL_CODE_FILE_NAME = "main.cpp";
		super.ImageName = "gcc:latest";
		super.volumnName = "/cpp";
	}

	@Override
	public ExecuteMessage compileFile(File userCodeFile) throws Exception {
		String compileCmd = LanguageCmdUtils.getDockerCompileCMD(containerId, volumnName, GLOBAL_CODE_FILE_NAME, "c++");
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
