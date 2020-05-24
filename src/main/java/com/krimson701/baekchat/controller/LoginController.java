package com.krimson701.baekchat.controller;

import com.krimson701.baekchat.model.UserDto;
import com.krimson701.baekchat.repository.UserRepository;
import com.krimson701.baekchat.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@Api(value="Oauth-controller", description="Oauth controller")
@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserService userService;

    /**
     * 최초 로그인시 이미 등록된 User인지 체크
     * 없다면 강제로 회원가입.
     * @param userId
     * @param userEmail
     * @return
     */
    @ApiOperation(
            value = "로그인 API"
            , notes = "로그인 API "
    )
    @ApiResponses(value={
            @ApiResponse(code=200, message="success"),
    })
    @RequestMapping(value="/signIn", method= RequestMethod.POST)
    public ResponseEntity<Void> signIn(
            @ApiIgnore @RequestAttribute("userId") Long userId
            , @ApiIgnore @RequestAttribute("userEmail") String userEmail) {
        // 넘어오는 User 데이터가 많아지면 id와 email만 받는게아니라 userAuthModel형식으로 모델로 받을것
        // 그러려면 당연히 인터셉터에서 모델로 보내야한다.
        if(!userRepository.findById(userId).isPresent()) {
            log.info("newUser insert Execute");
            UserDto newUser = new UserDto(userId, userEmail);
            userService.insertUser(newUser);
        }
        log.info("userId : [{}]  Email : [{}]", userId, userEmail);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
    }
}
