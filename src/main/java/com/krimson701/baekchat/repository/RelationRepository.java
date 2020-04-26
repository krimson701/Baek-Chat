package com.krimson701.baekchat.repository;

import com.krimson701.baekchat.domain.Relation;
import com.krimson701.baekchat.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RelationRepository extends JpaRepository<Relation, Long> {

    List<User> findAllById(int userId);

//    @Query("SELECT DISTINCT relation FROM tb_user_relation relation left join fetch relation.pets WHERE owner.lastName LIKE :lastName%")
//    @Transactional(readOnly = true)
//    List<User> findFreindsById(@Param("userId") Integer userId);

}
