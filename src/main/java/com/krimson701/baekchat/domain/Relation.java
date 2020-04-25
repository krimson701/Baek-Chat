package com.krimson701.baekchat.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="tb_user_relation")
@Data
public class Relation {

    @Id
    @Column(name ="user_id")
    private String userId;

    @Column(name ="related_id")
    private String reslatedId;

    @Column(name ="type")
    private String type; // 나중에 Enum으로 정의하자

    @Column(name ="create_date")
    private Date createDate;

    public Relation(String id, String type){
        this.reslatedId = id;
        this.type = type;
    }
}
