package com.zt.model.judge;

import com.zt.codesandbox.model.ExecuteCodeResponse;
import com.zt.model.entity.OjProblem;
import com.zt.model.entity.OjProblemSubmit;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JudgeTask {

	// 判题的目标题目
	public OjProblem ojProblem;

	// 代码运行结果
	public ExecuteCodeResponse executeCodeResponse;

	// 代码提交信息
	public OjProblemSubmit ojProblemSubmit;

}
