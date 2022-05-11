package by.kuvonchbekn.outlaysbot.assemblers;

import by.kuvonchbekn.outlaysbot.controller.UserController;
import by.kuvonchbekn.outlaysbot.entity.User;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class UserAssembler implements RepresentationModelAssembler<User, EntityModel<User>> {
    @Override
    public EntityModel<User> toModel(User user) {
        return EntityModel.of(user, linkTo(methodOn(UserController.class).getUserById(user.getId())).withSelfRel(),
                linkTo(methodOn(UserController.class).getUserList()).withRel("users"));
    }

    public CollectionModel<EntityModel<User>> toCustomCollectionModel(List<User> allUserList){
        List<EntityModel<User>> users = allUserList.stream().map(this::toModel).collect(Collectors.toList());
        return CollectionModel.of(users,
                linkTo(methodOn(UserController.class).getUserList()).withSelfRel());
    }
}
