package com.xiaojiuwo;

import com.xiaojiuwo.service.IUserInfoService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = XiaojiuwoDemoApplication.class)
//由于是Web项目，Junit需要模拟ServletContext，因此我们需要给我们的测试类加上@WebAppConfiguration。
@WebAppConfiguration
public class XiaojiuwoDemoApplicationTests {

    @Autowired
    private IUserInfoService userInfoService;

    @Test
    public void insertUserInfo() {
        userInfoService.insertUserInfo();
    }

    @Test
    public void copyUserInfo() {
        userInfoService.copyUserInfo();
    }

    @Test
    public void copyUserInfoOne() {
        userInfoService.copyUserInfoOne();
    }

}
