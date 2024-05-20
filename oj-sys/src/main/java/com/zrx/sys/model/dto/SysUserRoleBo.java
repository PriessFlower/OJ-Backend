package com.zrx.sys.model.dto;

import lombok.Data;

import java.util.List;

/**
 * @author zhang.rx
 * @since 2024/5/10
 */
@Data
public class SysUserRoleBo {

	private Long userId;

	private List<String> remark;

}
