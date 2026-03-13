package com.zt.codesandbox;

import com.zt.codesandbox.mannager.CodeManager;
import com.zt.codesandbox.model.ExecuteCodeRequest;
import com.zt.codesandbox.model.ExecuteCodeResponse;

import java.io.IOException;

public class LocalCodeSandbox implements CodeSandBox {

	@Override
	public ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest) throws IOException {
		CodeSandBox codeSandbox = CodeManager.getCodeSandbox(executeCodeRequest.getLanguage());
		return codeSandbox.executeCode(executeCodeRequest);
	}

}