package com.xiaojiuwo.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xiaojiuwo.common.ApiResult;
import com.xiaojiuwo.model.EsConstant;
import com.xiaojiuwo.model.vo.UserInfoReq;
import com.xiaojiuwo.model.vo.UserInfoRes;
import com.xiaojiuwo.service.EsService;
import com.xiaojiuwo.service.IUserInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.index.query.MultiMatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
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

        //String str ="[{\"sizeHD\":0,\"videoTopic\":{\"ename\":\"T1460515713131\",\"tname\":\"面呆围笑\",\"alias\":\"爱笑的人运气不会太差\",\"topic_icons\":\"http://img2.cache.netease.com/m/newsapp/topic_icons/T1460515713131.png\",\"tid\":\"T1460515713131\"},\"upTimes\":0,\"mp4Hd_url\":null,\"description\":\"\",\"title\":\"两个肉嘟嘟的小汪打架，可爱\",\"mp4_url\":\"http://flv3.bn.netease.com/50a19b2c1054f3ed53e6adb3270c01f80808ea367d1d9f5276fe40cc5738bb29de033db90191e9d2b691c4d7aa99c9cc5dde557ccae75ac32a36b22c663fa8e6ce395899626dc9892e4713f53ad8667bd4c6f10b2ecc09e70ba827bf0723174034aea6d063b53e7306f1e7dba7cdb2fed6ce24d67670bc80.mp4\",\"vid\":\"VDB9RPOIC\",\"cover\":\"http://vimg1.ws.126.net/image/snapshot/2018/3/S/1/VDB9RPMS1.jpg\",\"sizeSHD\":0,\"playersize\":0,\"ptime\":\"2018-06-27 00:00:00\",\"m3u8_url\":\"http://flv.bn.netease.com/50a19b2c1054f3ed53e6adb3270c01f8ac9088783ea87edd609326c1303b56061db6b9caa278270d8e9a237bf34179005055c487b518478f729e015856823e2862c7311fef876f93b9bf9a3186ed61949d99003899c89b7442f973cc994080a524c8f137923978b43d6b8f85bc11d3e61f1fa5d9d52b38c5.m3u8\",\"topicImg\":\"http://vimg3.ws.126.net/image/snapshot/2016/4/C/4/VBILQ3FC4.jpg\",\"votecount\":0,\"length\":14,\"videosource\":\"新媒体\",\"downTimes\":0,\"m3u8Hd_url\":null,\"sizeSD\":609,\"topicSid\":\"VBILQ3FC1\",\"commentStatus\":1,\"playCount\":0,\"replyCount\":0,\"replyBoard\":\"video_bbs\",\"replyid\":\"DB9RPOIC008535RB\",\"topicName\":\"面呆围笑\",\"sectiontitle\":\"\",\"topicDesc\":\"爱笑的人运气不会太差\"},{\"sizeHD\":0,\"videoTopic\":{\"ename\":\"T1486732282730\",\"tname\":\"二十二条笑话\",\"alias\":\"每天推荐搞笑视频让你笑口常开！激发生活正能量！\",\"topic_icons\":\"http://img2.cache.netease.com/m/newsapp/topic_icons/T1486732282730.png\",\"tid\":\"T1486732282730\"},\"upTimes\":0,\"mp4Hd_url\":null,\"description\":\"\",\"title\":\"自己把自己都逗笑了，可爱\",\"mp4_url\":\"http://flv3.bn.netease.com/50a19b2c1054f3ed53e6adb3270c01f84ac6e986912eca6efd3d4730cb3886f368290b9363f780b658d2a9ad1f4f9326c16476d6199c61e0f8fb88738e3df2b4ac088447d3f7db75cfeb2962b6022188138169d39a1251f16b746e3e015202839d1087089f9076414879c64640dc6c03e450a11462d6ad42.mp4\",\"vid\":\"VDBMI9A9D\",\"cover\":\"http://vimg1.ws.126.net/image/snapshot/2018/3/4/H/VDBMI994H.jpg\",\"sizeSHD\":0,\"playersize\":0,\"ptime\":\"2018-06-27 00:00:00\",\"m3u8_url\":\"http://flv.bn.netease.com/50a19b2c1054f3ed53e6adb3270c01f8fe5de839d28959a4165a1a4d169e27f4e93fa98ea909531a51429b34535980c48a7810720301d5c7a890ce4cc103105feb09ea295294e8237919edc47d0df078e1b52666543f0daf15892d47b53a3219ef0d3415e2b9a6e242d4477153429eb3f94554b1938e03c2.m3u8\",\"topicImg\":\"http://vimg3.ws.126.net/image/snapshot/2017/2/G/V/VCC0GKVGV.jpg\",\"votecount\":0,\"length\":11,\"videosource\":\"新媒体\",\"downTimes\":0,\"m3u8Hd_url\":null,\"sizeSD\":478,\"topicSid\":\"VCC0GKVGS\",\"commentStatus\":1,\"playCount\":0,\"replyCount\":0,\"replyBoard\":\"video_bbs\",\"replyid\":\"DBMI9A9D008535RB\",\"topicName\":\"二十二条笑话\",\"sectiontitle\":\"\",\"topicDesc\":\"每天推荐搞笑视频让你笑口常开！激发生活正能量！\"},{\"sizeHD\":0,\"videoTopic\":{\"ename\":\"T1460515710511\",\"tname\":\"大眼睛爱猎奇\",\"alias\":\"给你带来最新最恐怖分奇闻异事\",\"topic_icons\":\"http://img2.cache.netease.com/m/newsapp/topic_icons/T1460515710511.png\",\"tid\":\"T1460515710511\"},\"upTimes\":20,\"mp4Hd_url\":null,\"description\":\"\",\"title\":\"汪星人在家里拉屎，主人批评\",\"mp4_url\":\"http://flv3.bn.netease.com/50a19b2c1054f3ed53e6adb3270c01f8fdbb48f6c9565055c83eb3e028d2ade6462629578c2c626eaad817c7a689393a2772eaa8a18e81ab76bdbc6c1d911dce61fbb9a58d747f86dda5057039e6b83acf0fd0e689b99f402439759627a964da124aebe01d369eb33c824dfb26f50b8fda3d7e27641caaea.mp4\",\"vid\":\"VDBMIBPR9\",\"cover\":\"http://vimg3.ws.126.net/image/snapshot/2018/3/A/A/VDBMIBOAA.jpg\",\"sizeSHD\":0,\"playersize\":0,\"ptime\":\"2018-06-27 00:00:00\",\"m3u8_url\":\"http://flv.bn.netease.com/50a19b2c1054f3ed53e6adb3270c01f8aa33011253648549880aefbe30e1a40c72962e2dd150789edb18c2be518957d217b8e09540cb16d4d02733ca3d2bb847757527900a949d5cdc83665dea3b21e63e9ec64c0c597759eec28b102c3add79c6f9674e8af9d72b9b64f7b9f34513d570fe6301f628129b.m3u8\",\"topicImg\":\"http://vimg1.ws.126.net/image/snapshot/2016/3/F/0/VBHLR8QF0.jpg\",\"votecount\":20,\"length\":58,\"videosource\":\"新媒体\",\"downTimes\":0,\"m3u8Hd_url\":null,\"sizeSD\":2523,\"topicSid\":\"VBHLR8QET\",\"commentStatus\":1,\"playCount\":0,\"replyCount\":22,\"replyBoard\":\"video_bbs\",\"replyid\":\"DBMIBPR9008535RB\",\"topicName\":\"大眼睛爱猎奇\",\"sectiontitle\":\"\",\"topicDesc\":\"给你带来最新最恐怖分奇闻异事\"},{\"sizeHD\":0,\"videoTopic\":{\"ename\":\"T1482313209970\",\"tname\":\"这是什么梗?\",\"alias\":\"搞笑图片，幽默笑话，有趣的故事\",\"topic_icons\":\"http://img2.cache.netease.com/m/newsapp/topic_icons/T1482313209970.png\",\"tid\":\"T1482313209970\"},\"upTimes\":6,\"mp4Hd_url\":null,\"description\":\"\",\"title\":\"现在的孩子一言不合就是哭\",\"mp4_url\":\"http://flv3.bn.netease.com/50a19b2c1054f3ed53e6adb3270c01f8462efc9c5f27ff55cf49927c33d4a017dd2076373ea0310cdc651bf95efc769d9f2d1f3060952c561a5928a6437ff3f5589e86fc966a00331ff2a6733b16c0db138bc724ffee9049ff695545e336e0c9dba22d65ca998955b83ac711ab9059ff6a1109c806da3f0c.mp4\",\"vid\":\"VDBMLJRPQ\",\"cover\":\"http://vimg2.ws.126.net/image/snapshot/2018/3/R/3/VDBMLJPR3.jpg\",\"sizeSHD\":0,\"playersize\":0,\"ptime\":\"2018-06-27 00:00:00\",\"m3u8_url\":\"http://flv.bn.netease.com/50a19b2c1054f3ed53e6adb3270c01f8c8f26d23df0b6597a1ac4875205053de9326c64c302025544357d414e9fa83384a06da207d42d16d9fbf106f60be1e912cbe21e53145bc18a5e29ed96f39814bc6abcab822ca604f7c70e652d211395f94ca83d1dbb21a9731744b18a218d21794d0c159e19d96bf.m3u8\",\"topicImg\":\"http://vimg3.ws.126.net/image/snapshot/2016/12/C/P/VC7T2A3CP.jpg\",\"votecount\":6,\"length\":12,\"videosource\":\"新媒体\",\"downTimes\":0,\"m3u8Hd_url\":null,\"sizeSD\":522,\"topicSid\":\"VC7T2A3CM\",\"commentStatus\":1,\"playCount\":0,\"replyCount\":7,\"replyBoard\":\"video_bbs\",\"replyid\":\"DBMLJRPQ008535RB\",\"topicName\":\"这是什么梗?\",\"sectiontitle\":\"\",\"topicDesc\":\"搞笑图片，幽默笑话，有趣的故事\"},{\"sizeHD\":0,\"videoTopic\":{\"ename\":\"T1460515706582\",\"tname\":\"劲爆搞笑\",\"alias\":\"让你开心每一天。\",\"topic_icons\":\"http://img2.cache.netease.com/m/newsapp/topic_icons/T1460515706582.png\",\"tid\":\"T1460515706582\"},\"upTimes\":55,\"mp4Hd_url\":null,\"description\":\"\",\"title\":\"大师驾到了会弹钢琴的汪\",\"mp4_url\":\"http://flv3.bn.netease.com/50a19b2c1054f3ed53e6adb3270c01f8f6ba2d30f5faea48684c567a0e8d5b8c142cc942ec2c2f9b0475e2af15e1e87f1faf30ac653ae9f47bf08d06c1172b90de9268f2b2c1ad774c795b44b5df5b92d6682c9eb326b7ee2d3fa6f142590a8195fcc0f67e0ce422a0ba87330cc99cbd8bed66b862d159e5.mp4\",\"vid\":\"VDBMP1JML\",\"cover\":\"http://vimg3.ws.126.net/image/snapshot/2018/3/N/O/VDBMP1HNO.jpg\",\"sizeSHD\":0,\"playersize\":0,\"ptime\":\"2018-06-27 00:00:00\",\"m3u8_url\":\"http://flv.bn.netease.com/50a19b2c1054f3ed53e6adb3270c01f8212b3f974ee10b8897a16e9f8e3a6381995526bcedafe1dd1fda83348803fbbf96877be65d21e7fe6444fe269684c1dd2fd220f795ec98299c5c0b1b60f0886973a971c293dc5fb2115de16e234ee7c47b926e318da8544683e87b3477a2e7fdf80630048b4a03a9.m3u8\",\"topicImg\":\"http://vimg3.ws.126.net/image/snapshot/2016/3/C/6/VBI02GKC6.jpg\",\"votecount\":55,\"length\":45,\"videosource\":\"新媒体\",\"downTimes\":0,\"m3u8Hd_url\":null,\"sizeSD\":1957,\"topicSid\":\"VBI02GKC3\",\"commentStatus\":1,\"playCount\":0,\"replyCount\":57,\"replyBoard\":\"video_bbs\",\"replyid\":\"DBMP1JML008535RB\",\"topicName\":\"劲爆搞笑\",\"sectiontitle\":\"\",\"topicDesc\":\"让你开心每一天。\"},{\"sizeHD\":0,\"videoTopic\":{\"ename\":\"T1460515710423\",\"tname\":\"笑翻天*\",\"alias\":\"挖掘生活中有趣的话题\",\"topic_icons\":\"http://img2.cache.netease.com/m/newsapp/topic_icons/T1460515710423.png\",\"tid\":\"T1460515710423\"},\"upTimes\":0,\"mp4Hd_url\":null,\"description\":\"\",\"title\":\"二哈为了叫醒猪也是拼了老命\",\"mp4_url\":\"http://flv3.bn.netease.com/50a19b2c1054f3ed53e6adb3270c01f80cb1b3203bcc0632f39c9dd2a24d881b823923097171f93ec4f4f2896e641676d95f8817b599fa9005e0eeeba8f9b7732cd7e644477c323910970343dbf759e5c3f59df38630acd5c2472256b9e58e5abf6f2f7b9fa459e1f5d1ce9db20b7dcd733355d9648f47c2.mp4\",\"vid\":\"VDBMLMFF6\",\"cover\":\"http://vimg1.ws.126.net/image/snapshot/2018/3/D/D/VDBMLMEDD.jpg\",\"sizeSHD\":0,\"playersize\":1,\"ptime\":\"2018-06-27 00:00:00\",\"m3u8_url\":\"http://flv.bn.netease.com/50a19b2c1054f3ed53e6adb3270c01f897c434aaf46a7697d1900b83890d2834390db97a642565e8de877d9c5fc8637dba7e919c24ff26889cb8b6315854ac224387970e3c60bd0527e19919162530b24d27122c775458f943361dfe99b8a4a4dced6c1792e493d7e355b7f4b45661c7f6b43c0de0c055c4.m3u8\",\"topicImg\":\"http://vimg1.ws.126.net/image/snapshot/2016/2/L/9/VBG7H78L9.jpg\",\"votecount\":0,\"length\":61,\"videosource\":\"新媒体\",\"downTimes\":0,\"m3u8Hd_url\":null,\"sizeSD\":2653,\"topicSid\":\"VBFGBBE76\",\"commentStatus\":1,\"playCount\":0,\"replyCount\":0,\"replyBoard\":\"video_bbs\",\"replyid\":\"DBMLMFF6008535RB\",\"topicName\":\"笑翻天\",\"sectiontitle\":\"\",\"topicDesc\":\"挖掘生活中有趣的话题\"},{\"sizeHD\":0,\"videoTopic\":{\"ename\":\"T1486704016155\",\"tname\":\"户外野生君\",\"alias\":\"世界之大无奇不有\",\"topic_icons\":\"http://img2.cache.netease.com/m/newsapp/topic_icons/T1486704016155.png\",\"tid\":\"T1486704016155\"},\"upTimes\":0,\"mp4Hd_url\":null,\"description\":\"\",\"title\":\"这家伙给酸的，表情包\",\"mp4_url\":\"http://flv3.bn.netease.com/50a19b2c1054f3ed53e6adb3270c01f8bb8dc8fd19ece5cd5ef9dc4e25c6d043e602ad873b5a3a9f1e05f1a53f42c358f320d33b8eb0bff70143d6f302ab7805908d471f236b2d61e079dbb15882a553f923c8af3c1c6dfc539a6008f38f1dfcbf4fc3993f9414f59988c6656cf2016138722004b5432704.mp4\",\"vid\":\"VDBM7SOT5\",\"cover\":\"http://vimg1.ws.126.net/image/snapshot/2018/3/D/S/VDBM7SNDS.jpg\",\"sizeSHD\":0,\"playersize\":0,\"ptime\":\"2018-06-27 00:00:00\",\"m3u8_url\":\"http://flv.bn.netease.com/50a19b2c1054f3ed53e6adb3270c01f891e9248a814a21799cf4bca65cacdee98e80244478049d9e31407216085f834a4f90df3f50f6f580c09fe26d7a632afe0c1b53ed6e74d7ad3743826878f2a357bb2368d0f27b5c4fa625d2e0109cc6091f933346372ef25cc730e8a6e5d36b05f4199a157ca1d924.m3u8\",\"topicImg\":\"http://vimg3.ws.126.net/image/snapshot/2017/2/M/T/VCC01SNMT.jpg\",\"votecount\":0,\"length\":12,\"videosource\":\"新媒体\",\"downTimes\":0,\"m3u8Hd_url\":null,\"sizeSD\":522,\"topicSid\":\"VCC01SNMQ\",\"commentStatus\":1,\"playCount\":0,\"replyCount\":0,\"replyBoard\":\"video_bbs\",\"replyid\":\"DBM7SOT5008535RB\",\"topicName\":\"户外野生君\",\"sectiontitle\":\"\",\"topicDesc\":\"世界之大无奇不有\"},{\"sizeHD\":0,\"videoTopic\":{\"ename\":\"T1482313272498\",\"tname\":\"笑笑更健康\",\"alias\":\"笑一笑,十年少…\",\"topic_icons\":\"http://img2.cache.netease.com/m/newsapp/topic_icons/T1482313272498.png\",\"tid\":\"T1482313272498\"},\"upTimes\":0,\"mp4Hd_url\":null,\"description\":\"\",\"title\":\"多大了还念着吃奶的样子\",\"mp4_url\":\"http://flv3.bn.netease.com/50a19b2c1054f3ed53e6adb3270c01f8c4c6be539efa354374d39f5fbdb9de1e0bb7b993c43fcb577124c1b149c7c9a543fa31ca493641ec8daaee3005aa5a19bd170cae188dad64fe28aab865fe751039d672bdf666b1f1b3317745b261b3a67cc03cdb0a9aee22b9edef250a7b859a8438940e6b551de4.mp4\",\"vid\":\"VDBMI7P2N\",\"cover\":\"http://vimg3.ws.126.net/image/snapshot/2018/3/L/S/VDBMI7NLS.jpg\",\"sizeSHD\":0,\"playersize\":0,\"ptime\":\"2018-06-27 00:00:00\",\"m3u8_url\":\"http://flv.bn.netease.com/50a19b2c1054f3ed53e6adb3270c01f8d31734d26b2a0142850aeeec38eb6d8e6458a68040b75be8d110775c285f99048789a070c9849becbfaf17e1fe96c2cb658bcc43dfd48d667d6b98b97ee2c1293c22ce5ebe1cac09a67e7e948e572f03a64559298db774ad00cc35bd9bc330a6fed4752a8abe3a85.m3u8\",\"topicImg\":\"http://vimg3.ws.126.net/image/snapshot/2016/12/4/L/VC7T2E14L.jpg\",\"votecount\":0,\"length\":21,\"videosource\":\"新媒体\",\"downTimes\":0,\"m3u8Hd_url\":null,\"sizeSD\":913,\"topicSid\":\"VC7T2E14I\",\"commentStatus\":1,\"playCount\":0,\"replyCount\":0,\"replyBoard\":\"video_bbs\",\"replyid\":\"DBMI7P2N008535RB\",\"topicName\":\"笑笑更健康\",\"sectiontitle\":\"\",\"topicDesc\":\"笑一笑,十年少…\"},{\"sizeHD\":0,\"videoTopic\":{\"ename\":\"T1486732227135\",\"tname\":\"欢乐人生不虚度\",\"alias\":\"大好年华，开心才是正事\",\"topic_icons\":\"http://img2.cache.netease.com/m/newsapp/topic_icons/T1486732227135.png\",\"tid\":\"T1486732227135\"},\"upTimes\":0,\"mp4Hd_url\":null,\"description\":\"\",\"title\":\"两个小朋友骚的刚刚好，哈哈\",\"mp4_url\":\"http://flv3.bn.netease.com/50a19b2c1054f3ed53e6adb3270c01f8cfe4f09111bf0f47456d0d5d58adb38196c48fc40928459184f8b487a54ff1e5e28e47ad4fdf62d5fb922640bac073ed1a875ca699a8b30f833e92b9f21c7ad6360031a56b4eec35fd85a42f4eb18d3ca17d2712365c5aefd5adace033aad8edd00d4e0ee0743fbd.mp4\",\"vid\":\"VDB9RQ7TF\",\"cover\":\"http://vimg3.ws.126.net/image/snapshot/2018/3/8/A/VDB9RQ68A.jpg\",\"sizeSHD\":0,\"playersize\":0,\"ptime\":\"2018-06-27 00:00:00\",\"m3u8_url\":\"http://flv.bn.netease.com/50a19b2c1054f3ed53e6adb3270c01f8136804e901f78c6a3d83ec14dfc70248c49c2c1a9901935814d43511ae1038d3218f6c89aa3d92696ad490fc270e133e9ea0beb3ac1e57bb54f9b19f581f360eb28074cf1c79b089064510c8b767d3d80828283a4ab54e4a0c856712a31063a8a0e868144d351be1.m3u8\",\"topicImg\":\"http://vimg2.ws.126.net/image/snapshot/2017/2/E/8/VCC0GA4E8.jpg\",\"votecount\":0,\"length\":15,\"videosource\":\"新媒体\",\"downTimes\":0,\"m3u8Hd_url\":null,\"sizeSD\":652,\"topicSid\":\"VCC0GA4E5\",\"commentStatus\":1,\"playCount\":0,\"replyCount\":0,\"replyBoard\":\"video_bbs\",\"replyid\":\"DB9RQ7TF008535RB\",\"topicName\":\"欢乐人生不虚度\",\"sectiontitle\":\"\",\"topicDesc\":\"大好年华，开心才是正事\\r\\n\"},{\"sizeHD\":0,\"upTimes\":0,\"mp4Hd_url\":null,\"description\":\"\",\"title\":\"你今天真可爱，可爱的小猪猪\",\"mp4_url\":\"http://flv3.bn.netease.com/50a19b2c1054f3ed53e6adb3270c01f80706c0bb1a9986df95071d32b5f757bb6a1be2307d79fb128180f5ebb008b4dccb27daf655afebd22ee89166d0f1a642296bec8944e91c8a49fab8e58a920c2694352f495fd6b518bdb6e8a8c0ce0ea59f9fe792a2bc2eb79dad5febaaa4d6554345ef657935e78b.mp4\",\"vid\":\"VDB9TEGE4\",\"cover\":\"http://vimg2.ws.126.net/image/snapshot/2018/3/3/V/VDB9TEF3V.jpg\",\"sizeSHD\":0,\"playersize\":0,\"ptime\":\"2018-06-27 00:00:00\",\"m3u8_url\":\"http://flv.bn.netease.com/50a19b2c1054f3ed53e6adb3270c01f87569149fa0ebc1336d0e9a25e0124995833fe74de9f8748acc2eccca0fd92611a56e3e4ec1fcc5a7e6861f83c7db3bcb28c16e6b2fffc552ef82f27d1e602df41c058881ce38800229c8f5b5cb14b807f608342be6ca3b5e6a0bc1d040ac6f2ec1ff842be6f8a0c1.m3u8\",\"topicImg\":\"http://vimg1.ws.126.net/image/snapshot/2017/2/S/T/VCCUNO3ST.jpg\",\"votecount\":0,\"length\":11,\"videosource\":\"新媒体\",\"downTimes\":0,\"m3u8Hd_url\":null,\"sizeSD\":478,\"topicSid\":\"VCCUNO3SQ\",\"commentStatus\":1,\"playCount\":0,\"replyCount\":0,\"replyBoard\":\"video_bbs\",\"replyid\":\"DB9TEGE4008535RB\",\"topicName\":\"短视频\",\"sectiontitle\":\"\",\"topicDesc\":\"芒果TV视频\"}]";
        //List<Map> maps = JSONUtil.toList(str, Map.class);
        //Integer i = 1;
        //for (Map map : maps) {
        //    esService.insertEsContent(EsConstant.CONTENT_INDEX,i.toString(),JSONUtil.toJsonStr(map));
        //    i++;
        //}

        //boolean aaa = esService.batchEsContent(list);

        MultiMatchQueryBuilder queryBuilder = QueryBuilders.multiMatchQuery("不会", "title", "topicName", "topicDesc");
        //MultiMatchQueryBuilder queryBuilder = QueryBuilders.multiMatchQuery("不会", "videoTopic.alias");
        Map<String, Boolean> sortFieldsToAsc = new HashMap<>();
        String[] includeFields = new String[]{};
        String[] excludeFields = new String[]{};
        List<Map<String, Object>> maps = esService.searchIndex(EsConstant.CONTENT_INDEX, 0, 20, queryBuilder, sortFieldsToAsc, includeFields, excludeFields, 60);
        log.info("返回：{}",maps);
        return ApiResult.success(maps);
    }

}

