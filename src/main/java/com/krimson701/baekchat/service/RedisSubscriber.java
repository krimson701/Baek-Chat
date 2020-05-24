package com.krimson701.baekchat.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.krimson701.baekchat.model.ChattingMessage;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class RedisSubscriber implements MessageListener {
    private final ObjectMapper objectMapper;
    private final RedisTemplate redisTemplate;
    private final SimpMessagingTemplate template;
    private final MongoTemplate mongoTemplate;

    public RedisSubscriber(ObjectMapper objectMapper, RedisTemplate redisTemplate, SimpMessagingTemplate template, MongoTemplate mongoTemplate) {
        this.objectMapper = objectMapper;
        this.redisTemplate = redisTemplate;
        this.template = template;
        this.mongoTemplate = mongoTemplate;
    }


    /**
     * Redis에서 메시지가 발행(publish)되면 대기하고 있던 onMessage가 해당 메시지를 받아 처리한다.
     */
    @SneakyThrows
    @Override
    public void onMessage(Message message, byte[] pattern) {

        String publishMessage = (String) redisTemplate.getStringSerializer().deserialize(message.getBody());
        ChattingMessage roomMessage = objectMapper.readValue(publishMessage, ChattingMessage.class);
        // redis에서 발행된 데이터를 받아 deserialize

        mongoTemplate.insert(roomMessage);
        //Mongo DB에 저장


        // Websocket 구독자에게 채팅 메시지 Send
        template.convertAndSend("/topic/public/" + roomMessage.getChannelNo(), roomMessage);
    }
}