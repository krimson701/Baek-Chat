package com.krimson701.baekchat.controller;


import com.krimson701.baekchat.controller.enums.RelationType;
import com.krimson701.baekchat.domain.Relation;
import com.krimson701.baekchat.service.RelationService;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Api(value="Relation-controller", description="Relation controller")
@RequestMapping("/relation")
@RestController
public class RelationController {

    @Autowired
    RelationService relationService;

    @ApiOperation(
            value = "유저 관계 조회"
            , notes = "유저 관계 조회"
    )
    @ApiResponses(value={
            @ApiResponse(code=200, message="complete")
    })
    @RequestMapping(value = "/get/{userId}/{relation}", method = RequestMethod.GET)
    public ResponseEntity<List<Relation>> getRelations(
            @ApiParam(value = "유저 ID 키", required = true) @PathVariable final long userId,
            @ApiParam(value = "관계 타입") @PathVariable(required = false) final RelationType relation){
        List<Relation> rtnDto = relationService.getRelations(userId, relation);

        return new ResponseEntity<>(rtnDto, HttpStatus.OK);
    }


    @ApiOperation(
            value = "유저 관계 추가"
            , notes = "유저 관계 추가"
    )
    @ApiResponses(value={
            @ApiResponse(code=200, message="complete")
    })
    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    public ResponseEntity<Void> insertRelation(
            @ApiParam(value = "유저 ID 키", required = true) @RequestParam final long userId,
            @ApiParam(value = "상대 ID 키", required = true) @RequestParam final long relatedId,
            @ApiParam(value = "관계 타입", required = true) @RequestParam final RelationType relation) throws Exception {
        relationService.insertRelation(userId, relatedId, relation);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ApiOperation(
            value = "유저 관계 삭제"
            , notes = "유저 관계 삭제"
    )
    @ApiResponses(value={
            @ApiResponse(code = 200, message = "complete")
    })
    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    public ResponseEntity<Void> deleteRelation(
            @ApiParam(value = "관계 ID 키", required = true) @RequestParam final long Id) throws Exception {
        relationService.deleteRelation(Id);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ApiOperation(
            value = "유저 관계 갱신"
            , notes = "유저 관계 갱신"
    )
    @ApiResponses(value={
            @ApiResponse(code = 200, message = "complete")
    })
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ResponseEntity<Void> updateRelation(
            @ApiParam(value = "관계 ID 키", required = true) @RequestParam final long Id,
            @ApiParam(value = "관계 타입", required = true) @RequestParam final RelationType relation) throws Exception {
        relationService.updateRelation(Id, relation);

        return new ResponseEntity<>(HttpStatus.OK);
    }

}
