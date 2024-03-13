package io.festival.distance.domain.base;

import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BaseTimeEntity {
    @CreationTimestamp
    @Column(name = "create_dt")
    @DateTimeFormat(pattern = "yyyy-mm-dd")
    private LocalDateTime createDt;

    @UpdateTimestamp
    @Column(name = "modify_dt")
    @DateTimeFormat(pattern = "yyyy-mm-dd")
    private LocalDateTime modifyDt;
}
