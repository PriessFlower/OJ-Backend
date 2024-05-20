package com.zrx.controller;

import com.mybatisflex.core.paginate.Page;
import com.zrx.model.common.Paging;
import com.zrx.model.dto.post.OjPostAddRequest;
import com.zrx.model.dto.post.OjPostQueryRequest;
import com.zrx.model.dto.post.OjPostUpdateRequest;
import com.zrx.model.vo.OjPostSimpleVo;
import com.zrx.model.vo.OjPostVo;
import com.zrx.reuslt.Result;
import com.zrx.service.OjPostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 帖子 控制层。
 *
 * @author zhang.rx
 * @since 2024/5/13
 */
@RestController
@Tag(name = "OjPost", description = "帖子接口")
@RequestMapping("/oj/post")
public class OjPostController {

	@Resource
	private OjPostService ojPostService;

	/**
	 * 添加帖子。
	 * @param req 帖子
	 * @return {@code true} 添加成功，{@code false} 添加失败
	 */
	@PostMapping("/save")
	@Operation(summary = "保存帖子")
	public Result<Boolean> save(@Valid @RequestBody @Parameter(description = "帖子") OjPostAddRequest req) {
		return Result.success(ojPostService.save(req));
	}

	/**
	 * 根据主键删除帖子。
	 * @param id 主键
	 * @return {@code true} 删除成功，{@code false} 删除失败
	 */
	@DeleteMapping("/remove/{id}")
	@Operation(summary = "根据主键帖子")
	public Result<Boolean> remove(@PathVariable @Parameter(description = "帖子主键") Long id) {
		return Result.success(ojPostService.removePostById(id));
	}

	/**
	 * 根据主键更新帖子。
	 * @param req 帖子
	 * @return {@code true} 更新成功，{@code false} 更新失败
	 */
	@PutMapping("/update")
	@Operation(summary = "根据主键更新帖子")
	public Result<Boolean> update(@Valid @RequestBody @Parameter(description = "帖子") OjPostUpdateRequest req) {
		return Result.success(ojPostService.updateById(req));
	}

	/**
	 * 根据帖子主键获取详细信息。
	 * @param id 帖子主键
	 * @return 帖子详情
	 */
	@GetMapping("/getInfo/{id}")
	@Operation(summary = "根据主键获取帖子")
	public Result<OjPostVo> getInfo(@PathVariable String id) {
		return Result.success((ojPostService.getInfoById(id)));
	}

	/**
	 * 分页查询帖子。
	 * @param page 分页对象
	 * @return 分页对象
	 */
	@PostMapping("/page")
	@Operation(summary = "分页查询帖子")
	public Result<Page<OjPostVo>> page(@Parameter(description = "分页信息") Paging page,
			@RequestBody OjPostQueryRequest req) {
		return Result.success(ojPostService.page(page, req, false));
	}

	/**
	 * 分页查询帖子。
	 * @param page 分页对象
	 * @return 分页对象
	 */
	@PostMapping("/pageSelf")
	@Operation(summary = "分页查询自己的帖子")
	public Result<Page<OjPostVo>> pageSelf(@Parameter(description = "分页查询自己的帖子") Paging page,
			@RequestBody OjPostQueryRequest req) {
		return Result.success(ojPostService.page(page, req, true));
	}

	/**
	 * 获取五个热门帖子
	 * @return 帖子简单信息Vo
	 */
	@GetMapping("/get/fiveHotPost")
	@Operation(summary = "获取五个热门帖子")
	public Result<List<OjPostSimpleVo>> getFiveHotPost() {
		return Result.success(ojPostService.getFiveHotPost());
	}

}
