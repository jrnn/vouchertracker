package vouchertracker.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.time.LocalDate;
import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Voucher extends UUIDPersistable {

    private String voucherId;
    private String issuedAt;
    private LocalDate issuedOn;
    private LocalDate receivedOn;
    private Long purchaseAmount; // use integer to avoid floating-point rounding error
    private Long refundAmount;   // use integer to avoid floating-point rounding error
    private boolean stamped;

    @JsonIgnore
    @ManyToOne(optional = false)
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;

    @JsonIgnore
    @ManyToOne(optional = false)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    private String lastEditedBy;
    private LocalDateTime lastEditedOn;

}