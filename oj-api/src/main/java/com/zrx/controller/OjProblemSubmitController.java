package com.zrx.controller;

//import com.zrx.exception.BusinessException;
import com.mybatisflex.core.paginate.Page;
import com.zrx.exception.BusinessException;
import com.zrx.model.dto.problemSubmit.ProblemSubmitAddRequest;
import com.zrx.model.vo.OjProblemPageVo;
import com.zrx.reuslt.Result;
import com.zrx.reuslt.ResultCode;
import com.zrx.security.utils.SecurityHelper;
import com.zrx.service.OjProblemSubmitService;
import com.zrx.sys.model.entity.SysUser;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
//import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "OjProblemSubmit", description = "题目提交管理")
@RequestMapping("/submit")
public class OjProblemSubmitController {


    @Autowired
    private OjProblemSubmitService ojProblemSubmitService;


    @PostMapping("/")
    public Result<Long> doQuestionSubmit(@RequestBody ProblemSubmitAddRequest problemSubmitAddRequest,
                                         HttpServletRequest request) throws Exception {
        if (problemSubmitAddRequest == null || problemSubmitAddRequest.getQuestionId() <= 0) {
            throw new BusinessException(ResultCode.PARAM_ERROR);
        }
        SysUser user = SecurityHelper.getUser();
        Long problemSubmitId = ojProblemSubmitService.doSubmit(problemSubmitAddRequest,user);
        return Result.success(problemSubmitId);
    }





}
