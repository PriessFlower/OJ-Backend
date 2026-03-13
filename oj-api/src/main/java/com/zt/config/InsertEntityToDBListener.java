package com.zt.config;

import com.mybatisflex.annotation.InsertListener;
import com.zt.exception.BusinessException;
import com.zt.model.common.BaseEntity;
import com.zt.security.utils.SecurityHelper;
import com.zt.sys.model.entity.SysUser;

import java.time.LocalDateTime;

/**
 * 实体类新增到数据库监听器
 *
 * @author zrx
 * @since 2024/1/30 13:51
 */
public class InsertEntityToDBListener implements InsertListener {

	@Override
	public void onInsert(Object o) {
		BaseEntity baseEntity = (BaseEntity) o;
		SysUser loginUser = SecurityHelper.getUser();
		Long userId = loginUser.getId();
		if (userId == null) {
			throw new BusinessException("请登录");
		}
		baseEntity.setCreator(String.valueOf(userId));
		baseEntity.setCreateTime(LocalDateTime.now());
	}

}
