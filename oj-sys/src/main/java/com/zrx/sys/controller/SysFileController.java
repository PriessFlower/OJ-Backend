package com.zrx.sys.controller;

import com.zrx.reuslt.Result;
import com.zrx.sys.service.SysFileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * 云文件 控制层。
 *
 * @author zhang.rx
 * @since 2024/4/18
 */
@RestController
@Tag(name = "云文件接口")
@RequestMapping("/sys/file")
public class SysFileController {

	@Resource
	private SysFileService sysFileService;

	/**
	 * 上传文件。
	 * @param file 云文件
	 * @return {@code true} 添加成功，{@code false} 添加失败
	 */
	@PostMapping("/upload")
	@Operation(summary = "上传文件")
	public Result<String> upload(@RequestParam("file") MultipartFile file) {
		return Result.success(sysFileService.upload(file));
	}

	// /**
	// * 根据主键删除云文件。
	// *
	// * @param id 主键
	// * @return {@code true} 删除成功，{@code false} 删除失败
	// */
	// @DeleteMapping("/remove/{id}")
	// @Operation(summary = "根据主键云文件")
	// public boolean remove(@PathVariable @Parameter(description = "云文件主键") Serializable
	// id) {
	// return sysFileService.removeById(id);
	// }
	//
	// /**
	// * 根据云文件主键获取详细信息。
	// *
	// * @param id 云文件主键
	// * @return 云文件详情
	// */
	// @GetMapping("/getInfo/{id}")
	// @Operation(summary = "根据主键获取云文件")
	// public SysFile getInfo(@PathVariable Serializable id) {
	// return sysFileService.getById(id);
	// }
	//
	// /**
	// * 分页查询云文件。
	// *
	// * @param page 分页对象
	// * @return 分页对象
	// */
	// @GetMapping("/page")
	// @Operation(summary = "分页查询云文件")
	// public Page<SysFile> page(@Parameter(description = "分页信息") Page<SysFile> page) {
	// return sysFileService.page(page);
	// }

}
