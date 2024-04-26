package com.zrx.sys.model.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.core.keygen.KeyGenerators;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 系统用户响应体
 *
 * @author zhang.rx
 * @since 2024/2/20
 */
@Data
@Schema(description = "系统用户响应体")
public class SysUserResponse {

	/**
	 * 主键
	 */
	@Id(keyType = KeyType.Generator, value = KeyGenerators.snowFlakeId)
	@Schema(description = "主键")
	private Long id;

	/**
	 * 用户名
	 */
	@Schema(description = "用户名")
	private String username;

	/**
	 * 昵称
	 */
	@Schema(description = "昵称")
	private String nickName;

	/**
	 * 姓名
	 */
	@Schema(description = "姓名")
	private String realName;

	/**
	 * 头像url
	 */
	@Schema(description = "头像url")
	private String avatar;

	/**
	 * 性别: 0:男; 1:女; 2:保密
	 */
	@Schema(description = "性别: 0:男; 1:女; 2:保密")
	private Integer gender;

	/**
	 * 邮箱
	 */
	@Schema(description = "邮箱")
	private String email;

	/**
	 * 手机号
	 */
	@Schema(description = "手机号")
	private String mobile;

	/**
	 * 部门ID
	 */
	@Schema(description = "部门ID")
	private String deptId;

	/**
	 * 状态: 0:停用; 1:正常
	 */
	@Schema(description = "状态: 0:停用; 1:正常")
	private Integer status;

	/**
	 * 注册时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Schema(description = "注册时间")
	private LocalDateTime registerTime;

	/**
	 * 个人简介
	 */
	@Schema(description = "个人简介")
	private String introduce;

	/**
	 * HACK: 权限最大的角色
	 */
	@Schema(description = "权限最大的角色")
	private String role;

	/**
	 * 角色
	 */
	@Schema(description = "角色")
	private List<String> roles;

	/**
	 * 是否在线
	 */
	@Schema(description = "是否在线")
	private Boolean online;

}
