package com.zrx.utils;

import cn.hutool.core.collection.CollUtil;
import com.zrx.enums.PostZoneEnums;
import com.zrx.model.vo.OjPostVo;
import com.zrx.sys.mapper.SysUserMapper;
import com.zrx.sys.model.entity.SysUser;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author zhang.rx
 * @since 2024/5/17
 */
@Component
public class PostUtil {

	@Resource
	private SysUserMapper sysUserMapper;

	public void setPostAuthor(List<OjPostVo> ojPostVos) {
		if (CollUtil.isNotEmpty(ojPostVos)) {
			Map<Long, List<OjPostVo>> map = ojPostVos.stream().collect(Collectors.groupingBy(OjPostVo::getCreator));
			Map<Long, List<SysUser>> userMap = sysUserMapper.selectListByIds(map.keySet())
				.stream()
				.collect(Collectors.groupingBy(SysUser::getId));
			for (Map.Entry<Long, List<OjPostVo>> post : map.entrySet()) {
				Long userId = post.getKey();
				List<OjPostVo> posts = post.getValue();
				for (OjPostVo ojPostVo : posts) {
					List<SysUser> users = userMap.get(userId);
					if (CollUtil.isNotEmpty(users)) {
						SysUser user = users.get(0);
						ojPostVo.setCreatorName(user.getNickName());
						ojPostVo.setAvatar(user.getAvatar());
						ojPostVo.setIntroduce(user.getIntroduce());
					}
				}
			}
		}
	}

	public void setPostZoneName(List<OjPostVo> ojPostVos) {
		if (CollUtil.isNotEmpty(ojPostVos)) {
			for (OjPostVo ojPostVo : ojPostVos) {
				ojPostVo.setZoneName(PostZoneEnums.getTextByValue(ojPostVo.getZone()));
			}
		}
	}

}
