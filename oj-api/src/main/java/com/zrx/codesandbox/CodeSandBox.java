package com.zrx.codesandbox;

import com.zrx.codesandbox.model.ExecuteCodeRequest;
import com.zrx.codesandbox.model.ExecuteCodeResponse;

import java.io.IOException;

public interface CodeSandBox {

	ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest) throws IOException;

}
