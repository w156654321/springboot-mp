package com.xiaojiuwo.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

/**
 * 开关状态
 * @author : liudh
 * @date : 2020/07/27
 **/
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum GenderEnum {

    MAN(1,"男"),
    WOMAN(2,"女");

    @Getter
    @EnumValue
    public Integer key;
    @Getter
    public String msg;


    GenderEnum(Integer k, String s){
        this.key = k;
        this.msg = s;
    }


    public static GenderEnum getByValue(Integer value){
        GenderEnum[] orgType = GenderEnum.values();
        for(GenderEnum ot : orgType){
            if(ot.key.intValue()==value.intValue()){
                return ot;
            }
        }
        return null;
    }

    public static Integer getByValue(GenderEnum type){
        GenderEnum[] orgType = GenderEnum.values();
        for(GenderEnum ot : orgType){
            if(ot.key.intValue()==type.getKey()){
                return ot.key.intValue();
            }
        }
        return null;
    }


}
