package AA.MappingTest.domain;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "auction_history")
public class AuctionHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private int bidPrice;

    @CreationTimestamp
    @Column(nullable = false)
    private LocalDateTime bidDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private Users user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "art_id")
    private Art art;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "auction_id")
    private Auction auction;

    public void addAuctionHistoryToUser(Users user){
        this.user = user;
        user.getAuctionHistoryList().add(this);
    }

    public void addAuctionHistoryToArt(Art art){
        this.art = art;
        art.getAuctionHistoryList().add(this);
    }

    public void addAuctionHistoryToAuction(Auction auction){
        this.auction = auction;
        auction.getAuctionHistoryList().add(this);
    }

    public AuctionHistory(int bidPrice) {
        this.bidPrice = bidPrice;
    }

    @Override
    public String toString() {
        return "\nAuctionHistory{" +
                "\n\tid=" + id +
                ", \n\tbidPrice=" + bidPrice +
                ", \n\tbidDate=" + bidDate +
                ", \n\tuser=" + user +
                "\n}";
    }
}
