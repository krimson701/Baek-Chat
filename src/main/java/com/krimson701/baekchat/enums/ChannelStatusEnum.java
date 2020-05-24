package com.krimson701.baekchat.enums;

import lombok.Getter;

@Getter
public enum ChannelStatusEnum {
    enabled(1)		//	활성화
    , close(0)		//	close
    , disabled(-1)	//	비활성화
    ;

    private int value;

    private ChannelStatusEnum(int refValue) {
        this.value = refValue;
    }
}