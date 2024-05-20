package com.zrx.controller;

import com.mybatisflex.core.paginate.Page;
import com.zrx.model.common.Paging;
import com.zrx.model.vo.OjPostVo;
import com.zrx.reuslt.Result;
import com.zrx.service.OjPostThumbService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

/**
 * 帖子点赞接口
 *
 * @author zhang.rx
 * @since 2024/5/13
 */
@RestController
@Tag(name = "OjThumbPost", description = "帖子点赞接口")
@RequestMapping("/oj/post/thumb")
public class OjThumbPostController {

	@Resource
	private OjPostThumbService postThumbService;

	/**
	 * 点赞帖子
	 */
	@PostMapping("/do/{id}")
	@Operation(summary = "点赞帖子")
	public Result<Boolean> thumb(@PathVariable Long id) {
		return Result.success(postThumbService.thumbPost(id));
	}

	/**
	 * 取消点赞
	 */
	@PostMapping("/cancel/{id}")
	@Operation(summary = "取消点赞")
	public Result<Boolean> cancel(@PathVariable @Parameter(description = "帖子id") Long id) {
		return Result.success(postThumbService.cancelThumbPost(id));
	}

	/**
	 * 根据用户id获取点赞过的帖子
	 */
	@GetMapping("/get/post/{id}")
	@Operation(summary = "根据用户id获取点赞过的帖子")
	public Result<Page<OjPostVo>> getThumbPost(@Parameter(description = "分页信息") Paging page,
			@PathVariable("id") String userId) {
		return Result.success(postThumbService.page(page, userId));
	}

}
