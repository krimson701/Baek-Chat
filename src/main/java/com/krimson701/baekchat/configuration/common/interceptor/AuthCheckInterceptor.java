package com.krimson701.baekchat.configuration.common.interceptor;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.krimson701.baekchat.model.AuthGoogleInfo;
import com.krimson701.baekchat.model.ChattingMessage;
import com.krimson701.baekchat.repository.UserRepository;
import com.krimson701.baekchat.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;
import java.util.LinkedHashMap;

/**
 * 인증 체크
 * @author BC-hoon
 *
 */
@Slf4j
@Component
@JsonIgnoreProperties(ignoreUnknown = true)
public class AuthCheckInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object controller) throws IOException {
        //log.info(request.getRequestURI());
        log.info(">> AuthCheckInterceptor preHandle Start<<");
        log.info("URI : [{}]", request.getRequestURI());
        Enumeration<String> it = request.getHeaderNames();
        while(it.hasMoreElements()) {
            String temp = it.nextElement();
            log.info("[{}] : [{}]", temp, request.getHeader(temp));
        }
        log.info("token : [{}]", request.getHeader("authorization"));
        String redis_key_token = request.getHeader("authorization");
        ResponseEntity<AuthGoogleInfo> googleResponse = null;
        if(redisTemplate.opsForValue().get(redis_key_token) == null) {
            try {
                String googleUrl = "https://www.googleapis.com/oauth2/v1/tokeninfo?access_token=" + request.getHeader("authorization");
                googleResponse = restTemplate.exchange(googleUrl, HttpMethod.GET, null, AuthGoogleInfo.class);
            } catch (HttpClientErrorException e){
                log.info("Invalid Google Token");
                response.sendError(HttpStatus.UNAUTHORIZED.value(), "Invalid Google Token");
                return false;
            }
            redisTemplate.opsForValue().set(redis_key_token, googleResponse.getBody());
        }
        Object result = redisTemplate.opsForValue().get(redis_key_token);
        ObjectMapper om = new ObjectMapper();
        AuthGoogleInfo authData = om.convertValue(result, AuthGoogleInfo.class);
        Long userId = Long.valueOf(authData.getUserId().substring(5));// String to Long
        log.info("userId cut : [{}]", userId);

        request.setAttribute("userId", userId);
        request.setAttribute("userEmail", authData.getEmail());
        log.info("결과 : Attr [{}]",request.getAttribute("userId"));
        log.info(">> AuthCheckInterceptor preHandle End<<");
        return true;
    }
}
