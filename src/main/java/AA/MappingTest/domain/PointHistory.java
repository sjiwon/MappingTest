package AA.MappingTest.domain;

import AA.MappingTest.enums.DealType;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "point_history")
public class PointHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "point_history_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    private DealType dealType; // JOIN(신규가입 - 포인트내역 instance 생성 = default), CHARGE(충전), REFUND(환불), USE(사용)

    private Integer dealAmount; // default 0

    private Integer point; // default 0

    @CreationTimestamp
    private LocalDateTime dealDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private Users user;

    @PrePersist
    void prePersist(){
        this.dealType = (this.dealType == null ? DealType.JOIN : this.dealType);
        this.dealAmount = (this.dealAmount == null ? 0 : this.dealAmount);
        this.point = (this.point == null ? 0 : this.point);
    }

    public PointHistory(Users user) {
        this.user = user;
    }

    public PointHistory(DealType dealType, Integer dealAmount, Integer point, Users user) {
        this.dealType = dealType;
        this.dealAmount = dealAmount;
        this.point = point;
        this.user = user;
    }

    public PointHistory(DealType dealType, Integer dealAmount,
                        Integer point, LocalDateTime dealDate) {
        this.dealType = dealType;
        this.dealAmount = dealAmount;
        this.point = point;
        this.dealDate = dealDate;
    }

    @Override
    public String toString() {
        return "PointHistory{" +
                "id=" + id +
                ", dealType=" + dealType +
                ", dealAmount=" + dealAmount +
                ", point=" + point +
                ", dealDate=" + dealDate +
                '}';
    }
}
