package com.xiaojiuwo.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.xiaojiuwo.enums.GenderEnum;
import com.xiaojiuwo.mapper.UserInfoMapper;
import com.xiaojiuwo.model.po.UserInfo;
import com.xiaojiuwo.model.vo.UserInfoReq;
import com.xiaojiuwo.model.vo.UserInfoRes;
import com.xiaojiuwo.service.IUserInfoService;
import com.xiaojiuwo.util.RandomValue;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;

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
    @Autowired
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;

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

    @Override
    public void copyUserInfo(){
        List<UserInfo> userInfoList = this.list();
        long start = System.currentTimeMillis();
        ExecutorCompletionService<Integer> completionService = new ExecutorCompletionService<Integer>(
                threadPoolTaskExecutor);

        List<List<UserInfo>> partitionUserInfoList =  Lists.partition(userInfoList, 3000);
        partitionUserInfoList.forEach(row->{
            Callable callable = (() ->
                    userInfoMapper.saveInfo(row)
            );
            completionService.submit(callable);
        });
        //不需要结果可不写
        partitionUserInfoList.forEach(row->{
            try {
                Integer result =  completionService.take().get();
                System.out.println(result);
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        });
        long end = System.currentTimeMillis();
        System.out.println("任务耗时：" + (end - start) + "毫秒");
    }

    @Override
    public void insertUserInfo(){
        long start = System.currentTimeMillis();
        List<UserInfo> userInfoList = new ArrayList<>();
        for (int i = 0; i<=1000;i++){
            UserInfo userInfo = new UserInfo();
            Map map = RandomValue.getAddress();
            userInfo.setName(String.valueOf(map.get("name")));
            userInfo.setGender( String.valueOf(map.get("sex")).equals("男")? GenderEnum.MAN:GenderEnum.WOMAN);
            userInfo.setMobile(String.valueOf(map.get("tel")));
            userInfoList.add(userInfo);
        }

        ExecutorCompletionService<Integer> completionService = new ExecutorCompletionService<Integer>(
                threadPoolTaskExecutor);

        List<List<UserInfo>> partitionUserInfoList =  Lists.partition(userInfoList, 30);
        partitionUserInfoList.forEach(row->{
            Callable callable = (() ->
                 userInfoMapper.saveInfo(row)
            );
            completionService.submit(callable);
        });
        //不需要结果可不写
        partitionUserInfoList.forEach(row->{
            try {
                Integer result =  completionService.take().get();
                System.out.println(result);
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        });
        long end = System.currentTimeMillis();
        System.out.println("任务耗时：" + (end - start) + "毫秒");

    }

    @Override
    public void copyUserInfoOne(){
        List<UserInfo> userInfoList = this.list();
        long start = System.currentTimeMillis();
        List<List<UserInfo>> partitionUserInfoList =  Lists.partition(userInfoList, 3000);
        partitionUserInfoList.forEach(row->{
            userInfoMapper.saveInfo(row);
        });
        long end = System.currentTimeMillis();
        System.out.println("任务耗时：" + (end - start) + "毫秒");
    }

}
