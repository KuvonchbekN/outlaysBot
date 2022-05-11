package by.kuvonchbekn.outlaysbot.assemblers;

import by.kuvonchbekn.outlaysbot.controller.GroupController;
import by.kuvonchbekn.outlaysbot.entity.Group;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class GroupAssembler implements RepresentationModelAssembler<Group, EntityModel<Group>> {

    @Override
    public EntityModel<Group> toModel(Group group) {
        return EntityModel.of(group, linkTo(methodOn(GroupController.class).getGroupById(group.getId())).withSelfRel(),
                linkTo(methodOn(GroupController.class).getGroupList()).withRel("groups"));
    }

    public CollectionModel<EntityModel<Group>> toCustomCollectionModel(List<Group> allGroupList){
        List<EntityModel<Group>> groups = allGroupList.stream().map(this::toModel).collect(Collectors.toList());
        return CollectionModel.of(groups,
                linkTo(methodOn(GroupController.class).getGroupList()).withSelfRel());
    }

}
