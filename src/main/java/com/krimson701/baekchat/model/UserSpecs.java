package com.krimson701.baekchat.model;

import com.krimson701.baekchat.domain.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

public class UserSpecs {

    public static Specification<User> searchLike(final String spec, final String keyword) {

        return (Specification<User>) (root, query, cb) -> {
            String keyWord = "";
            if (StringUtils.isNotEmpty(keyword))
                keyWord = keyword;
            return cb.like(root.get(spec), "%" + keyWord + "%");
        };
    }

}
