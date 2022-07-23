package AA.MappingTest.domain;

import AA.MappingTest.domain.enums.SaleType;
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

    @Column(name = "name", nullable = false, unique = true, length = 100)
    private String name;

    @Column(name = "description", nullable = false, length = 160)
    private String description;

    @Column(name = "init_price", nullable = false)
    private Integer initPrice;

    @Enumerated(EnumType.STRING)
    @Column(name = "sale_type", nullable = false, length = 8)
    private SaleType saleType; // GENERAL(일반 판매), AUCTION(경매를 통한 판매)

    @CreationTimestamp
    @Column(name = "register_date")
    private LocalDateTime registerDate;

    @Column(name = "upload_name", nullable = false, length = 100)
    private String uploadName;

    @Column(name = "storage_name", nullable = false, unique = true, length = 40)
    private String storageName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, updatable = false)
    private Users user;

    @ManyToMany
    @JoinTable(
            name = "art_hashtag",
            joinColumns = @JoinColumn(name = "art_id"),
            inverseJoinColumns = @JoinColumn(name = "hashtag_id")
    )
    private List<Hashtag> artHashtagList = new ArrayList<>(); // N:M

    public void setUser(Users user){
        this.user = user;
        user.getArtList().add(this);
    }

    // 생성 메소드 //
    public static Art createArt(Users user, String name, String description, Integer initPrice, SaleType saleType,
               String uploadName, String storageName, Hashtag... hashtags) {
        Art art = new Art();
        art.setUser(user);
        art.name = name;
        art.description = description;
        art.initPrice = initPrice;
        art.saleType = saleType;
        art.uploadName = uploadName;
        art.storageName = storageName;
        for (Hashtag hashtag : hashtags) {
            art.getArtHashtagList().add(hashtag);
        }
        return art;
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
        return "Art{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", initPrice=" + initPrice +
                ", saleType=" + saleType +
                ", registerDate=" + registerDate +
                ", uploadName='" + uploadName + '\'' +
                ", storageName='" + storageName + '\'' +
                ", user=" + user +
                '}';
    }
}
