package com.zrx.codesandbox.impl.docker;

import com.zrx.codesandbox.impl.template.CodeSandboxTemplate;
import com.zrx.codesandbox.model.ExecuteCodeRequest;
import com.zrx.codesandbox.model.ExecuteCodeResponse;
import org.springframework.stereotype.Service;

@Service
public class JavaDockerCodeSandbox extends CodeSandboxTemplate {

	public JavaDockerCodeSandbox() {
		super();
		super.GLOBAL_CODE_FILE_NAME = "Main.java";
		super.ImageName = "phenompeople/openjdk17:latest";
		super.volumnName = "/java";
	}

	@Override
	public ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest) {
		return super.executeCode(executeCodeRequest);
	}

}
