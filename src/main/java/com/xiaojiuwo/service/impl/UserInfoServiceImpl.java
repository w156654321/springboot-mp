package com.xiaojiuwo.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiaojiuwo.model.po.UserInfo;
import com.xiaojiuwo.mapper.UserInfoMapper;
import com.xiaojiuwo.model.vo.UserInfoReq;
import com.xiaojiuwo.model.vo.UserInfoRes;
import com.xiaojiuwo.service.IUserInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author auth
 * @since 2022-01-05
 */
@Service
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo> implements IUserInfoService {

    @Autowired
    private UserInfoMapper userInfoMapper;

    @Override
    public UserInfoRes queryUser(UserInfoReq userInfoReq) {
        UserInfo user = userInfoMapper.selectByMobile(userInfoReq.getMobile());
        LambdaQueryWrapper<UserInfo> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserInfo::getMobile,userInfoReq.getMobile());
        UserInfo userInfo = this.getOne(wrapper);
        if(null==userInfo){
            return null;
        }
        UserInfoRes userInfoRes = BeanUtil.copyProperties(userInfo, UserInfoRes.class);
        return userInfoRes;
    }

    @Override
    public void saveUser(UserInfoReq userInfoReq) {
        UserInfo userInfo = BeanUtil.copyProperties(userInfoReq, UserInfo.class);
        this.save(userInfo);
    }

    @Override
    public void updateUser(UserInfoReq userInfoReq) {
        UserInfo userInfo = BeanUtil.copyProperties(userInfoReq, UserInfo.class);
        userInfo.setUpdateUser(1L);
        this.updateById(userInfo);
    }

    /**
     * 分页
     * @param userInfoReq
     * @return
     */
    @Override
    public IPage<UserInfoRes> userPage(UserInfoReq userInfoReq) {
        LambdaQueryWrapper<UserInfo> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(StringUtils.isNotBlank(userInfoReq.getMobile()),UserInfo::getMobile,userInfoReq.getMobile());
        IPage pageInfo = new Page<>(userInfoReq.getCurrent(),userInfoReq.getSize());
        pageInfo = this.page(pageInfo,wrapper);
        List<UserInfo> records = pageInfo.getRecords();
        List<UserInfoRes> userInfoRes = BeanUtil.copyToList(records, UserInfoRes.class);
        pageInfo.setRecords(userInfoRes);
        return pageInfo;
    }

    /**
     * 自定义分页
     * @param userInfoReq
     * @return
     */
    @Override
    public IPage<UserInfoRes> userPageXml(UserInfoReq userInfoReq) {
        IPage pageInfo = new Page<>(userInfoReq.getCurrent(),userInfoReq.getSize());
        pageInfo = userInfoMapper.userPageXml(pageInfo,userInfoReq.getMobile());
        List<UserInfo> records = pageInfo.getRecords();
        List<UserInfoRes> userInfoRes = BeanUtil.copyToList(records, UserInfoRes.class);
        pageInfo.setRecords(userInfoRes);
        return pageInfo;
    }

    @Override
    public void delUser(UserInfoReq userInfoReq) {
        this.removeById(userInfoReq.getId());
    }

}
