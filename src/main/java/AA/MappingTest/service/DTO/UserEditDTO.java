package AA.MappingTest.service.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserEditDTO {
    private String nickname;
    private String phoneNumber;
    private String address;

    @Override
    public String toString() {
        return "UserEditForm{" +
                "nickname='" + nickname + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
