package com.zrx.mapper;

import com.mybatisflex.core.BaseMapper;
import com.zrx.model.entity.OjProblem;
import org.apache.ibatis.annotations.Update;

/**
 * 题目 映射层。
 *
 * @author zhang.rx
 * @since 2024/3/20
 */
public interface OjProblemMapper extends BaseMapper<OjProblem> {

    @Update("UPDATE oj_problem SET submit_num = IFNULL(submit_num, 0) + 1 WHERE id = #{id}")
    void incrementSubmitNum(Long id);

}
