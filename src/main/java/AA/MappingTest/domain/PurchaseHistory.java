package AA.MappingTest.domain;

import AA.MappingTest.domain.enums.PurchaseCategory;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "purchase_history")
public class PurchaseHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "purchase_history_id")
    private Long id;

    @Column(name = "price", nullable = false)
    private Integer price;

    @CreationTimestamp
    @Column(name = "purchase_date")
    private LocalDateTime purchaseDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "purchase_category", nullable = false, length = 8)
    private PurchaseCategory purchaseCategory; // GENERAL(일반 판매를 통한 구매), AUCTION(경매를 통한 낙찰)

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, updatable = false)
    private Users user;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "art_id", nullable = false, unique = true)
    private Art art;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "auction_id", unique = true)
    private Auction auction;

    public void addPurchaseHistoryToUser(Users user){
        this.user = user;
        user.getPurchaseHistoryList().add(this);
    }

    public PurchaseHistory(Integer price, PurchaseCategory purchaseCategory) {
        this.price = price;
        this.purchaseCategory = purchaseCategory;
    }

    @Override
    public String toString() {
        return "\nPurchaseHistory{" +
                "\n\tid=" + id +
                ", \n\tprice=" + price +
                ", \n\tpurchaseDate=" + purchaseDate +
                ", \n\tpurchaseCategory=" + purchaseCategory +
                ", \n\tuser=" + user +
                "\n}";
    }
}

