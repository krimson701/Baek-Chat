package com.krimson701.baekchat.domain;

import lombok.Data;

import javax.persistence.*;

import java.util.List;
import java.util.ArrayList;
import java.util.Date;

@Entity
@Table(name="tb_user")
@Data
public class User {
    @Id
    @Column(name="USER_ID")
    private String id;

    @Column(length=20, nullable=false) // varchar(100) not null
    private String name;

    @Column		// varchar(255)
    private String hobby;

    @OneToMany(mappedBy="relatingId")
    private List<Friendship> friendships = new ArrayList<Friendship>();

    @Column
    private Date createDate;


    public User(){}

    public User(String name, String hobby){
        this.name = name;
        this.hobby = hobby;
    }
}
