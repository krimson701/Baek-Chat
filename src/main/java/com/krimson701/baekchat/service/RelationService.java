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

    public List<Relation> getRelations(final long userId, final RelationType type){
        List<Relation> relations = relationRepository.findAllByRelatingIdAndType(userId, type.name());

        return relations;
    }

    public void insertRelation(final long userId, final long relatedId , final RelationType relationType) throws Exception {

        Optional<User> relatedUser = userRepository.findById(relatedId);
        Relation relation = null;
        if(relatedUser.isPresent()) {
            relation = new Relation(userId, relatedUser.get(), relationType.name());
        } else {
            throw new Exception();      // 로거 추가하자
        }
        relationRepository.save(relation);
    }

    public void deleteRelation(final long Id) throws Exception {

        Optional<Relation> relation = RelationRepository.findById(Id);
        relationRepository.delete(relation);
    }

}
