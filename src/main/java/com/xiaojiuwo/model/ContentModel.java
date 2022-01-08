package com.xiaojiuwo.model;

import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;


/**
 * es存放实体类
 */
@Data
@Accessors(chain = true)
@ToString
public class ContentModel {

    private static final long serialVersionUID = 6320548148250372657L;

    private Integer contentId;

    private String title;


}