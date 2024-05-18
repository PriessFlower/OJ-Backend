package com.zrx.sys.mapstruct;

import com.zrx.sys.model.dto.SysUserRequest;
import com.zrx.sys.model.dto.SysUserUpdateRequest;
import com.zrx.sys.model.entity.SysUser;
import com.zrx.sys.model.vo.SysUserResponse;
import com.zrx.sys.model.vo.SysUserSimpleResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

/**
 * 系统用户 mapstruct
 *
 * @author zhang.rx
 * @since 2024/2/20
 */
@Mapper(componentModel = "Spring")
public interface SysUserMap {

	SysUser toEntity(SysUserRequest request);

	@Mappings({ @Mapping(source = "createTime", target = "registerTime") })
	SysUserResponse toResp(SysUser sysUser);

	SysUserSimpleResponse toSimpleResp(SysUser sysUser);

	SysUser toEntityByUpdate(SysUserUpdateRequest request);

}
