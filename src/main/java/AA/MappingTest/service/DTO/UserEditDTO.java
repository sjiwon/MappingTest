package AA.MappingTest.service.DTO;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserEditDTO {
    private String nickname;
    private String phoneNumber;
    private String address;

    public UserEditDTO(String nickname, String phoneNumber, String address) {
        this.nickname = nickname;
        this.phoneNumber = phoneNumber;
        this.address = address;
    }

    @Override
    public String toString() {
        return "UserEditForm{" +
                "nickname='" + nickname + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
