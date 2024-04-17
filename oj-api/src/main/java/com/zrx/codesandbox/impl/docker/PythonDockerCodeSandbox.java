package com.zrx.codesandbox.impl.docker;

import com.zrx.codesandbox.impl.template.CodeSandboxTemplate;
import com.zrx.codesandbox.model.ExecuteCodeRequest;
import com.zrx.codesandbox.model.ExecuteCodeResponse;

import java.util.ArrayList;
import java.util.List;

public class PythonDockerCodeSandbox extends CodeSandboxTemplate {

	public PythonDockerCodeSandbox() {
		super();
		super.GLOBAL_CODE_FILE_NAME = "main.py";
		super.ImageName = "python:3.7";
		super.volumnName = "/python";
	}

	@Override
	public ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest) {
		List<String> inputList = executeCodeRequest.getInputList();
		List<String> newInputList = new ArrayList<>();
		for (String input : inputList) {
			newInputList.add(input.replaceAll(" ", "\n"));
		}
		executeCodeRequest.setInputList(newInputList);
		return super.executeCode(executeCodeRequest);
	}

}
