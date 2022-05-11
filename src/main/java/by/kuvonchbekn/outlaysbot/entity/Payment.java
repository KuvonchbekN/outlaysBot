package by.kuvonchbekn.outlaysbot.entity;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.sun.istack.NotNull;
import lombok.*;
import org.springframework.beans.factory.support.SecurityContextProvider;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Locale;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
@NoArgsConstructor
public class Payment extends BaseEntity{
    @NotNull
    private BigDecimal amount;

    @JsonProperty(value = "has_been_paid")
    private boolean hasBeenPaid;

    private String description;

    @JsonDeserialize
    private LocalDate paymentDate = LocalDate.now();

    @ManyToOne(optional = false)
    @JsonProperty(value = "payment_maker")
    private User paymentMaker;

    @ManyToOne(optional = false)
    private Group group;
}
