package com.krimson701.baekchat.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;


@Entity
@Table(name="tb_user")
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id", nullable=false)
    private long id;

    @Column(name="user_id", unique=true)		// varchar(255)
    private String userId;

    @Column(name="email", length=40, nullable=false) // varchar(100) not null
    private String email;

    @Column(name="hobby")		// varchar(255)
    private String hobby;

    @JsonBackReference
    @OneToMany(fetch=FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name="relating_id", nullable = false, insertable = false, updatable = false)
    private Set<Relation> relations;

    @Column(name="create_date")
    @CreationTimestamp
    private Date createDate;

    public User(){}

    public User(String email, String hobby){
        this.email = email;
        this.hobby = hobby;
    }
}
