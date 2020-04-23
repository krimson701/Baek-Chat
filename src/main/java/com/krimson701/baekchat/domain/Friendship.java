package com.krimson701.baekchat.domain;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.util.Date;

@Data
public class Friendship {

    @Id
    @Column(name ="FRIEND_ID")
    private String relatedId;

    @ManyToOne
    @JoinColumn(name ="USER_ID")
    private User relatingId;

    @Column
    private String type; // 나중에 Enum으로 정의하자

    @Column
    private Date createDate;

    public Friendship(User relatingId, String relatedId, String type){
        this.relatingId = relatingId;
        this.relatedId = relatedId;
        this.type = type;
    }
}
