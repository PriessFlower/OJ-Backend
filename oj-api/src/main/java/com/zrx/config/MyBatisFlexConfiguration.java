package com.zrx.config;

import com.mybatisflex.core.FlexGlobalConfig;
import com.mybatisflex.spring.boot.MyBatisFlexCustomizer;
import com.zrx.model.entity.OjPost;
import com.zrx.model.entity.OjProblem;
import com.zrx.sys.entity.SysFile;
import com.zrx.sys.model.entity.SysRole;
import com.zrx.sys.model.entity.SysUser;
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