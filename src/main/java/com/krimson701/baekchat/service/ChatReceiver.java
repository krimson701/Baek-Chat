package com.krimson701.baekchat.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.krimson701.baekchat.model.ChattingMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
@Slf4j
public class ChatReceiver {

    @Autowired
    private SimpMessagingTemplate template;

    @KafkaListener(id = "main-listener", topics = "baek-chatting")
    public void receive(ChattingMessage message) throws Exception {
        log.info("message='{}'", message);
        HashMap<String, String> msg = new HashMap<>();
        msg.put("timestamp", Long.toString(message.getTimeStamp()));
        msg.put("message", message.getMessage());
        msg.put("author", message.getUser());

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(msg);

        this.template.convertAndSend("/topic/chat/", json);
    }
}
