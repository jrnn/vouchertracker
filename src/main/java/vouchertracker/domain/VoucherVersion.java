package vouchertracker.domain;

import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.AUTO;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class VoucherVersion implements Serializable {

    @Id
    @GeneratedValue(strategy = AUTO)
    private Long id;

    private String voucherId;
    private String issuedAt;
    private LocalDate issuedOn;
    private LocalDate receivedOn;
    private Double purchaseAmount;
    private Double refundAmount;
    private boolean stamped;

    @Column(nullable = false)
    private Integer versionNumber = 1;

    @Column(updatable = false, nullable = false)
    private String createdBy;

    @Column(updatable = false, nullable = false)
    private LocalDate createdOn;

    @ManyToOne(optional = false)
    @JoinColumn(name = "voucher_entry_id", nullable = false)
    private VoucherEntry voucherEntry;

}