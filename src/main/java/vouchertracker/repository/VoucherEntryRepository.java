package vouchertracker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vouchertracker.domain.VoucherEntry;

public interface VoucherEntryRepository extends JpaRepository<VoucherEntry, String> {
}