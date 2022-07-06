package AA.MappingTest.service.DTO;

import AA.MappingTest.domain.Users;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AuctionBidForm {
    private Users user;
    private Integer bidPrice;

    @Override
    public String toString() {
        return "\nAuctionBidForm{" +
                "\n\tuser=" + user +
                ", \n\tbidPrice=" + bidPrice +
                "\n}";
    }
}
