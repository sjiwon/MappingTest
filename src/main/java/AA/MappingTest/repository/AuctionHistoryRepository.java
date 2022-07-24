package AA.MappingTest.repository;

import AA.MappingTest.domain.AuctionHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AuctionHistoryRepository extends JpaRepository<AuctionHistory, Long> {

    // [user_id]의 경매 참여 내역 조회
    @Query("select ah from AuctionHistory ah join fetch ah.user where ah.user.id = :userId")
    List<AuctionHistory> findAuctionHistoriesByUserId(@Param("userId") Long userId);

    // [art_id]의 경매 내역 조회
    @Query("select ah from AuctionHistory ah join fetch ah.art where ah.art.id = :artId")
    List<AuctionHistory> findAuctionHistoriesByArtId(@Param("artId") Long artId);

    // [auction_id]의 bid 내역 조회
    @Query("select ah from AuctionHistory ah join fetch ah.auction where ah.auction.id = :auctionId")
    List<AuctionHistory> findAuctionHistoriesByAuctionId(@Param("auctionId") Long auctionId);
}
