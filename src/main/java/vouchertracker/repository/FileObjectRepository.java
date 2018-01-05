package vouchertracker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import vouchertracker.domain.entity.FileObject;

public interface FileObjectRepository extends JpaRepository<FileObject, String> {

    @Query("SELECT f FROM FileObject f WHERE f.id = :id")
    FileObject findByUuid(@Param("id") String id);

}