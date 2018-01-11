package vouchertracker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import vouchertracker.domain.entity.Shipment;

public interface ShipmentRepository extends JpaRepository<Shipment, String> {

    @Query("SELECT s FROM Shipment s WHERE s.id = :id")
    Shipment findByUuid(@Param("id") String id);

}