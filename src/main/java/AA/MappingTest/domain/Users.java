package AA.MappingTest.domain;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter 
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    private String name;

    private String nickname;

    private String loginId;

    private String loginPassword;

    private String schoolName;

    private String phoneNumber;

    private String address;

    private LocalDate birth;

    @OneToMany(mappedBy = "user")
    private List<Art> artList = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Auction> auctionList = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<AuctionHistory> auctionHistoryList = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<PurchaseHistory> purchaseHistoryList = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<LikeArt> likeArtList = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<PointHistory> pointHistoryList = new ArrayList<>();

    public Users(String name, String nickname, String loginId, String loginPassword, String schoolName,
                 String phoneNumber, String address, LocalDate birth) {
        this.name = name;
        this.nickname = nickname;
        this.loginId = loginId;
        this.loginPassword = loginPassword;
        this.schoolName = schoolName;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.birth = birth;
    }

    @Override
    public String toString() {
        return "\nUsers{" +
                "\n\tid=" + id +
                ", \n\tname='" + name + '\'' +
                ", \n\tnickname='" + nickname + '\'' +
                ", \n\tloginId='" + loginId + '\'' +
                ", \n\tloginPassword='" + loginPassword + '\'' +
                ", \n\tschoolName='" + schoolName + '\'' +
                ", \n\tphoneNumber='" + phoneNumber + '\'' +
                ", \n\taddress='" + address + '\'' +
                ", \n\tbirth=" + birth +
                "\n}";
    }
}
