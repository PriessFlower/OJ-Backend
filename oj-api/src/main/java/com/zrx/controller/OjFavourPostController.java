package com.zrx.controller;

import com.mybatisflex.core.paginate.Page;
import com.zrx.model.common.Paging;
import com.zrx.model.vo.OjPostVo;
import com.zrx.reuslt.Result;
import com.zrx.service.OjPostFavourService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

/**
 * 帖子收藏接口
 *
 * @author zhang.rx
 * @since 2024/5/13
 */
@RestController
@Tag(name = "OjFavourPost", description = "帖子收藏接口")
@RequestMapping("/oj/post/favour")
public class OjFavourPostController {

	@Resource
	private OjPostFavourService postFavourService;

	/**
	 * 收藏帖子
	 */
	@PostMapping("/do/{id}")
	@Operation(summary = "收藏帖子")
	public Result<Boolean> favour(@PathVariable Long id) {
		return Result.success(postFavourService.favourPost(id));
	}

	/**
	 * 取消点赞
	 */
	@PostMapping("/cancel/{id}")
	@Operation(summary = "取消收藏")
	public Result<Boolean> cancel(@PathVariable @Parameter(description = "帖子id") Long id) {
		return Result.success(postFavourService.cancelFavourPost(id));
	}

	/**
	 * 根据用户id获取收藏过的帖子
	 */
	@GetMapping("/get/post/{id}")
	@Operation(summary = "根据用户id获取收藏过的帖子")
	public Result<Page<OjPostVo>> getFavourPost(@Parameter(description = "分页信息") Paging page,
			@PathVariable("id") String userId) {
		return Result.success(postFavourService.page(page, userId));
	}

}
