package com.krimson701.baekchat.domain;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="tb_user_relation")
@Data
public class Relation {

    @Id
    @Column(name ="id")
    private int id;

    @OneToOne
    @JoinColumn(name ="id", nullable = false, insertable = false, updatable = false)
    private User relatedUser;

    @Column(name ="type")
    private String type; // 나중에 Enum으로 정의하자

    @Column(name ="create_date")
    @CreationTimestamp
    private Date createDate;

    public  Relation(){}

    public Relation(int id,User user, String type){
        this.id = id;
        this.relatedUser = user;
        this.type = type;
    }
}
