package com.zrx.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * 访问默认端口跳转到接口文档
 *
 * @author zhang.rx
 * @since 2024/2/8
 */
public class ApiDocInterceptor implements HandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		if ("/api/".equals(request.getRequestURI()) || "".equals(request.getRequestURI())) {
			// 重定向到接口文档
			response.sendRedirect(request.getContextPath() + "/doc.html");
			return false;
		}
		return true;
	}

}