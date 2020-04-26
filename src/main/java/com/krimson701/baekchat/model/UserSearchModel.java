package com.krimson701.baekchat.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/***
 * 유저 검색 모델
 * @author krimson701
 */
@Data
public class UserSearchModel {

    @ApiModelProperty(value = "아이디(like)", required = false)
    private String id;

    @ApiModelProperty(value = "취미(like)", required = false)
    private String hobby;

}
