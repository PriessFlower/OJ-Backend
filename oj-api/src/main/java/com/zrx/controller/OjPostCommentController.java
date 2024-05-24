package com.zrx.controller;

import com.zrx.model.dto.postComment.PostCommentRequest;
import com.zrx.model.vo.PostCommentVo;
import com.zrx.reuslt.Result;
import com.zrx.service.OjPostCommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.List;

/**
 * 帖子评论 控制层。
 *
 * @author zhang.rx
 * @since 2024/5/20
 */
@RestController
@Tag(name = "OjPostComment", description = "帖子评论接口")
@RequestMapping("/oj/post/comment")
public class OjPostCommentController {

    @Resource
    private OjPostCommentService ojPostCommentService;

    /**
     * 添加帖子评论。
     * @param req 帖子评论
     * @return {@code true} 添加成功，{@code false} 添加失败
     */
    @PostMapping("/save")
    @Operation(summary = "评论帖子")
    public Result<Boolean> save(@Valid @RequestBody @Parameter(description = "帖子评论") PostCommentRequest req) {
        return Result.success(ojPostCommentService.save(req));
    }

    /**
     * 根据主键删除帖子评论。
     * @param id 主键
     * @return {@code true} 删除成功，{@code false} 删除失败
     */
    @DeleteMapping("/remove/{id}")
    @Operation(summary = "根据主键删除帖子评论")
    public Result<Boolean> remove(@PathVariable @Parameter(description = "帖子评论主键") Serializable id) {
        return Result.success(ojPostCommentService.removeRecursionById(id));
    }

    /**
     * 查询所有帖子评论。
     * @return 所有数据
     */
    @GetMapping("/list")
    @Operation(summary = "获取根节点评论")
    public Result<List<PostCommentVo>> list(@RequestParam String postId) {
        return Result.success(ojPostCommentService.list(postId));
    }

    /**
     * 根据父节点id获取帖子子评论。
     * @return 所有数据
     */
    @GetMapping("/listChildren")
    @Operation(summary = "根据父节点id获取帖子子评论")
    public Result<List<PostCommentVo>> listChildren(@RequestParam String postId) {
        return Result.success(ojPostCommentService.listChildren(postId));
    }

    @GetMapping("/get/num")
    @Operation(summary = "根据帖子id获取评论数量")
    public Result<Long> getNum(@RequestParam String postId) {
        return Result.success(ojPostCommentService.getNum(postId));
    }

}
