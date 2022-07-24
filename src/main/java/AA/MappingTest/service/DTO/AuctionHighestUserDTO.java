package AA.MappingTest.service.DTO;

import AA.MappingTest.domain.Users;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AuctionHighestUserDTO {
    private Users user;
    private Integer price;

    public AuctionHighestUserDTO(Users user, Integer price) {
        this.user = user;
        this.price = price;
    }

    @Override
    public String toString() {
        return "\nAuctionHighestUserForm{" +
                "\n\tuser=" + user +
                ", \n\tprice=" + price +
                "\n}";
    }
}
