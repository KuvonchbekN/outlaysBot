package by.kuvonchbekn.outlaysbot.controller;


import by.kuvonchbekn.outlaysbot.assemblers.GroupAssembler;
import by.kuvonchbekn.outlaysbot.entity.Group;
import by.kuvonchbekn.outlaysbot.service.GroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/group")
public class GroupController {
    private final GroupService groupService;
    private final GroupAssembler groupAssembler;

    @GetMapping
    public ResponseEntity<?> getGroupList(){
        List<Group> groupList = groupService.getGroupList();
        CollectionModel<EntityModel<Group>> groupModels =
                groupAssembler.toCustomCollectionModel(groupList);
        return ResponseEntity.ok(groupModels);
    }

    @GetMapping("/{groupId}")
    public ResponseEntity<?> getGroupById(@PathVariable String groupId){
        Group groupById = groupService.getGroupById(groupId);
        EntityModel<Group> groupEntityModel = groupAssembler.toModel(groupById);
        return ResponseEntity.ok(groupEntityModel);
    }

    @PostMapping
    public ResponseEntity<?> createGroup(@RequestBody Group group){
        String createdId = groupService.create(group);
        return ResponseEntity.created(ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(createdId).toUri()).build();
    }

    @PutMapping("/{groupId}")
    public ResponseEntity<?> updateGroup(@RequestBody Group group, @PathVariable String groupId){
        String updatedId = groupService.update(groupId, group);
        return ResponseEntity.ok(updatedId);
    }

    @DeleteMapping("/{groupId}")
    public ResponseEntity<?> deleteGroup(@PathVariable String groupId){
        groupService.delete(groupId);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/groupAdmin/add")
    public ResponseEntity<?> addAdminToGroup(@RequestParam String adminId, @RequestParam String groupId){
        groupService.addAdminToGroup(adminId, groupId);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/groupAdmin/remove")
    public ResponseEntity<?> removeAdminFromGroup(@RequestParam String adminId, @RequestParam String groupId){
        groupService.removeAdminFromGroup(adminId, groupId);
        return ResponseEntity.ok().build();
    }


}

