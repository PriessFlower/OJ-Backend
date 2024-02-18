package com.zrx.controller;

import com.zrx.reuslt.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测试接口
 *
 * @author zhang.rx
 * @since 2024/2/18
 */
@Tag(name = "测试接口", description = "这是测试接口")
@RestController
@RequestMapping("core/test")
public class TestController {

	@GetMapping("/test")
	@Operation(summary = "测试接口", description = "测试接口描述")
	public Result<String> test() {
		return Result.success("测试成功");
	}

}
