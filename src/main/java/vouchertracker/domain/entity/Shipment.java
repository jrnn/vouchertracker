package vouchertracker.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.NamedSubgraph;
import javax.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NamedEntityGraph(
        name = "shipment-entity-graph",
        attributeNodes = {
            @NamedAttributeNode(
                    value = "vouchers",
                    subgraph = "vouchers-subgraph"
            )
        },
        subgraphs = {
            @NamedSubgraph(
                    name = "vouchers-subgraph",
                    attributeNodes = {
                        @NamedAttributeNode("account"),
                        @NamedAttributeNode("customer")
                    }
            )
        }
)
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Shipment extends UUIDPersistable {

    @Column(length = 64, nullable = false)
    private String trackingNo;
    private LocalDate shippedOn;
    private String addedBy;

    @JsonIgnore
    @OneToMany(mappedBy = "shipment")
    private List<Voucher> vouchers = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "shipment")
    private List<Comment> comments = new ArrayList<>();

    public int getVouchersCount() {
        return getVouchers().size();
    }

    @Override
    public String toString() {
        return getId();
    }

}