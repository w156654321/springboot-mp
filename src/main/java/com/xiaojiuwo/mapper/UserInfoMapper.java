package com.xiaojiuwo.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiaojiuwo.model.po.UserInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author auth
 * @since 2022-01-05
 */
public interface UserInfoMapper extends BaseMapper<UserInfo> {

     UserInfo selectByMobile(@Param("mobile")String mobile);

     IPage<UserInfo> userPageXml(IPage page,String mobile);

}
