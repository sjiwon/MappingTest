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
    private Integer bid_price;

    @Column(nullable = false)
    private LocalDateTime startDate;

    @Column(nullable = false)
    private LocalDateTime endDate;

    @Column(nullable = false)
    private boolean finishFlag; // DB에 저장되는 값은 {true = 1, false = 0}

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private Users user;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "art_id", nullable = false, unique = true)
    private Art art;

    @OneToMany(mappedBy = "auction")
    private List<AuctionHistory> auctionHistoryList = new ArrayList<>();

    public Auction(Integer bid_price, LocalDateTime startDate,
                   LocalDateTime endDate, boolean finishFlag) {
        this.bid_price = bid_price;
        this.startDate = startDate;
        this.endDate = endDate;
        this.finishFlag = finishFlag;
    }

    @Override
    public String toString() {
        return "Auction{" +
                "id=" + id +
                ", bid_price=" + bid_price +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", finishFlag=" + finishFlag +
                '}';
    }
}
