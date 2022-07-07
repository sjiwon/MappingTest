package AA.MappingTest.domain;

import AA.MappingTest.enums.SaleType;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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
    @OrderBy(value = "bidDate desc")
    private List<AuctionHistory> auctionHistoryList = new ArrayList<>();

    public void addUser(Users user){
        this.user = user;
        user.getArtList().add(this);
    }

    public Art(String name, String description, Integer initPrice, SaleType saleType, String storageName) {
        this.name = name;
        this.description = description;
        this.initPrice = initPrice;
        this.saleType = saleType;
        this.registerDate = registerDate;
        this.storageName = storageName;
    }

    // 작품 이름 수정
    public void changeArtName(String name){
        this.name = name;
    }

    // 작품 설명 수정
    public void changeDescription(String description){
        this.description = description;
    }

    @Override
    public String toString() {
        return "\nArt{" +
                "\n\tid=" + id +
                ", \n\tname='" + name + '\'' +
                ", \n\tdescription='" + description + '\'' +
                ", \n\tinitPrice=" + initPrice +
                ", \n\tsaleType=" + saleType +
                ", \n\tregisterDate=" + registerDate +
                ", \n\tstorageName='" + storageName + '\'' +
                ", \n\tuser=" + user +
                "\n}";
    }
}
