package by.kuvonchbekn.outlaysbot.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PaymentDto {

    private BigDecimal amount;
    private String description;
    @JsonProperty(value = "payment_maker_id")
    private String paymentMakerId;
    @JsonProperty(value = "group_id")
    private String groupId;
    @JsonProperty(value = "payment_date")
    private LocalDate paymentDate;


}
