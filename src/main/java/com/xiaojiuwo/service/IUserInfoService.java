package com.xiaojiuwo.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xiaojiuwo.model.po.UserInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaojiuwo.model.vo.UserInfoReq;
import com.xiaojiuwo.model.vo.UserInfoRes;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author auth
 * @since 2022-01-05
 */
public interface IUserInfoService extends IService<UserInfo> {

    UserInfoRes queryUser(UserInfoReq userInfoReq);

    void saveUser(UserInfoReq userInfoReq);

    void updateUser(UserInfoReq userInfoReq);

    IPage<UserInfoRes> userPage(UserInfoReq userInfoReq);

    IPage<UserInfoRes> userPageXml(UserInfoReq userInfoReq);

    void delUser(UserInfoReq userInfoReq);

}
