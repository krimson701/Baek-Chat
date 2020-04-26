package com.krimson701.baekchat.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="tb_user_relation")
@Data
public class Relation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="id")
    private int id;

    @OneToOne
    @JoinColumn(name ="related_id", nullable = false, insertable = false, updatable = false)
    private User relatedUser;

    @Column(name ="type")
    private String type; // 나중에 Enum으로 정의하자

    @Column(name ="create_date")
    private Date createDate;

    public Relation(User user, String type){
        this.relatedUser = user;
        this.type = type;
    }
}
