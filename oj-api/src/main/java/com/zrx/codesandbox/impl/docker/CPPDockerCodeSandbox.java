package com.zrx.codesandbox.impl.docker;

import com.zrx.codesandbox.impl.template.CodeSandboxTemplate;
import com.zrx.codesandbox.model.ExecuteCodeRequest;
import com.zrx.codesandbox.model.ExecuteCodeResponse;

public class CPPDockerCodeSandbox extends CodeSandboxTemplate {

	public CPPDockerCodeSandbox() {
		super();
		super.GLOBAL_CODE_FILE_NAME = "main.cpp";
		super.ImageName = "gcc:latest";
		super.volumnName = "/cpp";
	}

	@Override
	public ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest) {
		return super.executeCode(executeCodeRequest);
	}

}
