package com.krimson701.baekchat.controller;

import com.krimson701.baekchat.domain.User;
import com.krimson701.baekchat.model.UserDto;
import com.krimson701.baekchat.service.UserService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;


@Api(value = "user-controller")
@RequestMapping("/user")
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @ApiOperation(
            value = "유저 확인"
            , notes = "유저 확인"
    )
    @ApiResponses(value={
            @ApiResponse(code=200, message="complete")
    })
    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public ResponseEntity<User> getUser(
            @ApiIgnore @RequestAttribute("userId") Long userId){

        User result = userService.getUser(userId);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @ApiOperation(
            value = "유저 등록"
            , notes = "유저 등록"
    )
    @ApiResponses(value={
            @ApiResponse(code=200, message="complete")
    })
    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    public ResponseEntity<Void> insertUser(
            @ModelAttribute final UserDto userDto) {

        userService.insertUser(userDto);

        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    @ApiOperation(
            value = "유저 수정"
            , notes = "유저 수정"
    )
    @ApiResponses(value={
            @ApiResponse(code=200, message="complete")
    })
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ResponseEntity<Void> updateUser(
            @ApiIgnore @RequestAttribute("userId") Long userId,
            @ModelAttribute final UserDto userDto) {

        userService.updateUser(userId, userDto);

        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    @ApiOperation(
            value = "유저 삭제"
            , notes = "유저 삭제"
    )
    @ApiResponses(value={
            @ApiResponse(code=200, message="complete")
    })
    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    public ResponseEntity<Void> deleteUser(
            @ApiIgnore @RequestAttribute("userId") Long userId) {

        userService.deleteUser(userId);

        return new ResponseEntity<Void>(HttpStatus.OK);
    }

}
