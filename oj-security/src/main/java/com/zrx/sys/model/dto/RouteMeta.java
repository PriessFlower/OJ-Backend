package com.zrx.sys.model.dto;

import lombok.Data;

/**
 * 路由其他配置
 * 
 * @author zhang.rx
 * @since 2024/2/27
 */
@Data
public class RouteMeta {
    // 订单号（可选）
    private Integer orderNo;
    
    // 标题（必填）
    private String title;
    
    // 动态路由级别（可选）
    private Integer dynamicLevel;
    
    // 动态路由真实路径（为了性能，可选）
    private String realPath;
    
    // 是否忽略权限（可选，默认为 false）
    private Boolean ignoreAuth;
    
    // 角色信息枚举列表（可选）test、admin
    private String roles;
    
    // 是否忽略缓存（可选，默认为 false）
    private Boolean ignoreKeepAlive;
    
    // 是否固定在标签上（可选，默认为 false）
    private Boolean affix;
    
    // 标签上的图标（可选）
    private String icon;
    
    // 标签上的图片（可选）
    private String img;
    
    // 框架源地址（可选）
    private String frameSrc;
    
    // 当前页面过渡效果（可选）
    private String transitionName;
    
    // 路由是否可动态添加（可选，默认为 false）
    private Boolean hideBreadcrumb;
    
    // 是否隐藏子菜单（可选，默认为 false）
    private Boolean hideChildrenInMenu;
    
    // 是否携带参数（可选，默认为 false）
    private Boolean carryParam;
    
    // 内部标记单级菜单（可选）
    private Boolean single;
    
    // 当前激活的菜单（可选）
    private String currentActiveMenu;
    
    // 从未在标签上显示（可选，默认为 false）
    private Boolean hideTab;
    
    // 从未在菜单上显示（可选，默认为 false）
    private Boolean hideMenu;
    
    // 是否作为链接处理（可选，默认为 false）
    private Boolean isLink;
    
    // 仅用于构建Menu，忽略实际路由功能（可选，默认为 false）
    private Boolean ignoreRoute;
    
    // 对子节点隐藏路径（可选，默认为 false）
    private Boolean hidePathForChildren;

}
