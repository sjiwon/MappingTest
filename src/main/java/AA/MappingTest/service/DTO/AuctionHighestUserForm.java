package AA.MappingTest.service.DTO;

import AA.MappingTest.domain.Users;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AuctionHighestUserForm {
    private Users user;
    private Integer price;

    @Override
    public String toString() {
        return "\nAuctionHighestUserForm{" +
                "\n\tuser=" + user +
                ", \n\tprice=" + price +
                "\n}";
    }
}
