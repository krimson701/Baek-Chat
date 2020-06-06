package com.krimson701.baekchat.controller;

import com.krimson701.baekchat.domain.User;
import com.krimson701.baekchat.model.UserDto;
import com.krimson701.baekchat.model.UserSearchModel;
import com.krimson701.baekchat.service.UserService;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;


@Api(value = "user-controller")
@RequestMapping("/user")
@RestController
@Slf4j
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
    @RequestMapping(value = "/info", method = RequestMethod.GET)
    public ResponseEntity<User> getUser(
            @ApiIgnore @RequestAttribute("userId") Long userId){

        User result = userService.getUser(userId);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @ApiOperation(
            value = "유저 검색"
            , notes = "유저 검색"
    )
    @ApiResponses(value={
            @ApiResponse(code=200, message="complete")
    })
    @RequestMapping(value = "/search/{userName}", method = RequestMethod.GET)
    public ResponseEntity<User> searchUser(
            @ApiParam(value = "유저 이메일", required = true) @PathVariable String userName){
        User result = userService.searchUser(userName);

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

    @ApiOperation(
            value = "유저 리스트 조회"
            , notes = "유저 리스트 조회"
    )
    @ApiResponses(value={
            @ApiResponse(code=200, message="complete")
    })
    @RequestMapping(value = "/searchList", method = RequestMethod.GET)
    public ResponseEntity<Page<User>> UserListSearch(
            @ModelAttribute final UserSearchModel userSearchModel,
            @PageableDefault(size=5, sort="id") final Pageable pageable){

        Page<User> rtnDto = userService.getUserList(userSearchModel, pageable);

        return new ResponseEntity<>(rtnDto, HttpStatus.OK);
    }


}
