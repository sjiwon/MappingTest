package AA.MappingTest.domain;

import AA.MappingTest.enums.SaleType;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "art")
public class Art {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "art_id")
    private Long id;

    private String name;

    private String decription;

    private Integer initPrice;

    @Enumerated(EnumType.STRING)
    private SaleType saleType; // GENERAL(일반 판매), AUCTION(경매를 통한 판매)

    @CreationTimestamp
    private LocalDateTime registerDate;

    private String artStorageName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private Users user;

    @OneToMany(mappedBy = "art")
    private List<ArtHashtag> artHashtagList = new ArrayList<>();

    @OneToMany(mappedBy = "art")
    private List<AuctionHistory> auctionHistoryList = new ArrayList<>();

    @OneToOne(mappedBy = "art")
    private PurchaseHistory purchaseHistory;

    public Art(String name, String decription, Integer initPrice, SaleType saleType,
               LocalDateTime registerDate, String artStorageName, Users user) {
        this.name = name;
        this.decription = decription;
        this.initPrice = initPrice;
        this.saleType = saleType;
        this.registerDate = registerDate;
        this.artStorageName = artStorageName;
        this.user = user;
    }

    @Override
    public String toString() {
        return "Art{" +
                "name='" + name + '\'' +
                ", decription='" + decription + '\'' +
                ", initPrice=" + initPrice +
                ", saleType=" + saleType +
                ", registerDate=" + registerDate +
                ", artStorageName='" + artStorageName + '\'' +
                ", user=" + user +
                '}';
    }
}
