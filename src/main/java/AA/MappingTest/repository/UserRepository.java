package AA.MappingTest.repository;

import AA.MappingTest.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository extends JpaRepository<Users, Long> {

    // loginId로 password 찾기
    @Query(value = "select u.loginPassword from Users u where u.loginId = :loginId")
    String findPasswordByLoginId(@Param("loginId") String loginId);

    // [user_id]가 소유한 작품 조회
    @Query(value = "select a from Art a join fetch a.user where a.user.id = :id")
    List<Art> findArtCollections(@Param("id") Long id);

    // [user_id]가 참여한 경매내역 조회
    @Query(value = "select ah from AuctionHistory ah join fetch ah.user where ah.user.id = :id")
    List<AuctionHistory> findParticipatedAuction(@Param("id") Long id);

    // [user_id]가 작품 구매한 내역 조회
    @Query(value = "select ph from PurchaseHistory ph join fetch ph.user where ph.user.id = :id")
    List<PurchaseHistory> findPurchasedHistory(@Param("id") Long id);

    // [user_id]가 찜한 작품 리스트 조회
    @Query(value = "select la from LikeArt la join fetch la.user where la.user.id = :id")
    List<LikeArt> findLikedArt(@Param("id") Long id);

    // [user_id]가 찜한 작가 리스트 조회
    @Query(value = "select la from LikeArtist la join fetch la.user join fetch la.artist where la.user.id = :id")
    List<LikeArtist> findLikedArtist(@Param("id") Long id);
}
