package com.xiaojiuwo.model.vo;

import com.xiaojiuwo.common.BaseModel;
import com.xiaojiuwo.common.BasePage;
import com.xiaojiuwo.enums.GenderEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;

/**
 * <p>
 * 
 * </p>
 *
 * @author auth
 * @since 2022-01-05
 */
@Data
@ApiModel(value="用户请求参数", description="")
public class UserInfoReq extends BasePage {

    @ApiModelProperty(value = "id")
    private Long id;

//    @NotBlank(message = "手机号必填")
    @ApiModelProperty(value = "手机号")
    private String mobile;

    @ApiModelProperty(value = "姓名")
    private String name;

    @ApiModelProperty(value = "1 男 2 女")
    private GenderEnum gender;

}
