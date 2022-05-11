package by.kuvonchbekn.outlaysbot.service;

import by.kuvonchbekn.outlaysbot.entity.Group;
import by.kuvonchbekn.outlaysbot.entity.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface GroupService {
    String create(Group group);
    void delete(String groupId);
    String update(String groupId, Group group);
    List<Group> getGroupList();
    Group getGroupById(String groupId);

    void addAdminToGroup(String adminId, String groupId);

    void removeAdminFromGroup(String adminId, String groupId);
}
