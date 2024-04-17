package com.zrx.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.core.util.StringUtil;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.zrx.config.flex.MybatisFlexFunc;
import com.zrx.exception.BusinessException;
import com.zrx.mapper.OjProblemMapper;
import com.zrx.mapstruct.OjProblemConverter;
import com.zrx.model.common.Paging;
import com.zrx.model.dto.problem.OjProblemAddRequest;
import com.zrx.model.dto.problem.OjProblemQueryRequest;
import com.zrx.model.dto.problem.OjProblemUpdateRequest;
import com.zrx.model.entity.OjProblem;
import com.zrx.model.vo.OjProblemPageVo;
import com.zrx.model.vo.OjProblemVo;
import com.zrx.service.OjProblemService;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;

import static com.zrx.model.entity.table.OjProblemTableDef.OJ_PROBLEM;

/**
 * 题目 服务层实现。
 *
 * @author zhang.rx
 * @since 2024/3/20
 */
@Service
public class OjProblemServiceImpl extends ServiceImpl<OjProblemMapper, OjProblem> implements OjProblemService {

	@Override
	public Boolean save(OjProblemAddRequest req) {
		OjProblem ojProblem = OjProblemConverter.addDto2Entity(req);
		mapper.insert(ojProblem);
		return true;
	}

	@Override
	public Boolean updateById(OjProblemUpdateRequest req) {
		OjProblem ojProblem = OjProblemConverter.updateDto2Entity(req);
		int updateFlag = mapper.update(ojProblem);
		if (updateFlag == 0) {
			throw new BusinessException("更新失败");
		}
		return true;
	}

	@Override
	public OjProblemVo getInfoById(Serializable id) {
		OjProblem ojProblem = mapper.selectOneById(id);
		if (ojProblem == null) {
			throw new BusinessException("当前题目不存在");
		}
		return OjProblemConverter.entity2Vo(ojProblem);
	}

	@Override
	public Page<OjProblemPageVo> page(Paging page, OjProblemQueryRequest req) {
		String title = req.getTitle();
		List<String> tags = req.getTags();
		Integer difficulty = req.getDifficulty();

		QueryWrapper queryWrapper = new QueryWrapper();
		queryWrapper.select(OJ_PROBLEM.ALL_COLUMNS)
			.where(OJ_PROBLEM.TITLE.like(title, StringUtil::isNotBlank))
			.and(OJ_PROBLEM.DIFFICULTY.eq(difficulty, ObjectUtil::isNotNull))
			.orderBy(MybatisFlexFunc.coalesce(OJ_PROBLEM.UPDATE_TIME, OJ_PROBLEM.CREATE_TIME), false);
		if (CollUtil.isNotEmpty(tags)) {
			for (String tag : tags) {
				queryWrapper.and(OJ_PROBLEM.TAGS.like("\"" + tag + "\""));
			}
		}
		Page<OjProblem> ojProblemPage = mapper.paginate(Page.of(page.getPageNum(), page.getPageSize()), queryWrapper);
		Page<OjProblemPageVo> resPage = new Page<>();
		List<OjProblemPageVo> voList = ojProblemPage.getRecords()
			.stream()
			.map(OjProblemConverter::entity2VoPage)
			.toList();

		resPage.setRecords(voList);
		resPage.setTotalPage(ojProblemPage.getTotalPage());
		resPage.setPageNumber(ojProblemPage.getPageNumber());
		resPage.setPageSize(ojProblemPage.getPageSize());
		resPage.setTotalRow(ojProblemPage.getTotalRow());
		return resPage;
	}

}
