package AA.MappingTest.domain;

import AA.MappingTest.enums.DealType;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "point_history")
public class PointHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "point_history_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "deal_type", nullable = false)
    private DealType dealType; // JOIN(신규가입 - 포인트내역 instance 생성 = default), CHARGE(충전), REFUND(환불), USE(사용)

    @Column(name = "deal_amount", nullable = false)
    private Integer dealAmount; // default 0

    @Column(name = "point", nullable = false)
    private Integer point; // default 0

    @CreationTimestamp
    @Column(name = "deal_date")
    private LocalDateTime dealDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private Users user;

    @PrePersist
    void init(){
        this.dealType = (this.dealType == null ? DealType.JOIN : this.dealType);
        this.dealAmount = (this.dealAmount == null ? 0 : this.dealAmount);
        this.point = (this.point == null ? 0 : this.point);
    }

    public PointHistory(Users user) { // 신규가입시 Instance Generate용도
        this.user = user;
    }

    // 테스트용 생성자
    public PointHistory(Users user, Integer point) {
        this.user = user;
        this.point = point;
    }

    public PointHistory(DealType dealType, Integer dealAmount, Integer point, Users user) {
        this.dealType = dealType;
        this.dealAmount = dealAmount;
        this.point = point;
        this.user = user;
    }

    @Override
    public String toString() {
        return "\nPointHistory{" +
                "\n\tid=" + id +
                ", \n\tdealType=" + dealType +
                ", \n\tdealAmount=" + dealAmount +
                ", \n\tpoint=" + point +
                ", \n\tdealDate=" + dealDate +
                ", \n\tuser=" + user +
                "\n}";
    }
}
