package AA.MappingTest.service.DTO;

import AA.MappingTest.domain.Users;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AuctionBidDTO {
    private Users user;
    private Integer bidPrice;

    public AuctionBidDTO(Users user, Integer bidPrice) {
        this.user = user;
        this.bidPrice = bidPrice;
    }

    @Override
    public String toString() {
        return "\nAuctionBidForm{" +
                "\n\tuser=" + user +
                ", \n\tbidPrice=" + bidPrice +
                "\n}";
    }
}
