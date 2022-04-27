package com.xiaojiuwo;

import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.annotation.XxlJob;
import com.xxl.job.core.log.XxlJobLogger;
import org.springframework.stereotype.Component;

/**
 * @Author: liudh
 * @Date: 2022/3/18 13:37
 */
@Component
public class TestJob {


    @XxlJob(value ="testJob")
    public ReturnT<String> testJob(String param) throws Exception {
        XxlJobLogger.log("refreshTokenJob开始:{}",param);
        XxlJobLogger.log("refreshTokenJob返回");
        return ReturnT.SUCCESS;
    }


}

