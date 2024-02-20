package com.zrx.sys.mapstruct;

import com.zrx.sys.model.dto.SysUserRequest;
import com.zrx.sys.model.entity.SysUser;
import com.zrx.sys.model.vo.SysUserResponse;
import org.mapstruct.Mapper;

/**
 * 系统用户 mapstruct
 *
 * @author zhang.rx
 * @since 2024/2/20
 */
@Mapper(componentModel = "Spring")
public interface SysUserMap {

	SysUser dto2Entity(SysUserRequest request);

	SysUserResponse entity2Dto(SysUser sysUser);

}
