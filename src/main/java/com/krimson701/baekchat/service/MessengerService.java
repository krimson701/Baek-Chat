package com.krimson701.baekchat.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.krimson701.baekchat.model.ChattingMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
@Slf4j
public class MessengerService {


    @Autowired
    private SimpMessagingTemplate template;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private KafkaTemplate<String, ChattingMessage> kafkaTemplate;

    public void messagePub(String topic, ChattingMessage data) {
        log.info("sending data='{}' to topic='{}'", data, topic);
        kafkaTemplate.send(topic, data);
    }

    @KafkaListener(id = "main-listener", topics = "baek-chatting")
    public void messageSub(ChattingMessage message) throws Exception {
        log.info("message='{}'", message);
        mongoTemplate.insert(message);

        HashMap<String, Object> msg = new HashMap<>();
        msg.put("timestamp", Long.toString(message.getTimestamp()));
        msg.put("message", message.getMessage());    // 여기서 UserNo로 UserName 얻어냈어야함
        msg.put("author", message.getUserNo());
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(msg);

        log.info("ChatReceive : [{}]",msg);
        this.template.convertAndSend("/topic/public/" + message.getChannelNo(), json);
    }

    public List<ChattingMessage> getMessageList(Long channelNo){
        Query query = Query.query(Criteria.where("channelNo").is(Long.valueOf(channelNo)))
                .with(Sort.by(Sort.Direction.DESC, "timestamp")).limit(40);
        return mongoTemplate.find(query, ChattingMessage.class);
    }
}
