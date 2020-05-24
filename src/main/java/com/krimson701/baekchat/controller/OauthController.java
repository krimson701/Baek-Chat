package com.krimson701.baekchat.controller;

import com.krimson701.baekchat.model.AuthGoogleInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.nio.charset.Charset;
import java.util.Enumeration;

@Slf4j
@Api(value="Oauth-controller", description="Oauth controller")
@RestController
@RequestMapping("/oauth")
public class OauthController {

    @Autowired
    RestTemplate restTemplate;

    @ApiOperation(
            value = "구글 토큰 인증 요청"
            , notes = "구글 토큰 인증 요청한다. "
    )
    @ApiResponses(value={
            @ApiResponse(code=200, message="success"),
    })
    @RequestMapping(value="/google", method= RequestMethod.POST)
    public ResponseEntity<Void> googleAuth(@ApiIgnore HttpServletRequest request) {

        log.info(">> AuthCheckInterceptor preHandle Start<<");

        log.info("request : [{}]",request);
        Enumeration headerNames = request.getHeaderNames();
        while(headerNames.hasMoreElements()){
            String name = (String)headerNames.nextElement();
            String value = request.getHeader(name);
            log.info(name + " : " + value);
        }
        String googleUrl = "https://www.googleapis.com/oauth2/v1/tokeninfo?access_token=" + request.getHeader("authorization");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application","json", Charset.forName("UTF-8")));
        ResponseEntity<AuthGoogleInfo> googleResponse = restTemplate.exchange(googleUrl, HttpMethod.GET, null, AuthGoogleInfo.class);

        log.info("response: [{}]", googleResponse.getBody());

        log.info(">> AuthCheckInterceptor preHandle End<<");


        return new ResponseEntity<>(HttpStatus.OK);
    }

    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
    }
}
