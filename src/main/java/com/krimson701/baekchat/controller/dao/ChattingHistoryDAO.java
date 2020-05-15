package com.krimson701.baekchat.controller.dao;

import com.krimson701.baekchat.model.ChattingMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ChattingHistoryDAO {

    @Autowired
    MongoTemplate mongoTemplate;

    public List<ChattingMessage> get(Long channelNo){
        Query query = Query.query(Criteria.where("channelNo").is(Long.valueOf(channelNo)))
        .with(Sort.by(Direction.ASC, "timeStamp")).limit(40);
        return mongoTemplate.find(query, ChattingMessage.class);
    }


    /**
     * 이하 내용은 Redis 서버 추가 후 Caching 처리 내용
     * DAO가 아닌것 같아 Service로 이전 고려중
     * asignee : do168
     */

//    // A simple cache for temporarily storing chat data
//    private final Cache<UUID, ChattingMessage> chatHistoryCache = CacheBuilder
//            .newBuilder().maximumSize(20).expireAfterWrite(10, TimeUnit.MINUTES)
//            .build();
//
//    public void save(ChattingMessage chatObj) {
//        this.chatHistoryCache.put(UUID.randomUUID(), chatObj);
//    }
//
//    public List<ChattingMessage> get(Long channelNo) {
//        return chatHistoryCache.asMap().values().stream()
//                .sorted(Comparator.comparing(ChattingMessage::getTimeStamp))
//                .collect(Collectors.toList());
//    }
}
