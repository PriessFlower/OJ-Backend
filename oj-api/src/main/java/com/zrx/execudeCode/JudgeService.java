package com.zrx.execudeCode;

import com.zrx.codesandbox.model.JudgeInfo;
import com.zrx.model.entity.OjProblemSubmit;

import java.io.IOException;

public interface JudgeService {


     void doJudge(Long ojProblemSubmitId) throws Exception;
}
