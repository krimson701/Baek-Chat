package com.krimson701.baekchat.service;

import com.krimson701.baekchat.domain.User;
import com.krimson701.baekchat.model.UserDto;
import com.krimson701.baekchat.model.UserSearchModel;
import com.krimson701.baekchat.model.UserSpecs;
import com.krimson701.baekchat.repository.RelationRepository;
import com.krimson701.baekchat.repository.UserRepository;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RelationRepository relationRepository;

    public User getUser(long id){
        return userRepository.findById(id).get();
    }

    public Page<User> getUserList(UserSearchModel userSearchModel, Pageable pageable) {

        Specification<User> spec = Specification.where(UserSpecs.searchLike("email", userSearchModel.getEmail()));
        if(StringUtils.isNotEmpty(userSearchModel.getHobby()))
            spec = spec.and(UserSpecs.searchLike("hobby", userSearchModel.getHobby()));
        Page<User> userList = userRepository.findAll(spec, pageable);
        //dto로 바꿀수있는지 생각해보자 응 바꿀수있다..
        return userList;
    }

    public void insertUser(UserDto userDto){

        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        User user = modelMapper.map(userDto,User.class);

        this.userRepository.save(user);
    }

    public void updateUser(long id, UserDto userDto){

        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        User user = userRepository.getOne(id);
        userDto.setId(user.getId()); // 이부분은 user_id(키 말고 ID)를 수정할수없도록 하려고함

        modelMapper.map(userDto,user);

        this.userRepository.save(user);
    }

    public void deleteUser(long id){
        userRepository.deleteById(id);
    }

    public User searchUser(String userEmail){
        return userRepository.findByEmail(userEmail+"@gmail.com").get();
    }
}
