package com.team6.todomateclone.common;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import java.time.LocalDateTime;

@MappedSuperclass
@Getter
@EntityListeners(AuditingEntityListener.class)
public class TimeStamped {

    @CreatedDate
    @Column(name = "CREATED_AT", nullable = false)
    private LocalDateTime createdAt;

     /**
     @PerPersist : 해당 엔티티 저장하기 전 실행
      - onPrePersist() : 생성일자 내 나노초 제거함
        사용 전 : 2022-12-25T01:23:53.73487
        사용 후 : 2022-12-25T01:23:53
      */
    @PrePersist
    private void onPrePersist(){
        this.createdAt = LocalDateTime.now().withNano(0); //나노초 9자 제거
    }

}
