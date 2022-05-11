package by.kuvonchbekn.outlaysbot.repo;

import by.kuvonchbekn.outlaysbot.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface PaymentRepo extends JpaRepository<Payment, String> {
    @Query(value = "SELECT * FROM Payment p WHERE p.payment_date > ?1 AND p.payment_date<?2 AND p.group_id = ?3 AND p.has_been_paid = FALSE", nativeQuery = true)
    List<Payment> getPaymentByPaymentDateAndGroup_Id(LocalDate startDay, LocalDate endDay, String group_id);


    @Query(value = "SELECT * FROM Payment p WHERE p.payment_date > ?1 AND p.payment_date<?2 AND p.group_id = ?3 AND p.payment_maker_id=?4 AND p.has_been_paid = FALSE", nativeQuery = true)
    List<Payment> getPaymentListByUserIdAndMonthAndGroupId(LocalDate startDate, LocalDate endDate, String groupId, String makerId);


}
