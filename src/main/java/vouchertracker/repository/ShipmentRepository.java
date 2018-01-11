package vouchertracker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vouchertracker.domain.entity.Shipment;

public interface ShipmentRepository extends JpaRepository<Shipment, String> {
}