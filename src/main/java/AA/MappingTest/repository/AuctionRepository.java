package AA.MappingTest.repository;

import AA.MappingTest.domain.Art;
import AA.MappingTest.domain.Auction;
import AA.MappingTest.service.DTO.AuctionHighestUserForm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AuctionRepository extends JpaRepository<Auction, Long> {

    // 현재 경매 최고가 User & Bid 금액 조회
    @Query("select new AA.MappingTest.service.DTO.AuctionHighestUserForm(a.user, a.bidPrice) from Auction a where a.id=:id")
    AuctionHighestUserForm findHighestUserByAuctionId(@Param("id") Long id);

    // [auction_id]에 해당하는 경매의 '작품' 조회
    @Query("select a from Auction a join fetch a.art where a.id=:id")
    Auction findArtByAuctionId(@Param("id") Long id);
}
