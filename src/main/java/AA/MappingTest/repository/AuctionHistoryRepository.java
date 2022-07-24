package AA.MappingTest.repository;

import AA.MappingTest.domain.AuctionHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AuctionHistoryRepository extends JpaRepository<AuctionHistory, Long> {

    // [user_id]의 경매 참여 내역 조회
    @Query("select ah from AuctionHistory ah join fetch ah.user where ah.user.id = :id")
    List<AuctionHistory> findAuctionHistoriesByUserId(@Param("id") Long id);

    // [art_id]의 경매 내역 조회
    @Query("select ah from AuctionHistory ah join fetch ah.art where ah.art.id = :id")
    List<AuctionHistory> findAuctionHistoriesByArtId(@Param("id") Long id);

    // [auction_id]의 bid 내역 조회
    @Query("select ah from AuctionHistory ah join fetch ah.auction where ah.auction.id = :id")
    List<AuctionHistory> findAuctionHistoriesByAuctionId(@Param("id") Long id);
}
