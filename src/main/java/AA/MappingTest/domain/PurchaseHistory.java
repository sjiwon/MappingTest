package AA.MappingTest.domain;

import AA.MappingTest.enums.PurchaseCategory;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "purchase_history")
public class PurchaseHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "purchase_history_id")
    private Long id;

    @Column(nullable = false)
    private Integer price;

    @CreationTimestamp
    private LocalDateTime purchaseDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PurchaseCategory purchaseCategory; // GENERAL(일반 판매를 통한 구매), AUCTION(경매를 통한 낙찰)

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private Users user;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "art_id", nullable = false, unique = true)
    private Art art;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "auction_id", unique = true)
    private Auction auction;

    public PurchaseHistory(Integer price, LocalDateTime purchaseDate,
                           PurchaseCategory purchaseCategory) {
        this.price = price;
        this.purchaseDate = purchaseDate;
        this.purchaseCategory = purchaseCategory;
    }
}
