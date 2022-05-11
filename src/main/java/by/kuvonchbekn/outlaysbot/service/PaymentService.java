package by.kuvonchbekn.outlaysbot.service;


import by.kuvonchbekn.outlaysbot.dto.PaymentDto;
import by.kuvonchbekn.outlaysbot.dto.response.PaymentDistributionDto;
import by.kuvonchbekn.outlaysbot.entity.Payment;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public interface PaymentService {
    List<Payment> getPaymentList(); //this gets the list of payments regardless of the month or group

    Payment getPaymentById(String paymentId);

    String createPayment(PaymentDto paymentDto);

    String updatePayment(Payment payment, String paymentId);

    void deletePayment(String paymentId);

    List<Payment> getPaymentListByMonthAndGroupId(LocalDate date, String groupId);

    List<Payment> getPaymentListByUserIdAndMonthAndGroupId(LocalDate date, String userId, String groupId);

    List<PaymentDistributionDto> calculatePaymentForMonthForGroup(LocalDate date, String groupId);
}
