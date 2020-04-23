package com.krimson701.baekchat.controller;

import com.krimson701.baekchat.model.UserSearchModel;
import com.krimson701.baekchat.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.security.PublicKey;


@Api(value = "user-controller")
@RequestMapping("/user")
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @ApiOperation(
            value = "유저 리스트 조회"
            , notes = "유저 리스트 조회"
    )
    @ApiResponses(value={
            @ApiResponse(code=200, message="complete")
    })
    @RequestMapping(value = "searchList", method = RequestMethod.GET)
    public ResponseEntity<Void> UserListSearch(@ModelAttribute UserSearchModel userSearchModel){

        return new ResponseEntity<Void>(HttpStatus.OK);
    }
}
