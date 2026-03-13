package com.zt.security.utils;

import cn.dev33.satoken.exception.NotWebContextException;
import cn.dev33.satoken.exception.SaTokenException;
import cn.dev33.satoken.stp.StpUtil;
import com.zt.sys.model.entity.SysUser;

/**
 * 获取用户信息工具类
 *
 * @author zhang.rx
 * @since 2024/2/26
 */
public class SecurityHelper {

    public static SysUser getUser() {
        try {
            String loginId = StpUtil.getLoginId() == null ? "" : (String) StpUtil.getLoginId();
            Object sysUser = StpUtil.getSession().get(loginId);
            if (sysUser == null) {
                return new SysUser();
            }
            return (SysUser) sysUser;
        } catch (NotWebContextException e) {
            // 【核心修改】：异步线程或定时任务没有 Web 上下文，会走到这里
            // 返回一个代表“系统”的虚拟用户，分配一个特殊的 ID
            SysUser systemUser = new SysUser();
            systemUser.setId(0L);
            return systemUser;
        } catch (SaTokenException e) {
            // 未登录等其他异常情况
            return new SysUser();
        }
    }



}
