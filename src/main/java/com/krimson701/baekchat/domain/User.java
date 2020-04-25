package com.krimson701.baekchat.domain;

import lombok.Data;

import javax.persistence.*;

import java.util.Date;
import java.util.Set;

@Entity
@Table(name="tb_user")
@Data
public class User {
    @Id
    @Column(name="user_id")
    private String id;

    @Column(name="email", length=40, nullable=false) // varchar(100) not null
    private String email;

    @Column(name="hobby")		// varchar(255)
    private String hobby;

    @OneToMany(fetch=FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name="user_id")
    private Set<Relation> friendships;

    @Column(name="create_date")
    private Date createDate;


    public User(){}

    public User(String email, String hobby){
        this.email = email;
        this.hobby = hobby;
    }
}
