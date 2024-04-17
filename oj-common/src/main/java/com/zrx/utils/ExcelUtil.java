package com.zrx.utils;

import jakarta.servlet.http.HttpServletResponse;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

/**
 * Excel 导出响应头设置工具类
 *
 * @author zhang.rx
 * @since 2024/3/26
 */
public class ExcelUtil {

	/**
	 * 设置响应结果
	 * @param response 响应结果对象
	 * @param rawFileName 文件名
	 */
	public static void setExcelResponseProp(HttpServletResponse response, String rawFileName) {
		response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
		response.setCharacterEncoding("utf-8");
		String fileName = URLEncoder.encode(rawFileName, StandardCharsets.UTF_8).replaceAll("\\+", "%20");
		response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");
	}

}
