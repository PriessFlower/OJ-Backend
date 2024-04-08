package com.zrx.model.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.zrx.config.excel.StringListConverter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * 题目响应体
 *
 * @author zhang.rx
 * @since 2024/3/20
 */
@Data
@Schema(description = "题目响应体-分页查询")
@ExcelIgnoreUnannotated
public class OjProblemPageVo {

	/**
	 * id
	 */
	@Schema(description = "id")
	private Long id;

	/**
	 * 标题
	 */
	@ColumnWidth(10)
	@ExcelProperty(value = "标题", index = 0)
	@Schema(description = "标题")
	private String title;

	/**
	 * 内容
	 */
	@ColumnWidth(10)
	@ExcelProperty(value = "内容", index = 1)
	@Schema(description = "内容")
	private String content;

	/**
	 * 标签列表（json 数组）
	 */
	@ExcelProperty(value = "标签", index = 2, converter = StringListConverter.class)
	@Schema(description = "标签列表（json 数组）")
	private List<String> tags;

	/**
	 * 题目难度，简单，中等，困难
	 */
	@ColumnWidth(15)
	@ExcelProperty(value = "题目难度", index = 3)
	@Schema(description = "题目难度，简单，中等，困难")
	private String difficulty;

	/**
	 * 题目提交数
	 */
	@ColumnWidth(20)
	@ExcelProperty(value = "题目提交数", index = 4)
	@Schema(description = "题目提交数")
	private Integer submitNum;

	/**
	 * 题目通过数
	 */
	@ColumnWidth(20)
	@ExcelProperty(value = "题目通过数", index = 5)
	@Schema(description = "题目通过数")
	private Integer acceptedNum;

	/**
	 * 点赞数
	 */
	@ColumnWidth(12)
	@ExcelProperty(value = "点赞数", index = 6)
	@Schema(description = "点赞数")
	private Integer thumbNum;

	/**
	 * 收藏数
	 */
	@ColumnWidth(12)
	@ExcelProperty(value = "收藏数", index = 7)
	@Schema(description = "收藏数")
	private Integer favourNum;

}
