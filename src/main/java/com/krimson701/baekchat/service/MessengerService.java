package com.krimson701.baekchat.service;

import com.krimson701.baekchat.model.ChattingMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class MessengerService{



    @Autowired
    private MongoTemplate mongoTemplate;


    @Autowired
    private RedisTemplate redisTemplate;

    //REDIS PUB
    public void messagePub(String topic, ChattingMessage data) {
        redisTemplate.convertAndSend(topic, data);
        //topic = "baek-chatting"
    }

    public List<ChattingMessage> getMessageList(Long channelNo){
        Query query = Query.query(Criteria.where("channelNo").is(Long.valueOf(channelNo)))
                .with(Sort.by(Sort.Direction.DESC, "timestamp")).limit(40);
        return mongoTemplate.find(query, ChattingMessage.class);
    }


}
