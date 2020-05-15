package com.krimson701.baekchat.repository;

import com.krimson701.baekchat.domain.Point;
import org.springframework.data.repository.CrudRepository;

public interface PointRedisRepository extends CrudRepository<Point, String> {
}