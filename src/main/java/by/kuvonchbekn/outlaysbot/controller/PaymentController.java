package by.kuvonchbekn.outlaysbot.controller;


import by.kuvonchbekn.outlaysbot.assemblers.PaymentAssembler;
import by.kuvonchbekn.outlaysbot.dto.PaymentDto;
import by.kuvonchbekn.outlaysbot.dto.response.PaymentDistributionDto;
import by.kuvonchbekn.outlaysbot.entity.Payment;
import by.kuvonchbekn.outlaysbot.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.jni.Local;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/payment")
public class PaymentController {
    private final PaymentAssembler paymentAssembler;
    private final PaymentService paymentService;

    @GetMapping //for admins
    public ResponseEntity<?> getAllPayments(){
        List<Payment> paymentList = paymentService.getPaymentList();
        CollectionModel<EntityModel<Payment>> entityModels =
                paymentAssembler.toCustomCollectionModel(paymentList);
        return ResponseEntity.ok(entityModels);
    }

    @GetMapping("/{paymentId}")
    public ResponseEntity<?> getPaymentById(@PathVariable String paymentId){
        Payment paymentById = paymentService.getPaymentById(paymentId);
        EntityModel<Payment> paymentEntityModel = paymentAssembler.toModel(paymentById);
        return ResponseEntity.ok(paymentEntityModel);
    }

    @PostMapping
    public ResponseEntity<?> createPayment(@RequestBody PaymentDto paymentDto){
        String paymentId = paymentService.createPayment(paymentDto);
        return ResponseEntity.ok(paymentId);
    }

    @PutMapping("/{paymentId}")
    public ResponseEntity<?> updatePayment(@RequestBody Payment payment, @PathVariable String paymentId){
        String paymentIdCreated = paymentService.updatePayment(payment, paymentId);
        return ResponseEntity.ok(paymentIdCreated);
    }

    @DeleteMapping("/{paymentId}")
    public ResponseEntity<?> deletePayment(@PathVariable String paymentId){
        paymentService.deletePayment(paymentId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/selection-month")
    public ResponseEntity<?> getPaymentListByMonthAndGroupId(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date, @RequestParam String groupId){
        List<Payment> paymentListByMonthAndGroupId = paymentService.getPaymentListByMonthAndGroupId(date, groupId);
        CollectionModel<EntityModel<Payment>> paymentList = paymentAssembler.toCustomCollectionModel(paymentListByMonthAndGroupId);
        return ResponseEntity.ok(paymentList);
    }

    @GetMapping("/user-payment/month")
    public ResponseEntity<?> getPaymentListByUserIdAndMonthAndGroupId(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date, @RequestParam String userId, @RequestParam String groupId){
        List<Payment> paymentListByUserIdAndMonthAndGroupId = paymentService.getPaymentListByUserIdAndMonthAndGroupId(date, userId, groupId);
        CollectionModel<EntityModel<Payment>> entityModels = paymentAssembler.toCustomCollectionModel(paymentListByUserIdAndMonthAndGroupId);
        return ResponseEntity.ok(entityModels);
    }

    @GetMapping("/calculate/payment")
    public ResponseEntity<?> calculatePaymentForMonthForGroup(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date, String groupId){
        List<PaymentDistributionDto> paymentDistributionDtoList = paymentService.calculatePaymentForMonthForGroup(date, groupId);
        return ResponseEntity.ok(paymentDistributionDtoList);
    }


}



















