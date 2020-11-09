package com.krimson701.baekchat.repository;

import com.krimson701.baekchat.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;


public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {

    Page<User> findAll(Specification<User> spec, Pageable pageable);

    List<User> findByIdIn(List<Long> idList);

    Optional<User> findByEmail(String userEmail);

    Optional<User> findById(Long id);
    
}
