package vouchertracker.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
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
    private Long purchaseAmount;
    private Long refundAmount;
    private boolean stamped;
    private boolean prepaid;

    @JsonIgnore
    @ManyToOne(optional = false)
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;

    @JsonIgnore
    @ManyToOne(optional = false)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @JsonIgnore
    @ManyToOne(optional = true)
    @JoinColumn(name = "shipment_id", nullable = true)
    private Shipment shipment = null;

    @JsonIgnore
    @OneToMany(mappedBy = "voucher")
    private List<Attachment> attachments = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "voucher")
    private List<Comment> comments = new ArrayList<>();

    private String lastEditedBy;
    private LocalDateTime lastEditedOn;

    @Override
    public String toString() {
        return getId();
    }

}