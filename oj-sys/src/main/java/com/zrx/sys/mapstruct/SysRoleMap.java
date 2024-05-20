package com.zrx.sys.mapstruct;

import com.mybatisflex.core.paginate.Page;
import com.zrx.sys.model.dto.SysRoleRequest;
import com.zrx.sys.model.entity.SysRole;
import com.zrx.sys.model.vo.SysRoleResponse;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * @author zhang.rx
 * @since 2024/4/24
 */
@Mapper(componentModel = "Spring")
public interface SysRoleMap {

	SysRole toEntity(SysRoleRequest req);

	SysRoleResponse toVo(SysRole entity);

	List<SysRoleResponse> toVoList(List<SysRole> entityList);

	Page<SysRoleResponse> toVoPage(Page<SysRole> entityPage);

}
