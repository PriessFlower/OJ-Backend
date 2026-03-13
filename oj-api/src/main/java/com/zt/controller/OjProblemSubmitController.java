package com.zt.controller;

import com.mybatisflex.core.paginate.Page;
import com.zt.model.common.Paging;
import com.zt.model.dto.problemSubmit.OjProblemSubmitQueryRequest;
import com.zt.model.dto.problemSubmit.OjProblemSubmitVo;
import com.zt.model.dto.problemSubmit.ProblemSubmitAddRequest;
import com.zt.reuslt.Result;
import com.zt.security.utils.SecurityHelper;
import com.zt.service.OjProblemSubmitService;
import com.zt.sys.model.entity.SysUser;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@Tag(name = "OjProblemSubmit", description = "题目提交管理")
@RequestMapping("/submit")
public class OjProblemSubmitController {

	@Resource
	private OjProblemSubmitService ojProblemSubmitService;

	@PostMapping("/")
	@Operation(summary = "提交题目")
	public Result<Long> doQuestionSubmit(@Valid @RequestBody ProblemSubmitAddRequest problemSubmitAddRequest)
			throws Exception {
		SysUser user = SecurityHelper.getUser();
		Long problemSubmitId = ojProblemSubmitService.doSubmit(problemSubmitAddRequest, user);
		return Result.success(problemSubmitId);
	}

	@GetMapping("/getInfo/{id}")
	@Operation(summary = "根据主键获取题目提交信息")
	public Result<OjProblemSubmitVo> getInfo(@PathVariable Long id) {
		return Result.success(ojProblemSubmitService.getInfoById(id));
	}

	@PostMapping("/pageInfoByUserId/{id}")
	@Operation(summary = "根据用户id分页查询题目提交信息列表")
	public Result<Page<OjProblemSubmitVo>> pageInfoByUserId(@PathVariable Long id,
			@RequestBody OjProblemSubmitQueryRequest req, Paging paging) {
		return Result.success(ojProblemSubmitService.pageInfoByUserId(id, req, paging));
	}

}
