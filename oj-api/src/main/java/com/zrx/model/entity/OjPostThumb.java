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
import java.time.LocalDateTime;

/**
 * 帖子点赞 实体类。
 *
 * @author zhang.rx
 * @since 2024/5/13
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "帖子点赞")
@Table(value = "oj_post_thumb")
public class OjPostThumb implements Serializable {

	@Serial
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@Id(keyType = KeyType.Generator, value = KeyGenerators.snowFlakeId)
	@Schema(description = "id")
	private Long id;

	/**
	 * 帖子 id
	 */
	@Schema(description = "帖子 id")
	private Long postId;

	/**
	 * 创建用户 id
	 */
	@Schema(description = "创建用户 id")
	private Long userId;

	/**
	 * 创建时间
	 */
	@Column(onInsertValue = "now()")
	@Schema(description = "创建时间")
	private LocalDateTime createTime;

}
