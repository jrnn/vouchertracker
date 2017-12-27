package vouchertracker.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import org.springframework.data.domain.Persistable;

@MappedSuperclass
public abstract class UUIDPersistable implements Serializable, Persistable<String> {

    @Id
    @Column(updatable = false, nullable = false, length = 32)
    private String id;

    public UUIDPersistable() {
        this.id = UUID.randomUUID().toString().replace("-", "");
    }

    @Override
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    @JsonIgnore
    public boolean isNew() {
        return false;
    }

}