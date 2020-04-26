package com.krimson701.baekchat.service;

import com.krimson701.baekchat.domain.User;
import com.krimson701.baekchat.repository.RelationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RelationService {

    @Autowired
    RelationRepository relationRepository;

    public List<User> getFriends(final int userId){
        List<User> userList = relationRepository.findAllById(userId);
        return userList;
    }


}
