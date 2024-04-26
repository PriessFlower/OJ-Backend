package com.zrx.service.impl;

import com.mybatisflex.core.query.QueryChain;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
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
import java.util.stream.Collectors;

import static com.zrx.model.entity.table.ProblemCommentTableDef.PROBLEM_COMMENT;

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

    @Override
    public Boolean save(ProblemCommentRequest req) {
        SysUser user = SecurityHelper.getUser();
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
        return findUserInfo(commentMapstruct.toVoList(problemComments));
    }

    /**
     * 获取用户信息
     */
    private List<ProblemCommentVo> findUserInfo(List<ProblemCommentVo> voList) {
        Map<Long, List<ProblemCommentVo>> map =
                voList.stream().collect(Collectors.groupingBy(ProblemCommentVo::getAuthorId));
        map.forEach((k, v) -> {
            SysUser user = userMapper.selectOneById(k);
            v.forEach(e -> {
                e.setAuthorAvatar(user.getAvatar());
                e.setAuthorName(user.getNickName());
            });
        });
        return map.values().stream().flatMap(List::stream).toList();
    }

    @Override
    public List<ProblemCommentVo> listChildren(Long parentId) {
        List<ProblemComment> list = QueryChain.of(mapper)
                .where(PROBLEM_COMMENT.PARENT_ID.eq(parentId))
                .list();
        return findUserInfo(commentMapstruct.toVoList(list));
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
