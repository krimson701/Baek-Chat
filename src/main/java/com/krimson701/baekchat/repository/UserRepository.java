package com.krimson701.baekchat.repository;

import com.krimson701.baekchat.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;



public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {
    Page<User> findAll(Specification<User> spec, Pageable pageable);
}
