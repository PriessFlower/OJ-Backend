package com.zrx.codesandbox.model;

import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class ExecuteCodeRequest {

	private List<String> inputList;

	private String code;

	private String language;

}
