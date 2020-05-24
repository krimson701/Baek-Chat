package com.krimson701.baekchat.controller;

import com.krimson701.baekchat.domain.User;
import com.krimson701.baekchat.model.ChannelInfo;
import com.krimson701.baekchat.model.ChattingMessage;
import com.krimson701.baekchat.service.MessengerService;
import com.krimson701.baekchat.service.UserService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping("/messenger")
@CrossOrigin
@Slf4j
public class ChattingController {

    @Autowired
    private MessengerService messengerService;

    @Autowired
    private UserService userService;

    private static String BOOT_TOPIC = "baek-chatting";

    //// "url/app/message"로 들어오는 메시지를 "/topic/public"을 구독하고있는 사람들에게 송신
    @MessageMapping("/message")
//@MessageMapping works for WebSocket protocol communication. This defines the URL mapping.
    //@SendTo("/topic/public")//websocket subscribe topic& direct send
    public void sendMessage(ChattingMessage message) throws Exception {
        message.setTimestamp(System.currentTimeMillis());

        messengerService.messagePub(BOOT_TOPIC, message);

    }

    @ApiOperation(
            value = "메세지 hist 조회"
            , notes = "메세지 hist 조회(userNo ApiIgnore로 권한체크 추가해야함)"
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "complete")
    })
    @RequestMapping(value = "/history/{channelNo}", method = RequestMethod.GET)
    public List<ChattingMessage> getChattingHistory(@PathVariable Long channelNo) throws Exception {
        System.out.println("history!");
        return messengerService.getMessageList(channelNo);
    }

    @MessageMapping("/templates/chat/join")
    public void join(ChattingMessage message) {
        User user = userService.getUser(message.getUserNo());
        message.setMessage(user.getEmail() + "님이 입장하셨습니다.");
        messengerService.messagePub(BOOT_TOPIC, message);
    }

    // 추후 추가할 파일 전송
    @MessageMapping("/file")
    @SendTo("/topic/chatting")
    public ChattingMessage sendFile(ChattingMessage message) throws Exception {
        return new ChattingMessage(message.getFileName(), message.getRawData(), message.getUserNo());
    }


    /**
     * 채널 생성, 생성시 이미 존재하는 채팅방이면 번호를 리턴
     * @param userNo
     * @param users
     * @param channelName
     * @return
     * @throws Exception
     */
    @ApiOperation(value = "채팅 방 생성", notes = "이미 생성시 기존 채팅방 번호 리턴 (UserNo @ApiIgnore @RequestAttribute 해주어야함)")
    @ApiResponses(value = {@ApiResponse(code = 200, message = ""), @ApiResponse(code = 400, message = "파라미터 오류"),
            @ApiResponse(code = 403, message = "요청 멤버 파트너 권한 없음") }) // 403 response는 추후 삭제 예정
    @RequestMapping(value = "/channel/create", method = RequestMethod.POST)
    public ResponseEntity<ChannelInfo> channelCreate(@RequestParam("userNo") Long userNo,
                                                     @ApiParam(value = "멤버 번호 배열(1,2,3,4,5), 본인 포함", required = true) @RequestParam(name = "users", required = true) String users,
                                                     @ApiParam(value = "채팅 방 이름", required = false) @RequestParam(name = "name", required = false) String channelName) throws Exception {

        if (StringUtils.isBlank(users)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        users = users.replaceAll("\\s+", "");

        // 여기부분 만약 유저이름이 없는아이들이라면 어떻게 되어야할지
        // 프론트에서 없는 유저이름은 받지않게 될것인지
        List<Long> userList = Stream.of(users.split(",")).map(Long::parseLong).distinct().collect(Collectors.toList());
        if (CollectionUtils.isEmpty(userList)) {
            // empty
            log.error("userList [{}] isEmpty", userList);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        /**
         * 채널 생성 요청
         */
        ChannelInfo channelInfo = messengerService.executeCreateChannel(userNo, channelName, userList);

//        /**
//         * 채널 생성시 join 하는 사용자들에게 알림 전송
//         */
//        if (channelInfo.isCreate()) {
//            ResponseChannelJoin responseMessage = new ResponseChannelJoin(messengerSession);
//            responseMessage.getData().setChannel(channelInfo);
//            messengerService.responseSend(responseMessage);
//        }

        return new ResponseEntity<>(channelInfo, HttpStatus.OK);
    }


    @ApiOperation(
            value = "유저 채널 리스트 조회"
            , notes = "유저 채널 리스트 조회(UserNo @ApiIgnore @RequestAttribute 해주어야함)"
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "complete")
    })
    @RequestMapping(value = "/channel/list", method = RequestMethod.GET)
    public ResponseEntity<List<ChannelInfo>> getChannelList(@RequestParam("userNo") Long userNo) throws Exception {

        List<ChannelInfo> resultList = messengerService.getChannelList(userNo);

        return new ResponseEntity<>(resultList, HttpStatus.OK);
    }


    @ApiOperation(
            value = "유저 채널 나가기"
            , notes = "유저 채널 나가기(UserNo @ApiIgnore @RequestAttribute 해주어야함)"
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "complete")
    })
    @RequestMapping(value = "/channel/{channelNo}/exit", method = RequestMethod.POST)
    public ResponseEntity<Void> exitChannel(
            @RequestParam("userNo") Long userNo,
            @PathVariable Long channelNo) throws Exception {

        messengerService.executeExitChannel(channelNo, userNo);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
