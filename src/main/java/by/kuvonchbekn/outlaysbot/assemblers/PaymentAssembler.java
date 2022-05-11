package by.kuvonchbekn.outlaysbot.assemblers;

import by.kuvonchbekn.outlaysbot.controller.GroupController;
import by.kuvonchbekn.outlaysbot.controller.PaymentController;
import by.kuvonchbekn.outlaysbot.entity.Group;
import by.kuvonchbekn.outlaysbot.entity.Payment;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class PaymentAssembler implements RepresentationModelAssembler<Payment, EntityModel<Payment>> {
    @Override
    public EntityModel<Payment> toModel(Payment payment) {
        return EntityModel.of(payment, linkTo(methodOn(PaymentController.class).getPaymentById(payment.getId())).withSelfRel(),
                linkTo(methodOn(PaymentController.class).getAllPayments()).withRel("payments"));
    }

    public CollectionModel<EntityModel<Payment>> toCustomCollectionModel(List<Payment> allPaymentList){
        List<EntityModel<Payment>> payments = allPaymentList.stream().map(this::toModel).collect(Collectors.toList());
        return CollectionModel.of(payments,
                linkTo(methodOn(PaymentController.class).getAllPayments()).withSelfRel());
    }

}
