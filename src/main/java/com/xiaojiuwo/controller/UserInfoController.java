package com.xiaojiuwo.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xiaojiuwo.common.ApiResult;
import com.xiaojiuwo.exception.ServiceException;
import com.xiaojiuwo.model.vo.UserInfoReq;
import com.xiaojiuwo.model.vo.UserInfoRes;
import com.xiaojiuwo.service.IUserInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author auth
 * @since 2022-01-05
 */
@RestController
@RequestMapping("/useInfo")
@Api(tags = "用户服务")
@Slf4j
public class UserInfoController {

    @Autowired
    private IUserInfoService userInfoService;

    @ApiOperation(value = "查询用户", notes = "查询用户")
    @GetMapping("/queryUser")
    public ApiResult<UserInfoRes> queryUser(@Valid @RequestBody UserInfoReq userInfoReq){
        UserInfoRes userInfoRes = userInfoService.queryUser(userInfoReq);
        return ApiResult.success(userInfoRes);
    }

    @ApiOperation(value = "新增用户", notes = "新增用户")
    @PostMapping("/saveUser")
    public ApiResult saveUser(@Valid @RequestBody UserInfoReq userInfoReq){
        userInfoService.saveUser(userInfoReq);
        return ApiResult.success();
    }

    @ApiOperation(value = "修改用户", notes = "修改用户")
    @PutMapping("/updateUser")
    public ApiResult updateUser(@Valid @RequestBody UserInfoReq userInfoReq){
        userInfoService.updateUser(userInfoReq);
        return ApiResult.success();
    }

    @ApiOperation(value = "删除用户", notes = "删除用户")
    @DeleteMapping("/delUser")
    public ApiResult delUser(@Valid @RequestBody UserInfoReq userInfoReq){
        userInfoService.delUser(userInfoReq);
        return ApiResult.success();
    }

    @ApiOperation(value = "分页查询用户", notes = "分页查询用户")
    @GetMapping("/userPage")
    public ApiResult<IPage<UserInfoRes>> userPage(@Valid @RequestBody UserInfoReq userInfoReq){
        IPage<UserInfoRes> userInfoResIPage = userInfoService.userPage(userInfoReq);
        return ApiResult.success(userInfoResIPage);
    }

    @ApiOperation(value = "分页查询用户", notes = "分页查询用户")
    @GetMapping("/userPageXml")
    public ApiResult<IPage<UserInfoRes>> userPageXml(@Valid @RequestBody UserInfoReq userInfoReq){
        IPage<UserInfoRes> userInfoResIPage = userInfoService.userPageXml(userInfoReq);
        return ApiResult.success(userInfoResIPage);
    }

}

