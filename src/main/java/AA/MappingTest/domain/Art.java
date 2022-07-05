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

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private Integer initPrice;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SaleType saleType; // GENERAL(일반 판매), AUCTION(경매를 통한 판매)

    @CreationTimestamp
    private LocalDateTime registerDate;

    @Column(nullable = false, unique = true)
    private String storageName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private Users user;

    @OneToMany(mappedBy = "art")
    private List<ArtHashtag> artHashtagList = new ArrayList<>();

    @OneToMany(mappedBy = "art")
    private List<AuctionHistory> auctionHistoryList = new ArrayList<>();

    @OneToOne(mappedBy = "art")
    private PurchaseHistory purchaseHistory;

    public Art(String name, String description, Integer initPrice,
               SaleType saleType, String storageName, Users user) {
        this.name = name;
        this.description = description;
        this.initPrice = initPrice;
        this.saleType = saleType;
        this.storageName = storageName;
        this.user = user;
    }

    public Art(String name, String description, Integer initPrice, SaleType saleType,
               LocalDateTime registerDate, String storageName, Users user) {
        this.name = name;
        this.description = description;
        this.initPrice = initPrice;
        this.saleType = saleType;
        this.registerDate = registerDate;
        this.storageName = storageName;
        this.user = user;
    }

    @Override
    public String toString() {
        return "\nArt{" +
                "\n\tname='" + name + '\'' +
                ", \n\tdescription='" + description + '\'' +
                ", \n\tinitPrice=" + initPrice +
                ", \n\tsaleType=" + saleType +
                ", \n\tregisterDate=" + registerDate +
                ", \n\tstorageName='" + storageName + '\'' +
                ", \n\tuser=" + user +
                "\n}";
    }
}
