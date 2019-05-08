package vouchertracker.repository;

import java.util.List;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.EntityGraph.EntityGraphType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vouchertracker.domain.entity.Shipment;

@Repository
public interface ShipmentRepository extends JpaRepository<Shipment, String> {

    @Override
    @EntityGraph(value = "shipment-entity-graph", type = EntityGraphType.LOAD)
    List<Shipment> findAll();

    @Query("SELECT s FROM Shipment s WHERE s.id = :id")
    Shipment findByUuid(@Param("id") String id);

}