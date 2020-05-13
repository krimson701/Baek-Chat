package com.krimson701.baekchat.controller;

import com.krimson701.baekchat.controller.dao.ChattingHistoryDAO;
import com.krimson701.baekchat.domain.User;
import com.krimson701.baekchat.model.ChattingMessage;
import com.krimson701.baekchat.service.ChatReceiver;
import com.krimson701.baekchat.service.ChatSender;
import com.krimson701.baekchat.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin
public class ChattingController {

    @Autowired
    private ChatSender sender;

    @Autowired
    private ChatReceiver receiver;

    @Autowired
    private ChattingHistoryDAO chattingHistoryDAO;

    @Autowired
    private UserService userService;

    private static String BOOT_TOPIC = "baekchat";

    //// "url/app/message"로 들어오는 메시지를 "/topic/public"을 구독하고있는 사람들에게 송신
    @MessageMapping("/message")//@MessageMapping works for WebSocket protocol communication. This defines the URL mapping.
    //@SendTo("/topic/public")//websocket subscribe topic& direct send
    public void sendMessage(ChattingMessage message) throws Exception {
        message.setTimeStamp(System.currentTimeMillis());
        sender.send(BOOT_TOPIC, message);

    }

    @RequestMapping("/history/{channelNo}")
    public List<ChattingMessage> getChattingHistory(@PathVariable Long channelNo) throws Exception {
        System.out.println("history!");
        return chattingHistoryDAO.get(channelNo);
    }

    @MessageMapping("/chat/join")
    public void join(ChattingMessage message) {
        User user = userService.getUser(message.getUserNo());
        message.setMessage( user.getUserId() + "님이 입장하셨습니다.");
        sender.send(BOOT_TOPIC, message);
    }

    @MessageMapping("/file")
    @SendTo("/topic/chatting")
    public ChattingMessage sendFile(ChattingMessage message) throws Exception {
        return new ChattingMessage(message.getFileName(), message.getRawData(), message.getUserNo());
    }
}
