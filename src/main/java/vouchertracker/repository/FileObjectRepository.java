package vouchertracker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vouchertracker.domain.entity.FileObject;

public interface FileObjectRepository extends JpaRepository<FileObject, Long> {
}