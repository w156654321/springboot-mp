package com.xiaojiuwo.controller;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xiaojiuwo.common.ApiResult;
import com.xiaojiuwo.mapper.UserInfoMapper;
import com.xiaojiuwo.model.EsConstant;
import com.xiaojiuwo.model.vo.UserInfoReq;
import com.xiaojiuwo.model.vo.UserInfoRes;
import com.xiaojiuwo.service.EsService;
import com.xiaojiuwo.service.IUserInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MultiMatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @Autowired
    private EsService esService;
    @Autowired
    private UserInfoMapper userInfoMapper;

    @ApiOperation(value = "", notes = "")
    @GetMapping("/test")
    public ApiResult test() throws IOException {

        //List<ContentModel> list = new LinkedList();
        //ContentModel contentModel = new ContentModel();
        //contentModel.setContentId(110);
        //contentModel.setTitle("测试1");
        //list.add(contentModel);
        //ContentModel contentModel1 = new ContentModel();
        //contentModel1.setContentId(111);
        //contentModel1.setTitle("测试2");
        //list.add(contentModel1);
        //ContentModel contentModel2 = new ContentModel();
        //contentModel2.setContentId(112);
        //contentModel2.setTitle("测试2");
        //list.add(contentModel2);


        //List<Map> maps = JSONUtil.toList(str, Map.class);
        List<Map<String, Object>> mapsq = userInfoMapper.queryCommodity();
        for (Map map : mapsq) {
            esService.deleteIndex(String.valueOf(map.get("artNo")));
            esService.insertEsContent(EsConstant.CONTENT_INDEX,String.valueOf(map.get("artNo")), JSONUtil.toJsonStr(map));
        }


        MultiMatchQueryBuilder queryBuilder = QueryBuilders.multiMatchQuery("五粮液", "commodityName");
        //RangeQueryBuilder titleValue = QueryBuilders.rangeQuery("retailPrice").from(1000).to(1200); //范围查询
        RangeQueryBuilder rangeQueryBuilder = new RangeQueryBuilder("retailPrice").from(1000).to(1200);
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        boolQueryBuilder.filter(queryBuilder);
        boolQueryBuilder.filter(rangeQueryBuilder);

        //MultiMatchQueryBuilder queryBuilder = QueryBuilders.multiMatchQuery("不会", "videoTopic.alias");
        Map<String, Boolean> sortFieldsToAsc = new HashMap<>();
        String[] includeFields = new String[]{};
        String[] excludeFields = new String[]{};
        List<Map<String, Object>> maps = esService.searchIndex(EsConstant.CONTENT_INDEX, 0, 2000, boolQueryBuilder, sortFieldsToAsc, includeFields, excludeFields, 60);
        log.info("返回：{}", JSONUtil.toJsonStr(maps));

        return ApiResult.success(maps);
    }

}

