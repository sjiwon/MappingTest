package AA.MappingTest.domain;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "users")
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String nickname;

    @Column(nullable = false, unique = true)
    private String loginId;

    @Column(nullable = false)
    private String loginPassword;

    @Column(nullable = false)
    private String schoolName;

    @Column(nullable = false, unique = true)
    private String phoneNumber;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private LocalDate birth;

    @OneToMany(mappedBy = "user")
    @OrderBy(value = "registerDate desc")
    private List<Art> artList = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    @OrderBy(value = "bidDate desc")
    private List<AuctionHistory> auctionHistoryList = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    @OrderBy(value = "purchaseDate desc")
    private List<PurchaseHistory> purchaseHistoryList = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<LikeArt> likeArtList = new ArrayList<>();

    public Users(String name, String nickname, String loginId, String loginPassword,
                 String schoolName, String phoneNumber, String address, LocalDate birth) {
        this.name = name;
        this.nickname = nickname;
        this.loginId = loginId;
        this.loginPassword = loginPassword;
        this.schoolName = schoolName;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.birth = birth;
    }

    public void changeNickname(String nickname){
        this.nickname = nickname;
    }

    public void changePhoneNumber(String phoneNumber){
        this.phoneNumber = phoneNumber;
    }

    public void changeAddress(String address){
        this.address = address;
    }

    public void changePassword(String loginPassword){
        this.loginPassword = loginPassword;
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