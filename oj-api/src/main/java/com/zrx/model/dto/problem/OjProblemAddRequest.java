package com.zrx.model.dto.problem;

import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serializable;

/**
 * 题目添加请求体
 *
 * @author zhang.rx
 * @since 2024/3/20
 */
@Schema(description = "题目添加请求体")
public class OjProblemAddRequest extends OjProblemRequest implements Serializable {

}
