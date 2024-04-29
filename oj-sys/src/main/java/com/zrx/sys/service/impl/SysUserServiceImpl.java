package com.zrx.sys.service.impl;

import cn.dev33.satoken.session.SaSession;
import cn.dev33.satoken.stp.StpUtil;
import com.mybatisflex.core.mask.MaskManager;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryChain;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.core.update.UpdateChain;
import com.mybatisflex.core.util.StringUtil;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.zrx.exception.BusinessException;
import com.zrx.model.common.Paging;
import com.zrx.reuslt.ResultCode;
import com.zrx.security.model.dto.LoginRequest;
import com.zrx.security.model.dto.RegisterRequest;
import com.zrx.security.model.vo.LoginResponse;
import com.zrx.security.satoken.AuthConst;
import com.zrx.security.service.CaptchaService;
import com.zrx.security.utils.Md5Util;
import com.zrx.security.utils.SecurityHelper;
import com.zrx.sys.enums.UserStatusEnum;
import com.zrx.sys.mapper.SysRoleMapper;
import com.zrx.sys.mapper.SysRoleUserMapper;
import com.zrx.sys.mapper.SysUserMapper;
import com.zrx.sys.mapstruct.SysUserMap;
import com.zrx.sys.model.dto.SysUserRequest;
import com.zrx.sys.model.dto.SysUserUpdateRequest;
import com.zrx.sys.model.entity.SysRoleUser;
import com.zrx.sys.model.entity.SysUser;
import com.zrx.sys.model.vo.SysUserResponse;
import com.zrx.sys.service.SysFileService;
import com.zrx.sys.service.SysUserService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import static com.zrx.sys.model.entity.table.SysRoleTableDef.SYS_ROLE;
import static com.zrx.sys.model.entity.table.SysRoleUserTableDef.SYS_ROLE_USER;
import static com.zrx.sys.model.entity.table.SysUserTableDef.SYS_USER;

/**
 * 系统用户 服务层实现。
 *
 * @author zhang.rx
 * @since 2024/2/18
 */
@Service
@RequiredArgsConstructor
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    private final CaptchaService captchaService;
    private final SysUserMap sysUserMap;
    private final SysFileService sysFileService;
    private final SysRoleUserMapper roleUserMapper;
    private final SysRoleMapper roleMapper;

    @Override
    public String uploadAvatar(MultipartFile file) {
        String url = sysFileService.upload(file);
        SysUser user = SecurityHelper.getUser();
        user.setAvatar(url);
        boolean updateFlag = updateById(user);
        if (!updateFlag) {
            throw new BusinessException("上传头像失败");
        }
        return url;
    }

    @Override
    public Boolean update(SysUserUpdateRequest request) {
        SysUser sysUser = sysUserMap.toEntityByUpdate(request);
        return updateById(sysUser, true);
    }

    @Override
    public SysUser getByUsername(String username) {
        if (StringUtils.isBlank(username)) {
            throw new BusinessException("用户名为空");
        }
        return mapper.selectOneByQuery(new QueryWrapper().where(SYS_USER.USERNAME.eq(username)));
    }

    @Override
    public LoginResponse login(LoginRequest loginRequest) {
        // 验证码是否正确
        boolean flag = captchaService.validate(loginRequest.getUuid(), loginRequest.getCaptcha());
        if (!flag) {
            throw new BusinessException(ResultCode.PARAM_ERROR.getCode(), "验证码错误");
        }
        // 用户信息
        SysUser user = getByUsername(loginRequest.getUsername());
        // 用户不存在
        if (user == null) {
            throw new BusinessException(ResultCode.FAIL.getCode(), "用户不存在");
        }
        // 密码错误
        if (!StringUtils.equals(Md5Util.inputPassToDBPass(loginRequest.getPassword(), user.getSalt()),
                user.getPassword())) {
            throw new BusinessException(ResultCode.FAIL.getCode(), "密码错误");
        }
        // 账号停用
        if (user.getStatus() == UserStatusEnum.DISABLE.getValue()) {
            throw new BusinessException(ResultCode.FORBIDDEN.getCode(), "当前用户已被系统停用");
        }
        // 删除之前的token信息
        StpUtil.logout(user.getId());
        String loginId = String.valueOf(user.getId());
        StpUtil.login(loginId);
        // 存储已经登录的用户信息
        StpUtil.getSession().set(loginId, user);
        return new LoginResponse(StpUtil.getTokenInfo().getTokenValue(), loginId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String register(RegisterRequest registerRequest) {
        String username = registerRequest.getUsername();
        String password = registerRequest.getPassword();
        String checkPassword = registerRequest.getCheckPassword();
        String mobile = registerRequest.getMobile();
        if (!StringUtils.equals(password, checkPassword)) {
            throw new BusinessException("两次输入的密码不一致");
        }
        SysUser sysUser = new SysUser();
        String salt = Md5Util.generateSalt();
        sysUser.setUsername(registerRequest.getUsername());
        sysUser.setPassword(Md5Util.inputPassToDBPass(password, salt));
        sysUser.setSalt(salt);
        sysUser.setStatus(UserStatusEnum.ENABLED.getValue());
        sysUser.setMobile(mobile);

        // 是否有相同用户名的用户
        if (exists(new QueryWrapper().where(SYS_USER.USERNAME.eq(username)))) {
            throw new BusinessException("用户名重复");
        }
        boolean saveFlag = save(sysUser);
        // 设置最初角色：普通用户
        SysRoleUser sysRoleUser = new  SysRoleUser();
        sysRoleUser.setUserId(sysUser.getId());
        sysRoleUser.setRoleId(
                QueryChain.of(roleMapper)
                        .select(SYS_ROLE.ID)
                        .where(SYS_ROLE.ROLE_CODE.eq(AuthConst.USER))
                        .oneAs(Long.class)
        );
        int insertChildFlag = roleUserMapper.insert(sysRoleUser);
        if (!saveFlag && insertChildFlag != 1) {
            throw new BusinessException("注册失败");
        }
        return "注册成功";
    }

    @Override
    public Boolean disable(String id) {
        boolean updateFlag = UpdateChain.of(mapper)
                .where(SYS_USER.ID.eq(id))
                .set(SYS_USER.STATUS, UserStatusEnum.DISABLE.getValue())
                .update();
        if (!updateFlag) {
            throw new BusinessException("停用失败");
        }
        // 强制踢下线
        StpUtil.kickout(id);
        return true;
    }

    @Override
    public Boolean enable(String id) {
        return UpdateChain.of(mapper)
                .where(SYS_USER.ID.eq(id))
                .set(SYS_USER.STATUS, UserStatusEnum.ENABLED.getValue())
                .update();
    }

    @Override
    public SysUserResponse getInfo() {
        Object loginId = StpUtil.getTokenInfo().loginId;
        Long userId = Long.valueOf((String) loginId);
        // TODO: 统一拦截脱敏、解除脱敏
        SysUser sysUser;
        try {
            MaskManager.skipMask();
            sysUser = mapper.selectOneByQuery(new QueryWrapper()
                    .where(SYS_USER.ID.eq(userId)));
        } finally {
            MaskManager.restoreMask();
        }
        if (sysUser == null) {
            throw new BusinessException("未找到该用户信息");
        }
        SysUserResponse resp = sysUserMap.toResp(sysUser);
        // TODO: 待实现，获取权限最大的角色（需要排序），返回角色列表。
        resp.setRole(AuthConst.SUPER_ADMIN);
        return resp;
    }

    @Override
    public Page<SysUserResponse> page(Paging page, SysUserRequest request) {
        QueryWrapper queryWrapper = new QueryWrapper().select(SYS_USER.DEFAULT_COLUMNS)
                .select(SYS_ROLE_USER.ROLE_ID, SYS_ROLE.REMARK.as("roles"))
                .leftJoin(SYS_ROLE_USER).on(SYS_USER.ID.eq(SYS_ROLE_USER.USER_ID))
                .leftJoin(SYS_ROLE).on(SYS_ROLE_USER.ROLE_ID.eq(SYS_ROLE.ID))
                .where(SYS_USER.USERNAME.like(request.getUsername(), StringUtil::isNotBlank))
                .and(SYS_USER.REAL_NAME.like(request.getRealName(), StringUtil::isNotBlank))
                .and(SYS_USER.GENDER.eq(request.getGender()))
                .and(SYS_USER.EMAIL.like(request.getEmail(), StringUtil::isNotBlank))
                .and(SYS_USER.MOBILE.like(request.getMobile(), StringUtil::isNotBlank))
                .and(SYS_USER.STATUS.eq(request.getStatus()))
                .orderBy(SYS_USER.UPDATE_TIME.desc());
        Page<SysUserResponse> respPage = mapper.paginateAs(Page.of(page.getPageNum(), page.getPageSize()), queryWrapper, SysUserResponse.class);
        respPage.getRecords().forEach(item -> {
            SaSession session = StpUtil.getSessionByLoginId(item.getId(), false);
            item.setOnline(session != null);
        });
        return respPage;
    }

    @Override
    public Boolean kick(Long id) {
        StpUtil.kickout(id);
        return true;
    }

}
