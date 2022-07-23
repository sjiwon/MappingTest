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

    @Column(name = "name", nullable = false, length = 30)
    private String name;

    @Column(name = "nickname", nullable = false, length = 30)
    private String nickname;

    @Column(name = "login_id", nullable = false, unique = true, length = 30)
    private String loginId;

    @Column(name = "login_password", nullable = false, length = 100)
    private String loginPassword;

    @Column(name = "school_name", nullable = false, length = 35)
    private String schoolName;

    @Column(name = "phone_number", nullable = false, unique = true, length = 11)
    private String phoneNumber;

    @Column(name = "address", nullable = false, length = 200)
    private String address;

    @Column(name = "birth", nullable = false)
    private LocalDate birth;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL) // auto persist
    @OrderBy(value = "registerDate desc")
    private List<Art> artList = new ArrayList<>();

    // 생성 메소드 //
    public static Users createUser(String name, String nickname, String loginId, String loginPassword,
                 String schoolName, String phoneNumber, String address, LocalDate birth) {
        Users user = new Users();
        user.name = name;
        user.nickname = nickname;
        user.loginId = loginId;
        user.loginPassword = loginPassword;
        user.schoolName = schoolName;
        user.phoneNumber = phoneNumber;
        user.address = address;
        user.birth = birth;
        return user;
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