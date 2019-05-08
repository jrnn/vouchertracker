package vouchertracker.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.time.LocalDateTime;
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
        name = "customer-entity-graph",
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
                        @NamedAttributeNode("shipment")
                    }
            )
        }
)
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Customer extends UUIDPersistable {

    private String firstName;
    private String lastName;

    @Column(length = 32, nullable = false)
    private String passport;

    @JsonIgnore
    @OneToMany(mappedBy = "customer")
    private List<Voucher> vouchers = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "customer")
    private List<Comment> comments = new ArrayList<>();

    private String lastEditedBy;
    private LocalDateTime lastEditedOn;

    public int getVouchersCount() {
        return getVouchers().size();
    }

    @Override
    public String toString() {
        return getId();
    }

}