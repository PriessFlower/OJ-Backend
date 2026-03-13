package com.zt.execudeCode;

import com.zt.codesandbox.CodeSandBox;
import com.zt.codesandbox.LocalCodeSandbox;
import com.zt.remote.RemoteCodeSandbox;

public class CodeSandboxManager {

	public static CodeSandBox instance(String type) {
		if (type.equals("remote")) {
			return new RemoteCodeSandbox();
		}
		else if (type.equals("local")) {
			return new LocalCodeSandbox();
		}
		else {
			return new RemoteCodeSandbox();
		}
	}

}
