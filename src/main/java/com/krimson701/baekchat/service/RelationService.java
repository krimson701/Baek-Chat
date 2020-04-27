package com.krimson701.baekchat.service;

import com.krimson701.baekchat.controller.enums.RelationType;
import com.krimson701.baekchat.domain.Relation;
import com.krimson701.baekchat.domain.User;
import com.krimson701.baekchat.repository.RelationRepository;
import com.krimson701.baekchat.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RelationService {

    @Autowired
    RelationRepository relationRepository;

    @Autowired
    UserRepository userRepository;

    public List<User> getRelations(final int userId, final RelationType relation){
        List<User> userList = relationRepository.findAllByIdAndType(userId, relation.name());
        return userList;
    }

    public void insertRelation(final int userId, final int relatedId , final RelationType relationType) throws Exception {

        Optional<User> relatedUser = userRepository.findById((long) relatedId);
        Relation relation = null;
        if(relatedUser.isPresent()) {
            relation = new Relation(userId,relatedUser.get(), relationType.name());
        } else {
            throw new Exception();
        }
        relationRepository.save(relation);
    }

}
