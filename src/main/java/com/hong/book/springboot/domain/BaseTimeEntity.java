package com.hong.book.springboot.domain;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

// -- JPA Auditing으로 생성시간/수정시간 자동화하기

@Getter
// JPA Entity 클래스들이 BaseTimeEntity을 상속할 경우 필드들(createDate, modifiedDate)도 칼럼으로 인식하게 됩니다.
@MappedSuperclass
// BaseTimeEntity 클래스에 Auditing 기능을 포함시킨다.
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseTimeEntity {

    // Entity가 생성되어 저장될 때, 시간이 자동으로 저장됩니다.
    @CreatedDate
    private LocalDateTime createDate;

    // 조회한 Entity의 값을 변경할 때, 시간이 자동으로 저장됩니다.
    @LastModifiedDate
    private LocalDateTime modifiedDate;
}
