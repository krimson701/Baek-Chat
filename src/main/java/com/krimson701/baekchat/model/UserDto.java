package com.krimson701.baekchat.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class UserDto {

    @ApiModelProperty(value = "아이디(update 불가)", required = true) // update 가능하게 할수있음
    private Long id;

    @ApiModelProperty(value = "이메일", required = true)
    private String email;

    @ApiModelProperty(value = "취미")
    private String hobby;

    public UserDto(Long id ,String email){
        this.id = id;
        this.email = email;
    }

}
