package AA.MappingTest.repository;

import AA.MappingTest.domain.Art;
import AA.MappingTest.domain.enums.SaleType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ArtRepository extends JpaRepository<Art, Long> {

    // 작품이 경매용인지 일반 판매용인지
    @Query("select a.saleType from Art a where a.id = :artId")
    SaleType isAuctionOrGeneral(@Param("artId") Long artId);

    // 특정 회원의 작품 리스트
    @Query("select a from Art a join fetch a.user where a.user.id = :userId")
    List<Art> findArtListByUserId(@Param("userId") Long userId);
}
