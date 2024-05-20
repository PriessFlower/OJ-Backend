package com.zrx.codesandbox;

import com.zrx.codesandbox.mannager.CodeManager;
import com.zrx.codesandbox.model.ExecuteCodeRequest;
import com.zrx.codesandbox.model.ExecuteCodeResponse;

import java.io.IOException;

public class LocalCodeSandbox implements CodeSandBox {

	@Override
	public ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest) throws IOException {
		CodeSandBox codeSandbox = CodeManager.getCodeSandbox(executeCodeRequest.getLanguage());
		return codeSandbox.executeCode(executeCodeRequest);
	}

}