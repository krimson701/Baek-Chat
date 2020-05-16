package com.krimson701.baekchat.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Document(collection = "messenger_channel")
public class ChannelInfo {

    /**
     * 채널 번호
     */
    @ApiModelProperty(value = "채널 번호")
    @Indexed(name = "idx_channelno")
    private long channelNo;

    /**
     * 채널이름
     */
    @ApiModelProperty(value = "채널이름")
    private String channelName;

    /**
     * 다이렉트 메시지 일때 참가한 유저번호(sort 해서 넣는다. 검색도 마찬가지)
     */
    @ApiModelProperty(value = "다이렉트 메시지 일때 참가한 유저번호(sort 해서 넣는다. 검색도 마찬가지)")
    @Indexed(name = "idx_user_nos")
    private List<Long> userNos;

    /**
     * 저장한 유저 번호
     */
    @ApiModelProperty(value = "저장한 유저 번호")
    private long regUserNo;

    /**
     * 생성 시간
     */
    @ApiModelProperty(value = "생성 시간")
    private long regDate;

    /**
     * ChannelStatusEnum 상태
     */
    @ApiModelProperty(value = "상태 ChannelStatus 참조")
    private int status;

    /**
     * 마지막 메시지 번호
     */
    @ApiModelProperty(value = "마지막 메시지 번호")
    private long lastMessageNo;

}
