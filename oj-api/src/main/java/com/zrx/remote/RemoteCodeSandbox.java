package com.zrx.remote;

import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.zrx.codesandbox.CodeSandBox;
import com.zrx.codesandbox.model.ExecuteCodeRequest;
import com.zrx.codesandbox.model.ExecuteCodeResponse;
import com.zrx.exception.BusinessException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;


public class RemoteCodeSandbox implements CodeSandBox {



    private static final String AUTH_REQUEST_HEADER = "auth";

    private static final String AUTH_REQUSET_SECRET = "secretKey";

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
        }catch (Exception e) {
            e.printStackTrace();
        }
        return JSONUtil.toBean(responseStr,ExecuteCodeResponse.class);
    }


}
