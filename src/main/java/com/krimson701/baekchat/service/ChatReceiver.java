package com.krimson701.baekchat.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.krimson701.baekchat.model.ChattingMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
@Slf4j
public class ChatReceiver {

    @Autowired
    private SimpMessagingTemplate template;

    @Autowired
    private MongoTemplate mongoTemplate;

    @KafkaListener(id = "main-listener", topics = "baekchat")
    public void receive(ChattingMessage message) throws Exception {
        log.info("message='{}'", message);
        mongoTemplate.insert(message);

        HashMap<String, Object> msg = new HashMap<>();
        msg.put("timestamp", Long.toString(message.getTimeStamp()));
        msg.put("message", message.getMessage());
        msg.put("author", message.getUserNo());     // 여기서 UserNo로 UserName 얻어냈어야함
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(msg);


        log.info("ChatReceiver : [{}]",msg);
        // 여기서 문제점은 그거임 바로 convertandsend로 보내주긴하지만 history에 추가하지도않음
        this.template.convertAndSend("/topic/public/" + message.getChannelNo(), json);
    }
}
