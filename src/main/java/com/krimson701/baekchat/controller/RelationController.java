package com.krimson701.baekchat.controller;


import com.krimson701.baekchat.domain.User;
import com.krimson701.baekchat.service.RelationService;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@Api(value="Relation-controller", description="Relation controller")
@RequestMapping("/relation")
@RestController
public class RelationController {

    @Autowired
    RelationService relationService;

    @ApiOperation(
            value = "친구 관계 조회"
            , notes = "친구 관계 조회"
    )
    @ApiResponses(value={
            @ApiResponse(code=200, message="complete")
    })
    @RequestMapping(value = "/getFriends/{userId}", method = RequestMethod.GET)
    public ResponseEntity<List<User>> UserRelations(
            @ApiParam(value = "유저 ID 키") @PathVariable final int userId){
        List<User> rtnDto = relationService.getFriends(userId);

        return new ResponseEntity<List<User>>(rtnDto, HttpStatus.OK);
    }

}
