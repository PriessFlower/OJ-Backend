package com.zrx.codesandbox.model;

import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class ExecuteCodeResponse {

	private List<String> ouputList;

	private String message;

	private Integer status;

	private JudgeInfo judgeInfo;

}
