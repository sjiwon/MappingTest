package AA.MappingTest.repository;

import AA.MappingTest.domain.Auction;
import AA.MappingTest.service.DTO.AuctionHighestUserDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AuctionRepository extends JpaRepository<Auction, Long> {

    // 현재 경매 최고가 User & Bid 금액 조회
    @Query("select new AA.MappingTest.service.DTO.AuctionHighestUserDTO(a.user, a.bidPrice) from Auction a where a.id = :auctionId")
    AuctionHighestUserDTO findHighestUserByAuctionId(@Param("auctionId") Long auctionId);

    // [auction_id]에 해당하는 경매의 '작품' 조회
    @Query("select a from Auction a join fetch a.art where a.id = :auctionId")
    Auction findArtByAuctionId(@Param("auctionId") Long auctionId);
}
