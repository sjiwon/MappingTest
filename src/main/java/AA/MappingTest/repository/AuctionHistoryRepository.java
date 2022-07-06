package AA.MappingTest.repository;

import AA.MappingTest.domain.AuctionHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AuctionHistoryRepository extends JpaRepository<AuctionHistory, Long> {

    // [art_id]의 경매 내역 조회
    @Query("select ah from AuctionHistory ah where ah.art.id = :id")
    List<AuctionHistory> findAuctionHistoriesByArtId(@Param("id") Long id);
}
