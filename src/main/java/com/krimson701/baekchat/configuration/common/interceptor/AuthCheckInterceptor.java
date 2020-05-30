package com.krimson701.baekchat.configuration.common.interceptor;

import com.krimson701.baekchat.model.AuthGoogleInfo;
import com.krimson701.baekchat.repository.UserRepository;
import com.krimson701.baekchat.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;

/**
 * 인증 체크
 * @author BC-hoon
 *
 */
@Slf4j
@Component
public class AuthCheckInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object controller) {
        //log.info(request.getRequestURI());
        log.info(">> AuthCheckInterceptor preHandle Start<<");
        log.info("URI : [{}]", request.getRequestURI());
        Enumeration<String> it = request.getHeaderNames();
        while(it.hasMoreElements()) {
            String temp = it.nextElement();
            log.info("[{}] : [{}]", temp, request.getHeader(temp));
        }
        log.info("token : [{}]", request.getHeader("authorization"));
        ResponseEntity<AuthGoogleInfo> googleResponse = null;
        try {
            String googleUrl = "https://www.googleapis.com/oauth2/v1/tokeninfo?access_token=" + request.getHeader("authorization");
            googleResponse = restTemplate.exchange(googleUrl, HttpMethod.GET, null, AuthGoogleInfo.class);
        } catch (HttpClientErrorException e){
            log.debug("Invalid Google Token");
            return false;
        }
        AuthGoogleInfo authData = googleResponse.getBody();
        Long userId = Long.valueOf(authData.getUserId().substring(2));// String to Long
        log.info("userId cut : [{}]", userId);

        request.setAttribute("userId", userId);
        request.setAttribute("userEmail", authData.getEmail());
        log.info("결과 : Attr [{}]",request.getAttribute("userId"));
        log.info(">> AuthCheckInterceptor preHandle End<<");
        return true;
    }
}
