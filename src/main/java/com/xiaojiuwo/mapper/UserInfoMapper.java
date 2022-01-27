package com.xiaojiuwo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xiaojiuwo.model.po.UserInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

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

     List<Map<String,Object>> queryCommodity();

}
