package by.kuvonchbekn.outlaysbot.dto.response;


import by.kuvonchbekn.outlaysbot.entity.Payment;
import by.kuvonchbekn.outlaysbot.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PaymentDistributionDto {
    private BigDecimal amountToBePaid;
    private User user;
//    private List<Payment> productPaymentListForEachUser;
}
