package AA.MappingTest.domain;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "auction")
public class Auction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "auction_id")
    private Long id;

    @Column(nullable = false)
    private Integer bidPrice;

    @Column(nullable = false)
    private LocalDateTime startDate;

    @Column(nullable = false)
    private LocalDateTime endDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private Users user;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "art_id", nullable = false, unique = true)
    private Art art;

    @OneToMany(mappedBy = "auction")
    private List<AuctionHistory> auctionHistoryList = new ArrayList<>();

    public Auction(Integer bidPrice, LocalDateTime startDate, LocalDateTime endDate, Users user, Art art) {
        this.bidPrice = bidPrice;
        this.startDate = startDate;
        this.endDate = endDate;
        this.user = user;
        this.art = art;
    }

    @Override
    public String toString() {
        return "\nAuction{" +
                "\n\tid=" + id +
                ", \n\tbidPrice=" + bidPrice +
                ", \n\tstartDate=" + startDate +
                ", \n\tendDate=" + endDate +
                "\n}";
    }
}
