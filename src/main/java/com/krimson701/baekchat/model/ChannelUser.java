package com.krimson701.baekchat.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "messenger_channel_user")
@CompoundIndexes({
        @CompoundIndex(name = "idx_unique", def = "{channelNo:1, userNo:1}", unique = true)
})
public class ChannelUser {
    /**
     * 채널 번호
     */
    @ApiModelProperty(value = "채널 번호")
    private long channelNo;

    /**
     * 유저번호
     */
    @ApiModelProperty(value = "유저번호")
    @Indexed(name = "idx_userno")
    private long userNo;

    /**
     * 최근 읽은 순서(0은 하나도 안 읽음)
     */
    @ApiModelProperty(value = "최근 읽은 순서(0은 하나도 안 읽음)")
    private long readMessageNo;

    /**
     * 저장 시간
     */
    @ApiModelProperty(value = "저장 시간")
    private long regtime;

    /**
     * 멘션 카운트
     */
    @ApiModelProperty(value = "멘션 카운트")
    private int mentionCount;

    /**
     * 읽지 않은 카운트
     */
    @ApiModelProperty(value = "읽지 않은 카운트")
    private int unreadCount;

    @ApiModelProperty(value = "채널 알람")
    private boolean alarm;
}
