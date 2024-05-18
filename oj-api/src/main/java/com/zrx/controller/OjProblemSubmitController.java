package com.zrx.controller;

import com.mybatisflex.core.paginate.Page;
import com.zrx.model.common.Paging;
import com.zrx.model.dto.problemSubmit.OjProblemSubmitQueryRequest;
import com.zrx.model.dto.problemSubmit.OjProblemSubmitVo;
import com.zrx.model.dto.problemSubmit.ProblemSubmitAddRequest;
import com.zrx.reuslt.Result;
import com.zrx.security.utils.SecurityHelper;
import com.zrx.service.OjProblemSubmitService;
import com.zrx.sys.model.entity.SysUser;
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
