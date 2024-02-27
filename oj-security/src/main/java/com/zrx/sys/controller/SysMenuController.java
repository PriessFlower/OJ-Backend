package com.zrx.sys.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.zrx.reuslt.Result;
import com.zrx.security.satoken.AuthConst;
import com.zrx.sys.mapstruct.SysMenuMap;
import com.zrx.sys.model.dto.SysMenuRequest;
import com.zrx.sys.model.entity.SysMenu;
import com.zrx.sys.service.SysMenuService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 菜单管理
 *
 * @author zhang.rx
 * @since 2024/2/26
 */
@Tag(name = "菜单管理")
@RestController
@RequiredArgsConstructor
@RequestMapping("/sys/menu")
public class SysMenuController {

    private final SysMenuService sysMenuService;

    private final SysMenuMap sysMenuMap;

    @PostMapping("/save")
    @Operation(summary = "新增菜单")
    @SaCheckPermission(AuthConst.SUPER_ADMIN)
    public Result<String> save(@Valid @RequestBody SysMenuRequest request) {
        sysMenuService.save(sysMenuMap.dto2Entity(request));
        return Result.ok();
    }


    @GetMapping("/list")
    @Operation(summary = "查询所有菜单")
    @SaCheckPermission(AuthConst.SUPER_ADMIN)
    public Result<List<SysMenu>> list(@Parameter(
            name = "type",
            description = "菜单类型 0:菜单 1:按钮 null:全部",
            in = ParameterIn.QUERY,
            schema = @Schema(allowableValues = {"0", "1", "null"}))
                                             Integer type) {
        return Result.success(sysMenuService.getAllMenuList(type));
    }

}
