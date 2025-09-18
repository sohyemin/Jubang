package HelloWorld.Jubang.domain.user;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class) // BaseTieEntity 클래스에 Auditing 기능 포함 시키기
public class BaseTimeEntity {

    // Entity가 생성되어 저장될 때 시간이 자동으로 저장
    @CreatedDate
    @Column(updatable = false) // 최초 등록 후 수정 금지
    private LocalDateTime createdAt;

    // 조회한 Entity의 값을 변경할 때 시간이 자동으로 저장
    @LastModifiedDate
    private LocalDateTime modifiedAt;

}
