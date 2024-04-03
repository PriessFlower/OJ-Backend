package com.zrx.model.dto.problem;

import com.zrx.model.common.UpdateGroup;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

/**
 * 题目更新请求体
 *
 * @author zhang.rx
 * @since 2024/3/20
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "题目更新请求体")
public class OjProblemUpdateRequest extends OjProblemRequest implements Serializable {

	@Serial
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@Schema(description = "主键")
	@NotNull(message = "主键不能为空", groups = UpdateGroup.class)
	private Long id;

}
