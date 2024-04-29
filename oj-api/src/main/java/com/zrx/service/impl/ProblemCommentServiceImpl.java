package com.zrx.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.google.common.collect.Sets;
import com.mybatisflex.core.query.QueryChain;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.zrx.exception.BusinessException;
import com.zrx.mapper.OjProblemMapper;
import com.zrx.mapper.ProblemCommentMapper;
import com.zrx.mapstruct.ProblemCommentMapstruct;
import com.zrx.model.dto.problemComment.ProblemCommentRequest;
import com.zrx.model.entity.ProblemComment;
import com.zrx.model.vo.ProblemCommentVo;
import com.zrx.security.utils.SecurityHelper;
import com.zrx.service.ProblemCommentService;
import com.zrx.sys.mapper.SysUserMapper;
import com.zrx.sys.model.entity.SysUser;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static com.zrx.model.entity.table.OjProblemTableDef.OJ_PROBLEM;
import static com.zrx.model.entity.table.ProblemCommentTableDef.PROBLEM_COMMENT;
import static com.zrx.sys.model.entity.table.SysUserTableDef.SYS_USER;

/**
 * 题目评论 服务层实现。
 *
 * @author zhang.rx
 * @since 2024/4/18
 */
@Service
public class ProblemCommentServiceImpl extends ServiceImpl<ProblemCommentMapper, ProblemComment> implements ProblemCommentService {

    @Resource
    private ProblemCommentMapstruct commentMapstruct;

    @Resource
    private SysUserMapper userMapper;

    @Resource
    private OjProblemMapper problemMapper;

    @Override
    public Boolean save(ProblemCommentRequest req) {
        SysUser user = SecurityHelper.getUser();
        long count = problemMapper.selectCountByCondition(OJ_PROBLEM.ID.eq(req.getProblemId(), req.getProblemId() != null));
        if (count == 0) {
            throw new BusinessException("题目不存在");
        }
        ProblemComment entity = commentMapstruct.toEntity(req);
        entity.setAuthorId(user.getId());
        return save(entity);
    }

    @Override
    public List<ProblemCommentVo> list(Long problemId) {
        QueryWrapper queryWrapper = new QueryWrapper()
                .where(PROBLEM_COMMENT.PROBLEM_ID.eq(problemId, problemId != null))
                .and(PROBLEM_COMMENT.PARENT_ID.isNull());
        List<ProblemComment> problemComments = mapper.selectListByQuery(queryWrapper);
        List<ProblemCommentVo> voList = commentMapstruct.toVoList(problemComments);
        Set<Long> userIds = Sets.newHashSet();
        // 查询所有子节点
        voList.forEach(e -> listChildren(e, userIds));
        findUserInfo(voList, userIds);
        return voList;
    }

    /**
     * 获取用户信息
     */
    private void findUserInfo(List<ProblemCommentVo> voList, Set<Long> userIds) {
        List<SysUser> sysUsers = userMapper.selectListByCondition(SYS_USER.ID.in(userIds));
        setUserInfo(voList, sysUsers);
    }

    /**
     * 递归set用户信息
     */
    private void setUserInfo(List<ProblemCommentVo> voList, List<SysUser> sysUsers) {
        if (CollUtil.isNotEmpty(voList)) {
            voList.forEach(item -> {
                SysUser sysUser = sysUsers.stream().filter(e -> e.getId().equals(item.getAuthorId())).findFirst().get();
                item.setAuthorName(sysUser.getNickName());
                item.setAuthorAvatar(sysUser.getAvatar());
                setUserInfo(item.getChildren(), sysUsers);
            });
        }
    }

    /**
     * 递归查询子节点
     */
    private void listChildren(ProblemCommentVo e, Set<Long> userIds) {
        userIds.add(e.getAuthorId());
        List<ProblemComment> list = QueryChain.of(mapper)
                .where(PROBLEM_COMMENT.PARENT_ID.eq(e.getId()))
                .list();
        if (CollUtil.isEmpty(list)) {
            return;
        }
        e.setChildren(commentMapstruct.toVoList(list));
        e.getChildren().forEach(child -> listChildren(child, userIds));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean removeRecursionById(Serializable id) {
        removeById(id);
        removeChildren(id);
        return true;
    }

    private void removeChildren(Serializable parentId) {
        List<Long> childrenIds = QueryChain.of(mapper)
                .select(PROBLEM_COMMENT.ID)
                .where(PROBLEM_COMMENT.PARENT_ID.eq(parentId))
                .listAs(Long.class);
        for (Long childrenId : childrenIds) {
            removeById(childrenId);
            removeChildren(childrenId);
        }
    }

}
