package com.krimson701.baekchat.controller;

import com.krimson701.baekchat.domain.User;
import com.krimson701.baekchat.model.UserDto;
import com.krimson701.baekchat.model.UserSearchModel;
import com.krimson701.baekchat.service.UserService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


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
            @ApiParam(value = "유저 ID", required = true) @RequestParam final String userId){

        User result = userService.getUser(userId);

        return new ResponseEntity<>(result, HttpStatus.OK);
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
            @ApiParam(value = "유저 ID 키", required = true) @RequestParam(required = true) int id,
            @ModelAttribute final UserDto userDto) {

        userService.updateUser(id, userDto);

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
            @ApiParam(value = "유저 ID 키", required = true) @RequestParam(required = true) int id) {

        userService.deleteUser(id);

        return new ResponseEntity<Void>(HttpStatus.OK);
    }

}
