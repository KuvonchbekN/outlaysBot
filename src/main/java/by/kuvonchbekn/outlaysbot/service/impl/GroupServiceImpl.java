package by.kuvonchbekn.outlaysbot.service.impl;

import by.kuvonchbekn.outlaysbot.entity.Group;
import by.kuvonchbekn.outlaysbot.entity.User;
import by.kuvonchbekn.outlaysbot.exception.specificExceptions.AlreadyGroupAdmin;
import by.kuvonchbekn.outlaysbot.exception.specificExceptions.GroupNotFoundException;
import by.kuvonchbekn.outlaysbot.exception.specificExceptions.UserNotFoundException;
import by.kuvonchbekn.outlaysbot.repo.GroupRepo;
import by.kuvonchbekn.outlaysbot.repo.UserRepo;
import by.kuvonchbekn.outlaysbot.service.GroupService;
import by.kuvonchbekn.outlaysbot.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Transactional
@Service
@RequiredArgsConstructor
public class GroupServiceImpl implements GroupService {
    private final GroupRepo groupRepo;
    private final MessageSource messageSource;
    private final UserService userService;

    @Override
    public String create(Group group) {
        Group savedGroup = groupRepo.save(group);
        return savedGroup.getId();
    }

    @Override
    public void delete(String groupId) {
        getGroupById(groupId); //just checks and throws an error if the user does not exist in the registry!
        groupRepo.deleteById(groupId);
    }

    @Override
    public String update(String groupId, Group group) {
        Group groupFound = getGroupById(groupId);
        groupFound.setGroupName(group.getGroupName());
        groupFound.setMemberNumber(group.getMemberNumber());

        Group updatedGroup = groupRepo.save(groupFound);

        return updatedGroup.getId();
    }

    @Override
    public List<Group> getGroupList() {
        return groupRepo.findAll();
    }

    @Override
    public Group getGroupById(String groupId) {
        Optional<Group> groupById = groupRepo.findById(groupId);
        if (groupById.isEmpty()) throw new GroupNotFoundException(messageSource.getMessage("api.error.group.not.found",null, Locale.ENGLISH));

        return groupById.get();
    }

    @Override
    public void addAdminToGroup(String adminId, String groupId) {
        Group group = getGroupById(groupId);
        User newAdmin = userService.getUserById(adminId);

        Set<User> admins = group.getAdmins();
        admins.stream().filter(admin -> admin.getId().equals(adminId)).findFirst().orElseThrow(()->{
            throw new AlreadyGroupAdmin(messageSource.getMessage("api.error.already.group.admin", null, Locale.ENGLISH));
        });

        admins.add(newAdmin);
        groupRepo.save(group);
    }


    public void removeAdminFromGroup(String adminId, String groupId){
        Group groupById = getGroupById(groupId);
        User userById = userService.getUserById(adminId);

        groupById.getAdmins().remove(userById);
        groupRepo.save(groupById);
    }



}
