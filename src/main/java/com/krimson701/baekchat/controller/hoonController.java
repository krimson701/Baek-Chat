package com.krimson701.baekchat.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Api(value="hoon-controller", description="hoon controller")
@RestController
@RequestMapping("/test")
public class hoonController {


    @ApiOperation(
            value = "test"
            , notes = "test"
    )
    @ApiResponses(value={
            @ApiResponse(code=200, message="complete")
    })
    @RequestMapping(value = "/hoon", method = RequestMethod.POST)
    public ResponseEntity<ResponseEntity> uosPostTest() {

        HttpComponentsClientHttpRequestFactory clientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory(
                HttpClientBuilder.create().build());
        RestTemplate restTemplate = new RestTemplate(clientHttpRequestFactory);


        HttpHeaders headers = new HttpHeaders();
        headers.add("Accept-Encoding","gzip, deflate, br");
        headers.add("Accept-Language","ko,en-US;q=0.9,en;q=0.8");
        headers.add("Connection","keep-alive");
        headers.add("Content-Type","application/x-www-form-urlencoded");
        headers.add("Cookie","WMONID=51Zm2aTawKf; LOGIN_DIV=1; WT_FPC=id=28f8520f84ff5df57bf1592118346047:lv=1592118346047:ss=1592118346047; NetFunnel_ID=; JSESSIONID=zWZL8hSKxhKGy6GTTecCPWYgGtp5uLVLwHp3TbyZ381SAXaq4GzOYTFTyaV8t6D7.wise02_servlet_engine1");
        headers.add("Host","wise.uos.ac.kr");
        headers.add("Origin","https://wise.uos.ac.kr");
        headers.add("Referer","https://wise.uos.ac.kr/uosdoc/include/contentXf.jsp?xtmPagego=ucr/UcrPersLecTimePrt.xtm&strMenuId=STUD00260&PgmId=UcrPersLecTimePrt&callProc=UcrPersLecTimePrt&pgmType=undefined&workInfoYn=undefined");
        headers.add("req-charset","UTF-8");
        headers.add("req-protocol","application/x-www-form-urlencoded");
        headers.add("res-charset","euc-kr");
        headers.add("res-protocol","xml");
        headers.add("Sec-Fetch-Dest","empty");
        headers.add("Sec-Fetch-Mode","cors");
        headers.add("Sec-Fetch-Site","same-origin");

        MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
        map.add("strSchYear","2019");
        map.add("strSmtCd","10");
        map.add("_XML_","XML");
        map.add("_strMenuId","stud00260");
        map.add("_COMMAND_","list");

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(map, headers);

        String url = "https://wise.uos.ac.kr/uosdoc/ucr.UcrPersLecTimePrt.do";

        ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);

        log.info("is not english?");
        return new ResponseEntity<ResponseEntity>(response, HttpStatus.OK);
    }
}
