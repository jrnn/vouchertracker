package vouchertracker.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import vouchertracker.utility.CustomParser;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Comment extends UUIDPersistable {

    private LocalDateTime exactTime = LocalDateTime.now();
    private String author;

    @Column(length = 1000)
    private String content;

    @JsonIgnore
    @ManyToOne(optional = true)
    @JoinColumn(name = "voucher_id", nullable = true)
    private Voucher voucher = null;

    @JsonIgnore
    @ManyToOne(optional = true)
    @JoinColumn(name = "customer_id", nullable = true)
    private Customer customer = null;

    @JsonIgnore
    @ManyToOne(optional = true)
    @JoinColumn(name = "shipment_id", nullable = true)
    private Shipment shipment = null;

    public String getMeta() {
        return author + " (" + CustomParser.parseTimestamp(exactTime) + ")";
    }

    @Override
    public String toString() {
        return getId();
    }

}