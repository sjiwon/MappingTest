package AA.MappingTest.repository;

import AA.MappingTest.domain.LikeArt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LikeArtRepository extends JpaRepository<LikeArt, Long> {

    // [user_id]의 찜한 artList 조회
    @Query("select la from LikeArt la join fetch la.art join fetch la.user where la.user.id = :userId")
    List<LikeArt> findArtListByUserId(@Param("userId") Long userId);

    // [user_id, art_id] instance 삭제
    @Modifying
    @Query("delete from LikeArt la where la.user.id = :userId and la.art.id = :artId")
    int deleteLikeArtByUserIdAndArtId(@Param("userId") Long userId, @Param("artId") Long artId);
}
