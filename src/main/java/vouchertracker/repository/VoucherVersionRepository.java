package vouchertracker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vouchertracker.domain.VoucherVersion;

public interface VoucherVersionRepository extends JpaRepository<VoucherVersion, Long> {
}