package com.zt.config;

import com.mybatisflex.core.FlexGlobalConfig;
import com.mybatisflex.spring.boot.MyBatisFlexCustomizer;
import com.zt.model.entity.OjPost;
import com.zt.model.entity.OjProblem;
import com.zt.sys.entity.SysFile;
import com.zt.sys.model.entity.SysRole;
import com.zt.sys.model.entity.SysUser;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MyBatisFlexConfiguration implements MyBatisFlexCustomizer {

	@Override
	public void customize(FlexGlobalConfig flexGlobalConfig) {
		InsertEntityToDBListener insertListener = new InsertEntityToDBListener();
		UpdateEntityToDBListener updateListener = new UpdateEntityToDBListener();
		// 为实体类设置新增监听器
		flexGlobalConfig.registerInsertListener(insertListener, OjProblem.class, SysFile.class, SysRole.class,
				OjPost.class);
		// 为实体类设置更新监听器
		flexGlobalConfig.registerUpdateListener(updateListener, OjProblem.class, SysUser.class, SysFile.class);
	}

}