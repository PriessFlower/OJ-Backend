package com.zrx.mapstruct;

import com.zrx.model.dto.problemSubmit.OjProblemSubmitVo;
import com.zrx.model.entity.OjProblemSubmit;
import org.mapstruct.Mapper;

/**
 * @author zhang.rx
 * @since 2024/5/8
 */
@Mapper(componentModel = "Spring")
public interface ProblemSubmitConverter {

    OjProblemSubmitVo toVo(OjProblemSubmit entity);

}
