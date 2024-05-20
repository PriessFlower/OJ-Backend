package com.zrx.sys.service;

import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.service.IService;
import com.zrx.model.common.Paging;
import com.zrx.security.model.dto.LoginRequest;
import com.zrx.security.model.dto.RegisterRequest;
import com.zrx.security.model.vo.LoginResponse;
import com.zrx.sys.model.dto.SysUserRequest;
import com.zrx.sys.model.dto.SysUserUpdateRequest;
import com.zrx.sys.model.entity.SysUser;
import com.zrx.sys.model.vo.SysUserResponse;
import com.zrx.sys.model.vo.SysUserSimpleResponse;
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
