package AA.MappingTest.domain;

import AA.MappingTest.enums.PurchaseCategory;
import lombok.*;

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

    private Integer price;

    private LocalDateTime purchaseDate;

    @Enumerated(EnumType.STRING)
    private PurchaseCategory purchaseCategory; // GENERAL(일반 판매를 통한 구매), AUCTION(경매를 통한 낙찰)

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private Users user;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "art_id")
    private Art art;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "auction_id")
    private Auction auction;

    public PurchaseHistory(Integer price, LocalDateTime purchaseDate,
                           PurchaseCategory purchaseCategory) {
        this.price = price;
        this.purchaseDate = purchaseDate;
        this.purchaseCategory = purchaseCategory;
    }
}
