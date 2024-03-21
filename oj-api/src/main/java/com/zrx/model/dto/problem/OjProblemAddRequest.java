package com.zrx.model.dto.problem;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 题目请求体
 *
 * @author zhang.rx
 * @since 2024/3/20
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Schema(name = "题目请求体")
public class OjProblemAddRequest extends OjProblemRequest implements Serializable {

}
