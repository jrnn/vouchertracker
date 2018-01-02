package vouchertracker.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import org.springframework.data.domain.Persistable;

@MappedSuperclass
public abstract class UUIDPersistable implements Serializable, Persistable<String> {

    @Id
    @Column(length = 32, nullable = false, updatable = false)
    private String id;

    @Column(nullable = false, updatable = false)
    private LocalDate createdOn;

    public UUIDPersistable() {
        this.id = UUID.randomUUID().toString().replace("-", "");
        this.createdOn = LocalDate.now();
    }

    @Override
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LocalDate getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(LocalDate createdOn) {
        this.createdOn = createdOn;
    }

    @Override
    @JsonIgnore
    public boolean isNew() {
        return false;
    }

}