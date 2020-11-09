package com.krimson701.baekchat.domain;

import com.krimson701.baekchat.controller.enums.RelationType;
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

    @Column(name ="relating_id", nullable = false)
    private long relatingId;

    @OneToOne
    @JoinColumn(name ="related_id", nullable = false)
    private User relatedUser;

    @Enumerated(value = EnumType.STRING)
    @Column(name ="type")
    private RelationType type; //

    @Column(name ="create_date")
    @CreationTimestamp
    private Date createDate;

    public  Relation(){}

    public Relation(long relatingId, User user, RelationType type){
        this.relatingId = relatingId;
        this.relatedUser = user;
        this.type = type;
    }

}
