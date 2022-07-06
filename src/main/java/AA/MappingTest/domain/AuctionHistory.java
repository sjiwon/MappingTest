package AA.MappingTest.domain;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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
    @JoinColumn(name = "user_id", nullable = false)
    private Users user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "art_id", nullable = false)
    private Art art;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "auction_id", nullable = false)
    private Auction auction;

    public AuctionHistory(int bidPrice, Users user, Art art, Auction auction) {
        this.bidPrice = bidPrice;
        this.user = user;
        this.art = art;
        this.auction = auction;
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
