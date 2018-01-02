package vouchertracker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vouchertracker.domain.entity.Voucher;

public interface VoucherRepository extends JpaRepository<Voucher, String> {
}