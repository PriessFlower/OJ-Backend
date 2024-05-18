package com.zrx.execudeCode;

import com.zrx.codesandbox.CodeSandBox;
import com.zrx.codesandbox.LocalCodeSandbox;
import com.zrx.remote.RemoteCodeSandbox;

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
