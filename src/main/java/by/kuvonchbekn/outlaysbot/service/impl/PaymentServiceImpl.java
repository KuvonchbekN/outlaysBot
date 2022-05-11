package by.kuvonchbekn.outlaysbot.service.impl;

import by.kuvonchbekn.outlaysbot.dto.PaymentDto;
import by.kuvonchbekn.outlaysbot.dto.response.PaymentDistributionDto;
import by.kuvonchbekn.outlaysbot.entity.Group;
import by.kuvonchbekn.outlaysbot.entity.Payment;
import by.kuvonchbekn.outlaysbot.entity.User;
import by.kuvonchbekn.outlaysbot.exception.specificExceptions.PaymentNotFoundException;
import by.kuvonchbekn.outlaysbot.repo.PaymentRepo;
import by.kuvonchbekn.outlaysbot.service.GroupService;
import by.kuvonchbekn.outlaysbot.service.PaymentService;
import by.kuvonchbekn.outlaysbot.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.time.YearMonth;
import java.util.*;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {
    private final PaymentRepo paymentRepo;
    private final MessageSource messageSource;
    private final UserService userService;
    private final GroupService groupService;

    @Override
    public List<Payment> getPaymentList() {
        return paymentRepo.findAll();
    }

    @Override
    public Payment getPaymentById(String paymentId) {
        Optional<Payment> byId = paymentRepo.findById(paymentId);
        if (byId.isEmpty())
            throw new PaymentNotFoundException(messageSource.getMessage("api.error.payment.not.found", null, Locale.ENGLISH));
        return byId.get();
    }

    @Override
    public String createPayment(PaymentDto paymentDto) {
        User paymentMaker = userService.getUserById(paymentDto.getPaymentMakerId());
        Group group = groupService.getGroupById(paymentDto.getGroupId());

        Payment payment = new Payment();
        payment.setPaymentMaker(paymentMaker);
        payment.setDescription(paymentDto.getDescription());
        payment.setAmount(paymentDto.getAmount());
        payment.setPaymentDate(paymentDto.getPaymentDate());
        payment.setGroup(group);

        Payment savedPayment = paymentRepo.save(payment);
        return savedPayment.getId();
    }

    @Override
    public String updatePayment(Payment payment, String paymentId) {
        Payment paymentById = getPaymentById(paymentId); //throws an exception, if not found!

        paymentById.setPaymentDate(LocalDate.now());
        paymentById.setPaymentMaker(payment.getPaymentMaker());
        paymentById.setAmount(payment.getAmount());
        paymentById.setGroup(payment.getGroup());
        paymentById.setHasBeenPaid(payment.isHasBeenPaid());
        paymentById.setDescription(payment.getDescription());
        Payment updatedPaymentId = paymentRepo.save(paymentById);

        return updatedPaymentId.getId();
    }

    @Override
    public void deletePayment(String paymentId) {
        Payment payment = getPaymentById(paymentId);
        paymentRepo.delete(payment);
    }

    @Override
    public List<Payment> getPaymentListByMonthAndGroupId(LocalDate date, String groupId) {
        Month month = date.getMonth();
        int year = date.getYear();


        YearMonth yearMonthObject = YearMonth.of(year, month);
        int daysInMonth = yearMonthObject.lengthOfMonth();

        LocalDate startingDate = LocalDate.of(year, month, 1);
        LocalDate endDate = LocalDate.of(year, month, daysInMonth);

        return paymentRepo.getPaymentByPaymentDateAndGroup_Id(startingDate, endDate, groupId);
    }

    @Override
    public List<Payment> getPaymentListByUserIdAndMonthAndGroupId(LocalDate date, String userId, String groupId) {
        LocalDate startingDate = getStartingDate(date);
        LocalDate endDate = getEndDate(date);

        return paymentRepo.getPaymentListByUserIdAndMonthAndGroupId(startingDate, endDate, groupId, userId);
    }

    /*this method is actually not returning, how much someone should give and take but
     actually this is returning the list of how much each specific user has spent in
     specific month in the specific group*/
    @Override
    public List<PaymentDistributionDto> calculatePaymentForMonthForGroup(LocalDate date, String groupId) {
        groupService.getGroupById(groupId);

        List<PaymentDistributionDto> paymentDistributionDtoList = new ArrayList<>();

        LocalDate startingDate = getStartingDate(date);
        LocalDate endDate = getEndDate(date);
        Map<String, BigDecimal> eachUserPayment = new HashMap<>();

        List<Payment> paymentsToBePaidForSpecificGroupAndDate = paymentRepo.getPaymentByPaymentDateAndGroup_Id(startingDate, endDate, groupId);
        paymentsToBePaidForSpecificGroupAndDate.forEach(payment -> {
            String userId = payment.getPaymentMaker().getId();
            if (eachUserPayment.containsKey(userId)) {
                BigDecimal amount = eachUserPayment.get(userId);
                BigDecimal totalAmount = amount.add(payment.getAmount());
                eachUserPayment.put(userId, totalAmount);
            } else {
                eachUserPayment.put(payment.getPaymentMaker().getId(), payment.getAmount());
            }
        });

        eachUserPayment.forEach((key, value) -> {
            PaymentDistributionDto paymentDistributionDto = new PaymentDistributionDto(value, userService.getUserById(key));
            paymentDistributionDtoList.add(paymentDistributionDto);
        });
        return paymentDistributionDtoList;
    }


    private LocalDate getStartingDate(LocalDate date) {
        Month month = date.getMonth();
        int year = date.getYear();

        return LocalDate.of(year, month, 1);
    }

    private LocalDate getEndDate(LocalDate date) {
        Month month = date.getMonth();
        int year = date.getYear();

        YearMonth yearMonth = YearMonth.of(year, month);
        int daysOfMonth = yearMonth.lengthOfMonth();
        return LocalDate.of(year, month, daysOfMonth);
    }
}
