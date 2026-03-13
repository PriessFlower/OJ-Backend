package com.zt.codesandbox;

import com.zt.codesandbox.model.ExecuteCodeRequest;
import com.zt.codesandbox.model.ExecuteCodeResponse;

import java.io.IOException;

public interface CodeSandBox {

	ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest) throws IOException;

}
