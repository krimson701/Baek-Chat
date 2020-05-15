package com.krimson701.baekchat.model;

import com.krimson701.baekchat.controller.enums.RelationType;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

public class RelationDto {
    @ApiModelProperty(value = "관계 id(update 불가)", required = true) // update 가능하게 할수있음
    private long Id;

    @ApiModelProperty(value = "relating_id", required = true)
    private long relating_id;

    @ApiModelProperty(value = "related_id", required = true)
    private long related_id;

    @ApiModelProperty(value = "관계 타입", required = true)
    private RelationType type;

    @ApiModelProperty(value = "create_date", required = true)
    private Date create_date;
}
