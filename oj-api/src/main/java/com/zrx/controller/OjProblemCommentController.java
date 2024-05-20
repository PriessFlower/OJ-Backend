package com.zrx.controller;

import com.zrx.model.dto.problemComment.ProblemCommentRequest;
import com.zrx.model.vo.ProblemCommentVo;
import com.zrx.reuslt.Result;
import com.zrx.service.ProblemCommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.List;

/**
 * 题目评论 控制层。
 *
 * @author zhang.rx
 * @since 2024/4/18
 */
@RestController
@Tag(name = "OjProblemComment", description = "题目评论接口")
@RequestMapping("/problem/comment")
public class OjProblemCommentController {

	@Resource
	private ProblemCommentService problemCommentService;

	/**
	 * 添加题目评论。
	 * @param req 题目评论
	 * @return {@code true} 添加成功，{@code false} 添加失败
	 */
	@PostMapping("/save")
	@Operation(summary = "评论题目")
	public Result<Boolean> save(@Valid @RequestBody @Parameter(description = "题目评论") ProblemCommentRequest req) {
		return Result.success(problemCommentService.save(req));
	}

	/**
	 * 根据主键删除题目评论。
	 * @param id 主键
	 * @return {@code true} 删除成功，{@code false} 删除失败
	 */
	@DeleteMapping("/remove/{id}")
	@Operation(summary = "根据主键删除题目评论")
	public Result<Boolean> remove(@PathVariable @Parameter(description = "题目评论主键") Serializable id) {
		return Result.success(problemCommentService.removeRecursionById(id));
	}

	/**
	 * 查询所有题目评论。
	 * @return 所有数据
	 */
	@GetMapping("/list")
	@Operation(summary = "获取根节点评论")
	public Result<List<ProblemCommentVo>> list(@RequestParam Long problemId) {
		return Result.success(problemCommentService.list(problemId));
	}

	/**
	 * 根据父节点id获取题目子评论。
	 * @return 所有数据
	 */
	@GetMapping("/listChildren")
	@Operation(summary = "根据父节点id获取题目子评论")
	public Result<List<ProblemCommentVo>> listChildren(@RequestParam Long problemId) {
		return Result.success(problemCommentService.listChildren(problemId));
	}

}
