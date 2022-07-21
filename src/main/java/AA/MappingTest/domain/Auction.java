package AA.MappingTest.domain;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "auction")
public class Auction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "auction_id")
    private Long id;

    @Column(name = "bid_price", nullable = false)
    private Integer bidPrice;

    @Column(name = "start_date", nullable = false)
    private LocalDateTime startDate;

    @Column(name = "end_date", nullable = false)
    private LocalDateTime endDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private Users user; // 경매에 참여하는 사람들 (최고가 Bid로 매번 update) & 처음 경매 등록할때는 NULL 허용

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "art_id", nullable = false, unique = true, updatable = false)
    private Art art;

    @OneToMany(mappedBy = "auction")
    @OrderBy(value = "bidDate desc")
    private List<AuctionHistory> auctionHistoryList = new ArrayList<>();

    public Auction(Integer bidPrice, LocalDateTime startDate, LocalDateTime endDate, Users user, Art art) { // 경매 처음 등록할 때 사용
        this.bidPrice = bidPrice;
        this.startDate = startDate;
        this.endDate = endDate;
        this.user = user;
        this.art = art;
    }

    public void applyNewBid(Users user, Integer bidPrice){
        this.user = user;
        this.bidPrice = bidPrice;
    }

    @Override
    public String toString() {
        return "\nAuction{" +
                "\n\tid=" + id +
                ", \n\tbidPrice=" + bidPrice +
                ", \n\tstartDate=" + startDate +
                ", \n\tendDate=" + endDate +
                ", \n\tuser=" + user +
                "\n}";
    }
}
