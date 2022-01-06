package com.xiaojiuwo.common;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;


@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="公共分页", description="公共分页")
public class BasePage implements Serializable {

    @ApiModelProperty(value = "当前页")
    protected Long current;

    @ApiModelProperty(value = "一页多少条")
    protected Long size;

}
