package vouchertracker.repository;

import java.util.List;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.EntityGraph.EntityGraphType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vouchertracker.domain.entity.Voucher;

@Repository
public interface VoucherRepository extends JpaRepository<Voucher, String> {

    @Query("SELECT v FROM Voucher v WHERE v.id = :id")
    Voucher findByUuid(@Param("id") String id);

    @Query("SELECT v FROM Voucher v JOIN v.customer c WHERE c.id = :id")
    List<Voucher> findByCustomerId(@Param("id")String id);

    @EntityGraph(value = "voucher-entity-graph", type = EntityGraphType.LOAD)
    @Query("SELECT v FROM Voucher v WHERE v.shipment = null")
    List<Voucher> findNonShipped();

    @EntityGraph(value = "voucher-entity-graph", type = EntityGraphType.LOAD)
    @Query("SELECT v FROM Voucher v WHERE v.shipment != null")
    List<Voucher> findShipped();

    @EntityGraph(value = "voucher-entity-graph", type = EntityGraphType.LOAD)
    @Query("SELECT v FROM Voucher v WHERE v.stamped = true AND v.shipment = null")
    List<Voucher> findShippable();

    @Query("SELECT v FROM Voucher v WHERE v.id != :id AND v.voucherId = :voucherId")
    List<Voucher> findIdenticalIds(@Param("id") String id, @Param("voucherId") String voucherId);

}