package com.krimson701.baekchat.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;


@Entity
@Table(name="tb_user_relation")
@Data
public class Relation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="id", nullable = false)
    private long id;

    @Column(name ="relating_id")
    private long relatingId;

    @OneToOne
    @JoinColumn(name ="related_id", nullable = false)
    private User relatedUser;

    @Column(name ="type")
    private String type; // 나중에 Enum으로 정의하자

    @Column(name ="create_date")
    @CreationTimestamp
    private Date createDate;

    public  Relation(){}

    public Relation(long relatingId, User user, String type){
        this.relatingId = relatingId;
        this.relatedUser = user;
        this.type = type;
    }
}
