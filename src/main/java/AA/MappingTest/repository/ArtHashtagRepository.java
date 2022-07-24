package AA.MappingTest.repository;

import AA.MappingTest.domain.ArtHashtag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArtHashtagRepository extends JpaRepository<ArtHashtag, Long> {

    // [art_id]의 해시태그 목록 조회
    @Query("select ah from ArtHashtag ah join fetch ah.hashtag join fetch ah.art where ah.art.id = :artId")
    List<ArtHashtag> findHashtagByArtId(@Param("artId") Long artId);

    // [art_id, hashtag_id] instance 삭제
    @Modifying
    @Query("delete from ArtHashtag ah where ah.art.id = :artId and ah.hashtag.id = :hashtagId")
    int deleteLikeArtByUserIdAndArtId(@Param("artId") Long artId, @Param("hashtagId") Long hashtagId);
}
