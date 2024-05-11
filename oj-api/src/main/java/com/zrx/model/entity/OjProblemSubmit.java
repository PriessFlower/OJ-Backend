package com.zrx.model.entity;

import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import com.mybatisflex.core.keygen.KeyGenerators;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

//@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "题目")
@Table(value = "oj_problem_submit")
public class OjProblemSubmit implements Serializable {

	@Serial
	private static final long serialVersionUID = 1L;

	@Id(keyType = KeyType.Generator, value = KeyGenerators.snowFlakeId)
	@Schema(description = "id")
	private Long id;

	@Schema(description = "编程语言")
	private String language;

	@Schema(description = "用户代码")
	private String code;

	@Schema(description = "代码执行状态（详见ProblemJudgeResultEnum）")
	private Integer codeStatus;

	@Schema(description = "题目实际输出（json）")
	private String output;

	@Schema(description = "判题信息（json 对象）")
	private String judgeInfo;

	@Schema(description = "判题状态（0 - 待判题、1 - 判题中、2 - 成功、3 - 失败）")
	private Integer status;

	@Schema(description = "题目 id")
	private Long questionId;

	@Schema(description = "创建用户id")
	private Long userId;

	@Column(onInsertValue = "now()")
	@Schema(description = "创建时间")
	private Date createTime;

	@Schema(description = "更新时间")
	private Date updateTime;

	@Schema(description = "是否删除")
	@Column(isLogicDelete = true)
	private Integer isDelete;

}
