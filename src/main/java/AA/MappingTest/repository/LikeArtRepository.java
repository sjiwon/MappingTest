package AA.MappingTest.repository;

import AA.MappingTest.domain.LikeArt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LikeArtRepository extends JpaRepository<LikeArt, Long> {

    // [user_id]의 찜한 artList 조회
    @Query("select la from LikeArt la join fetch la.art join fetch la.user where la.user.id=:id")
    List<LikeArt> findArtListByUserId(@Param("id") Long id);
}
