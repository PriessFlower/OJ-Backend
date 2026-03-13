package com.zt.sys.service;

import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.service.IService;
import com.zt.model.common.Paging;
import com.zt.security.model.dto.LoginRequest;
import com.zt.security.model.dto.RegisterRequest;
import com.zt.security.model.vo.LoginResponse;
import com.zt.sys.model.dto.SysUserRequest;
import com.zt.sys.model.dto.SysUserUpdateRequest;
import com.zt.sys.model.entity.SysUser;
import com.zt.sys.model.vo.SysUserResponse;
import com.zt.sys.model.vo.SysUserSimpleResponse;
import org.springframework.web.multipart.MultipartFile;

/**
 * 系统用户 服务层。
 *
 * @author zhang.rx
 * @since 2024/2/18
 */
public interface SysUserService extends IService<SysUser> {

	SysUser getByUsername(String username);

	Boolean update(SysUserUpdateRequest request);

	LoginResponse login(LoginRequest loginRequest);

	String register(RegisterRequest registerRequest);

	Boolean disable(String id);

	Boolean enable(String id);

	SysUserResponse getInfo();

	SysUserSimpleResponse getInfoById(String userId);

	Page<SysUserResponse> page(Paging page, SysUserRequest request);

	String uploadAvatar(MultipartFile file);

	Boolean kick(Long id);

}
