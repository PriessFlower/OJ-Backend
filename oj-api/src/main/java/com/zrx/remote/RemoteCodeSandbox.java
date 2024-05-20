package com.zrx.remote;

import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.zrx.codesandbox.CodeSandBox;
import com.zrx.codesandbox.model.ExecuteCodeRequest;
import com.zrx.codesandbox.model.ExecuteCodeResponse;
import com.zrx.exception.BusinessException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;

import java.util.Arrays;

public class RemoteCodeSandbox implements CodeSandBox {

	private static final String AUTH_REQUEST_HEADER = "auth";

	private static final String AUTH_REQUSET_SECRET = "secretKey";

	public static void main(String[] args) {
		RemoteCodeSandbox remoteCodeSandbox = new RemoteCodeSandbox();
		String code = "//\n" + "// Created by Administrator on 2024/4/30.\n" + "//\n" + "\n" + "\n"
				+ "#include <iostream>\n" + "#include <bits/stdc++.h>\n" + "\n" + "using namespace std;\n" + "\n"
				+ "int method(string &res, int yue, int chu) {\n" + "    yue = yue * 10;\n"
				+ "    int shan = yue / chu;\n" + "    yue = yue % chu;\n" + "    res += (char) ('0' + shan);\n"
				+ "    return yue;\n" + "}\n" + "\n" + "int main() {\n" + "    int a, b, n;\n"
				+ "    cin >> a >> b >> n;\n" + "    int yue = a % b;\n" + "    if (yue == 0) {\n"
				+ "        cout << -1;\n" + "        return 0;\n" + "    }\n" + "    bool flag = false;\n"
				+ "    string res = \"\";\n" + "    int kuai = yue;\n" + "    int man = yue;\n"
				+ "    string manStr = \"\";\n" + "    for (int i = 0; i < n; i += 2) {\n"
				+ "        kuai = method(res, kuai, b);\n" + "        kuai = method(res, kuai, b);\n"
				+ "        man = method(manStr, man, b);\n" + "        if (kuai == man) {\n"
				+ "            method(manStr, man, b);\n" + "            res = manStr;\n" + "            flag = true;\n"
				+ "            break;\n" + "        }\n" + "        if (kuai == 0 || man == 0) {\n"
				+ "            res = \"-1\";\n" + "            flag = true;\n" + "            break;\n" + "        }\n"
				+ "    }\n" + "    if (flag) {\n" + "        cout << res;\n" + "    } else {\n"
				+ "        cout << \"0\";\n" + "    }\n" + "    return 0;\n" + "}";
		ExecuteCodeRequest executeCodeRequest = new ExecuteCodeRequest();
		executeCodeRequest.setCode(code);
		executeCodeRequest.setLanguage("c++");
		executeCodeRequest.setInputList(Arrays.asList("90 45 100\n", "77 119 100\n", "335 113 19\n"));
		ExecuteCodeResponse executeCodeResponse = remoteCodeSandbox.executeCode(executeCodeRequest);
		System.out.println(executeCodeResponse);
	}

	@Override
	public ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest) {
		String responseStr = null;
		try {
			System.out.println("这是远程接口调用");
			String url = "http://118.24.87.165:8091/executeCode";
			String json = JSONUtil.toJsonStr(executeCodeRequest);
			responseStr = HttpUtil.createPost(url)
				.header(AUTH_REQUEST_HEADER, AUTH_REQUSET_SECRET)
				.body(json)
				.execute()
				.body();
			if (StringUtils.isBlank(responseStr)) {
				throw new Exception("executeCode remoteSandbox is error");
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return JSONUtil.toBean(responseStr, ExecuteCodeResponse.class);
	}

}
