package com.krimson701.baekchat.service;


import com.krimson701.baekchat.configuration.AsyncConfig;
import com.krimson701.baekchat.domain.User;
import com.krimson701.baekchat.enums.ChannelStatusEnum;
import com.krimson701.baekchat.enums.SequenceEnum;
import com.krimson701.baekchat.model.*;
import com.krimson701.baekchat.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


@Service
@Slf4j
public class MessengerService{

    @Autowired
    private AsyncConfig asyncConfig;


    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RedisTemplate redisTemplate;


    //REDIS PUB
    public void messagePub(String topic, ChattingMessage data) {
        redisTemplate.convertAndSend(topic, data);
        //topic = "baek-chatting"
    }

    public List<ChattingMessage> getMessageList(Long channelNo) {
        // 채널 메세지 리스트 조회 timestamp 최신순으로 40개

        Query query = Query.query(Criteria.where("channelNo").is(Long.valueOf(channelNo)))
                .with(Sort.by(Sort.Direction.DESC, "timestamp")).limit(40);
        return mongoTemplate.find(query, ChattingMessage.class);
    }
  
    public List<ChannelInfo> getChannelList(Long userNo) {
        // 사용자 채널 리스트
        Query query = Query.query(Criteria.where("userNo").is(Long.valueOf(userNo)));
        query.fields().include("channelNo"); // channelNo만 필요해서 다른 정보 조회 하지 않음
        List<ChannelUser> userChannelList = mongoTemplate.find(query, ChannelUser.class);

        // 사용자 채널이 없으면 더이상 조회할 필요 없이 return
        if (CollectionUtils.isEmpty(userChannelList))
            return new ArrayList<ChannelInfo>();

        // 채널 번호 리스트
        List<Long> channelNos = userChannelList.stream().map(ChannelUser::getChannelNo)
                .collect(Collectors.toList());

        List<ChannelInfo> channelList = mongoTemplate.find(
                Query.query(Criteria.where("channelNo").in(channelNos).and("status")
                        .is(ChannelStatusEnum.enabled.getValue())),
                ChannelInfo.class);
        return channelList;
    }

    /**
     * 채널 생성
     *
     * @param userNo
     * @param channelName
     * @param userList
     * @return
     */
    public ChannelInfo executeCreateChannel(Long userNo, String channelName, List<Long> userList) throws Exception {
        Collections.sort(userList);

        /**
         * 기존 채널이 있는지 검색하는 코드
         */
        Query findQuery = new Query();
        Criteria mainCriteria = Criteria.where("userNos").is(userNo);
        // 같은 컬럼(userNos)은 and로 여러번 엮을수 없다.
        mainCriteria.andOperator(Criteria.where("userNos").size(userList.size()));
        findQuery.addCriteria(mainCriteria);

        List<ChannelInfo> joinChannels = mongoTemplate.find(findQuery, ChannelInfo.class);
        boolean isChannelsExist = false;
        long channelNo = -1;
        if (CollectionUtils.isEmpty(joinChannels) == false) {
            for (ChannelInfo mongoChannel : joinChannels) {
                isChannelsExist = userList.equals(mongoChannel.getUserNos());
                channelNo = mongoChannel.getChannelNo();
                if (isChannelsExist)
                    break;
            }
        }

        ChannelInfo channelInfo = null;

        /**
         * 기존 채널 존재 여부에 따라 분기
         */
        if (isChannelsExist) {
            /**
             * 이미 존재하는 채널 데이터 조회 해서 내린다.
             */
            log.info("createChannel isChannelsExist[true] channelNo[{}]", channelNo);
            channelInfo = getChannelInfo(channelNo, userNo);
            return channelInfo;
        }

        /**
         * 생성 후 조회해서 내린다.
         */
        channelNo = this.nextMessageSeq(SequenceEnum.channel);
        String userNos = StringUtils.join(userList, ",");
        if (StringUtils.isBlank(channelName)) {
            channelName = userNos;  // 비어있으면 일단 유저 번호로 채움(카톡처럼)
        }

        /**
         * 채널 이름은 최대 300자
         */
        if (channelName.length() > 300)
            channelName = channelName.substring(0, 299);

        /**
         * 채널 등록
         */
        ChannelInfo channel = new ChannelInfo();
        channel.setChannelNo(channelNo);
        channel.setChannelName(channelName);
        channel.setUserNos(userList);
        channel.setRegUserNo(userNo);
        channel.setStatus(ChannelStatusEnum.enabled.getValue());
        channel.setRegDate(Calendar.getInstance().getTimeInMillis());
        mongoTemplate.insert(channel);

        List<ChannelUser> channelUsers = new ArrayList<>();
        for (long row : userList) {
            ChannelUser channelUser = new ChannelUser();
            channelUser.setChannelNo(channelNo);
            channelUser.setUserNo(row);
            channelUser.setRegtime(Calendar.getInstance().getTimeInMillis());
            channelUser.setAlarm(true);
            channelUsers.add(channelUser);
        }
        mongoTemplate.insertAll(channelUsers);


        log.info("createChannel new channelNo[{}]", channelNo);

        channelInfo = getChannelInfo(channelNo, userNo);
        return channelInfo;
    }

    /**
     * 채널 정보 조회
     *
     * @param channelNo
     * @param userNo
     * @return
     */
    public ChannelInfo getChannelInfo(long channelNo, Long userNo) {

        /**
         * 채널 정보 조회
         */
        ChannelInfo channelInfo = mongoTemplate.findOne(
                Query.query(Criteria.where("channelNo").is(Long.valueOf(channelNo)).and("status")
                        .is(ChannelStatusEnum.enabled.getValue())),
                ChannelInfo.class);

        /**
         * 권한 체크
         */
        if (channelInfo == null || channelInfo.getUserNos().contains(Long.valueOf(userNo)) == false) {
            return null;
        } else {
            return channelInfo;
        }
    }

    /**
     * 채널 퇴장
     *
     * @param channelNo
     * @param userNo
     */
    public void executeExitChannel(Long channelNo, Long userNo) {

        /**
         * 채널 userNo 리스트에서 제외
         */
        Update update = new Update();
        update.pull("userNos", userNo);

        mongoTemplate.updateFirst(Query.query(Criteria.where("channelNo").is(Long.valueOf(channelNo)).and("status")
                        .is(ChannelStatusEnum.enabled.getValue())), update, ChannelInfo.class);
        /**
         * 유저 - 채널 관계 삭제
         */
        mongoTemplate.remove(Query.query(Criteria.where("channelNo").is(Long.valueOf(channelNo)).and("userNo")
                        .is(Long.valueOf(userNo))), "messenger_channel_user");

        // udpate 혹은 delete 작업 오류시 MongoUnacknowledgedWriteException 이 발생할 것
        // Transaction 처리되어야 정상
    }

    /**
     * 유저 채널 초대
     * @return
     * @throws Exception
     */
    public void executeInviteUser(Long channelNo, List<Long> userNos){

        /**
         * 채널 userNo 리스트에 추가
         */
        Update update = new Update();
        update.push("userNos").each(userNos);

        mongoTemplate.updateFirst(Query.query(Criteria.where("channelNo").is(Long.valueOf(channelNo)).and("status")
                .is(ChannelStatusEnum.enabled.getValue())), update, ChannelInfo.class);
        /**
         * 유저 - 채널 관계 추가
         */
        List<ChannelUser> channelUsers = new ArrayList<>();
        for (long row : userNos) {
            ChannelUser channelUser = new ChannelUser();
            channelUser.setChannelNo(channelNo);
            channelUser.setUserNo(row);
            channelUser.setRegtime(Calendar.getInstance().getTimeInMillis());
            channelUser.setAlarm(true);
            channelUsers.add(channelUser);
        }
        mongoTemplate.insertAll(channelUsers);
    }

    /**
     * 채널 내 유저 리스트
     */
    public List<User> getChannelUserList(Long channelNo){

        Query query = Query.query(Criteria.where("channelNo").is(Long.valueOf(channelNo)));
        query.fields().include("userNo"); // userNo만 필요해서 다른 정보 조회 하지 않음
        List<ChannelUser> userList = mongoTemplate.find(query, ChannelUser.class);
        log.info("channelUser : [{}]", userList);

        List<Long> users = userList.stream().map(ChannelUser::getUserNo).collect(Collectors.toList());
        log.info("users : [{}]", users);

        return userRepository.findByIdIn(users);
    }

    /**
     * 채널 권한 체크
     *
     * @param channelNo
     * @param userNo
     * @return
     */
    public ChannelInfo channelRoleCheck(long userNo, long channelNo) {
        // 개별 채널
        ChannelInfo channelsInfo = mongoTemplate.findOne(Query.query(Criteria.where("channelNo")
                .is(Long.valueOf(channelNo)).and("userNos").is(Long.valueOf(userNo))), ChannelInfo.class);
        /*
         * 프로젝트 탈퇴시 채널 데이터가 없을거라 권한을 따로 분기해야 하나 싶음. 프로젝트 채널 정보 조회시, 메세지 보낼때 삭제 여부 및 권한
         * 없음(완료) 분기해서 응답해야함. 404 : 삭제 401 : 권한없음.(탈퇴 당해서) 401 : 완료된 채널
         */
        if (channelsInfo == null) {
            log.error("Channel Role Fail");
            throw new RuntimeException();
        }

        return channelsInfo;
    }

    protected long nextMessageSeq(SequenceEnum key) {
        MongoSequence sequence = mongoTemplate.findAndModify(Query.query(Criteria.where("_id").is(key.getValue())),
                new Update().inc("seq", 1), FindAndModifyOptions.options().returnNew(true), MongoSequence.class);

        if (sequence == null) {
            log.error("Failed to get sequence");
            throw new RuntimeException();
        }
        return sequence.getSeq();
    }

}
