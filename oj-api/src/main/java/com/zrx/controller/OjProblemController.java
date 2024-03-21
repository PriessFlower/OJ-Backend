package com.zrx.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.mybatisflex.core.paginate.Page;
import com.zrx.model.common.Paging;
import com.zrx.model.common.SaveGroup;
import com.zrx.model.common.UpdateGroup;
import com.zrx.model.dto.problem.OjProblemAddRequest;
import com.zrx.model.dto.problem.OjProblemQueryRequest;
import com.zrx.model.dto.problem.OjProblemUpdateRequest;
import com.zrx.model.vo.OjProblemVo;
import com.zrx.reuslt.Result;
import com.zrx.reuslt.ResultCode;
import com.zrx.security.satoken.AuthConst;
import com.zrx.service.OjProblemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;

/**
 * 题目 控制层。
 *
 * @author zhang.rx
 * @since 2024/3/20
 */
@RestController
@Tag(name = "OjProblem", description = "题目管理")
@RequestMapping("/ojProblem")
public class OjProblemController {

    @Resource
    private OjProblemService ojProblemService;

    /**
     * 添加题目。
     *
     * @param req 题目
     * @return {@code true} 添加成功，{@code false} 添加失败
     */
    @PostMapping("/save")
    @Operation(summary = "保存题目")
    @SaCheckPermission(AuthConst.SUPER_ADMIN)
    public Result<Boolean> save(@Validated({SaveGroup.class}) @RequestBody @Parameter OjProblemAddRequest req) {
        return Result.success(ojProblemService.save(req));
    }

    /**
     * 根据主键删除题目。
     *
     * @param id 主键
     * @return {@code true} 删除成功，{@code false} 删除失败
     */
    @DeleteMapping("/remove/{id}")
    @Operation(summary = "根据主键删除题目")
    @SaCheckPermission(AuthConst.SUPER_ADMIN)
    public Result<String> remove(@PathVariable @Parameter(description = "题目主键") Serializable id) {
        boolean removeFlag = ojProblemService.removeById(id);
        if (!removeFlag) {
            return Result.fail(ResultCode.FAIL);
        }
        return Result.ok();
    }

    /**
     * 根据主键更新题目。
     *
     * @param req 题目
     * @return {@code true} 更新成功，{@code false} 更新失败
     */
    @PutMapping("/update")
    @Operation(summary = "根据主键更新题目")
    @SaCheckPermission(AuthConst.SUPER_ADMIN)
    public Result<Boolean> update(@Validated({UpdateGroup.class}) @RequestBody @Parameter(description = "题目主键") OjProblemUpdateRequest req) {
        return Result.success(ojProblemService.updateById(req));
    }

    /**
     * 根据题目主键获取详细信息。
     *
     * @param id 题目主键
     * @return 题目详情
     */
    @GetMapping("/getInfo/{id}")
    @Operation(summary = "根据主键获取题目")
    @SaCheckPermission(AuthConst.SUPER_ADMIN)
    public Result<OjProblemVo> getInfo(@PathVariable Serializable id) {
        return Result.success(ojProblemService.getInfoById(id));
    }

    /**
     * 分页查询题目。
     *
     * @param page 分页对象
     * @return 分页对象
     */
    @GetMapping("/page")
    @Operation(summary = "分页查询题目")
    @SaCheckPermission(AuthConst.SUPER_ADMIN)
    public Result<Page<OjProblemVo>> page(@Parameter(description = "分页信息") Paging page, OjProblemQueryRequest req) {
        return Result.success(ojProblemService.page(page, req));
    }

}
