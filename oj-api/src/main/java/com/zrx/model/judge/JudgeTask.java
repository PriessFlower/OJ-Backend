package com.zrx.model.judge;

import com.zrx.codesandbox.model.ExecuteCodeResponse;
import com.zrx.model.entity.OjProblem;
import com.zrx.model.entity.OjProblemSubmit;
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
