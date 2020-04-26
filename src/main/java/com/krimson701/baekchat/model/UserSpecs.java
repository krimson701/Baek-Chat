package com.krimson701.baekchat.model;

import com.krimson701.baekchat.domain.User;

import org.springframework.data.jpa.domain.Specification;
import org.apache.commons.lang3.StringUtils;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class UserSpecs {

    public static Specification<User> searchLike(final String spec, final String keyword) {

        return new Specification<User>() {
            @Override
            public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                String keyWord = "";
                if (StringUtils.isNotEmpty(keyword))
                    keyWord = keyword;
                return cb.like(root.get(spec), "%" + keyWord + "%");
            }
        };
    }

}
