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

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "init_price", nullable = false)
    private Integer initPrice;

    @Enumerated(EnumType.STRING)
    @Column(name = "sale_type", nullable = false)
    private SaleType saleType; // GENERAL(일반 판매), AUCTION(경매를 통한 판매)

    @CreationTimestamp
    @Column(name = "register_date")
    private LocalDateTime registerDate;

    @Column(name = "upload_name", nullable = false)
    private String uploadName;

    @Column(name = "storage_name", nullable = false, unique = true)
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

    public Art(String name, String description, Integer initPrice, SaleType saleType, String uploadName, String storageName) {
        this.name = name;
        this.description = description;
        this.initPrice = initPrice;
        this.saleType = saleType;
        this.registerDate = registerDate;
        this.uploadName = uploadName;
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
                ", \n\tuploadName=" + uploadName +
                ", \n\tstorageName='" + storageName + '\'' +
                ", \n\tuser=" + user +
                "\n}";
    }
}
