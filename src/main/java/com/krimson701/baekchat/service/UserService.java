package com.krimson701.baekchat.service;

import com.krimson701.baekchat.domain.User;
import com.krimson701.baekchat.model.UserSearchModel;
import com.krimson701.baekchat.model.UserSpecs;
import com.krimson701.baekchat.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public Page<User> getUserList(UserSearchModel userSearchModel, Pageable pageable) {

        Specification<User> spec = Specification.where(UserSpecs.searchLike("id", userSearchModel.getId()));
        spec = spec.and(UserSpecs.searchLike("hobby", userSearchModel.getHobby()));
        Page<User> userList = userRepository.findAll(spec, pageable);
        //dto로 바꿀수있는지 생각해보자 응 바꿀수있다..
        return userList;
    }
}
