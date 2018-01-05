package vouchertracker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import vouchertracker.domain.entity.Attachment;

public interface AttachmentRepository extends JpaRepository<Attachment, String> {

    @Query("SELECT a FROM Attachment a WHERE a.id = :id")
    Attachment findByUuid(@Param("id") String id);

}