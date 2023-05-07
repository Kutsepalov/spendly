package com.acceleron.spendly.controller;

import com.acceleron.spendly.core.dto.UserGroupDto;
import com.acceleron.spendly.core.service.UserGroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/user/groups")
@RequiredArgsConstructor
public class UserGroupController {

    private final UserGroupService userGroupService;

    @GetMapping
    public List<UserGroupDto> getUserGroups(){
        return userGroupService.findAll();
    }

    @PostMapping
    public UserGroupDto createUserGroup(@RequestBody UserGroupDto userGroupDto){
        return userGroupService.save(userGroupDto);
    }

    @PutMapping
    public UserGroupDto updateUserGroup(@RequestBody UserGroupDto userGroupDto){
        return userGroupService.update(userGroupDto);
    }

    @DeleteMapping("/{id}")
    public UserGroupDto deleteUserGroup(@PathVariable UUID id){
        return userGroupService.delete(id);
    }

    @PostMapping("/{groupId}/invite/{userId}")
    public ResponseEntity<Void> inviteUserIntoGroup(@PathVariable UUID userId, @PathVariable UUID groupId){
        userGroupService.inviteUserIntoGroup(userId, groupId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
