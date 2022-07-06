package AA.MappingTest.service;

import AA.MappingTest.domain.Art;
import AA.MappingTest.domain.ArtHashtag;
import AA.MappingTest.domain.Hashtag;
import AA.MappingTest.domain.Users;
import AA.MappingTest.enums.SaleType;
import AA.MappingTest.repository.ArtHashtagRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@SpringBootTest
@Transactional
class ArtHashtagServiceTest {

    @Autowired
    ArtHashtagRepository artHashtagRepository;

    @Autowired
    ArtHashtagService artHashtagService;

    @Autowired
    UserService userService;

    @Autowired
    HashtagService hashtagService;

    @Autowired
    ArtService artService;

    @Autowired
    EntityManager em;

    @BeforeEach
    void init() {
        String[] tables = {
                "users",
                "art",
                "hashtag",
                "art_hashtag",
                "auction",
                "auction_history",
                "like_art",
//                "like_artist",
                "point_history",
                "purchase_history"
        };

        for (String table : tables) {
            String query = "alter table " + table + " auto_increment = 1";
            em.createNativeQuery(query).executeUpdate();
        }
    }

    // [art_id]로 해시태그 목록 조회
    @Test
    @DisplayName("[art_id]로 art의 해시태그 목록 조회")
    void test(){
        // given
        Users user = new Users(
                "서지원",
                "Seo Ji Won",
                "sjiwon",
                "1234",
                "경기대학교",
                "01012345678",
                "경기도 안양",
                LocalDate.of(2000, 1, 18)
        );
        userService.joinUser(user);

        Hashtag hashtagA = new Hashtag("Hashtag - A");
        Hashtag hashtagB = new Hashtag("Hashtag - B");
        Hashtag hashtagC = new Hashtag("Hashtag - C");
        Hashtag hashtagD = new Hashtag("Hashtag - D");
        Hashtag hashtagE = new Hashtag("Hashtag - E");
        Hashtag hashtagF = new Hashtag("Hashtag - F");
        Hashtag hashtagG = new Hashtag("Hashtag - G");
        Hashtag hashtagH = new Hashtag("Hashtag - H");
        Hashtag hashtagI = new Hashtag("Hashtag - I");
        Hashtag hashtagJ = new Hashtag("Hashtag - J");
        hashtagService.registerHashTag(hashtagA);
        hashtagService.registerHashTag(hashtagB);
        hashtagService.registerHashTag(hashtagC);
        hashtagService.registerHashTag(hashtagD);
        hashtagService.registerHashTag(hashtagE);
        hashtagService.registerHashTag(hashtagF);
        hashtagService.registerHashTag(hashtagG);
        hashtagService.registerHashTag(hashtagH);
        hashtagService.registerHashTag(hashtagI);
        hashtagService.registerHashTag(hashtagJ);

        String storageNameArtA = UUID.randomUUID().toString().replaceAll("-", "");
        log.info("\nartA의 서버 저장 이름 = {}", storageNameArtA);
        Art artA = new Art(
                "너의 모든 순간",
                "이 노래는 성시경이 불렀습니다.",
                10000,
                SaleType.GENERAL,
                storageNameArtA,
                user
        );
        artService.registerArt(artA);

        String storageNameArtB = UUID.randomUUID().toString().replaceAll("-", "");
        log.info("\nartB의 서버 저장 이름 = {}", storageNameArtB);
        Art artB = new Art(
                "희재",
                "이 노래는 성시경이 불렀습니다.",
                20000,
                SaleType.AUCTION,
                storageNameArtB,
                user
        );
        artService.registerArt(artB);

        ArtHashtag artHashtag1 = artHashtagService.addHashtagToArt(artA, hashtagA);
        ArtHashtag artHashtag2 = artHashtagService.addHashtagToArt(artA, hashtagB);
        ArtHashtag artHashtag3 = artHashtagService.addHashtagToArt(artA, hashtagC);
        ArtHashtag artHashtag4 = artHashtagService.addHashtagToArt(artA, hashtagG);
        ArtHashtag artHashtag5 = artHashtagService.addHashtagToArt(artA, hashtagH);
        ArtHashtag artHashtag6 = artHashtagService.addHashtagToArt(artA, hashtagI);
        ArtHashtag artHashtag7 = artHashtagService.addHashtagToArt(artB, hashtagJ);
        ArtHashtag artHashtag8 = artHashtagService.addHashtagToArt(artB, hashtagD);
        ArtHashtag artHashtag9 = artHashtagService.addHashtagToArt(artB, hashtagE);
        ArtHashtag artHashtag10 = artHashtagService.addHashtagToArt(artB, hashtagF);

        // when
        List<ArtHashtag> findHashtagListFromArtA = artHashtagService.getHashtagsFromArtId(artA.getId());
        log.info("\nArtA = {}의 해시태그 목록들 = {}", artA, findHashtagListFromArtA);

        List<ArtHashtag> findHashtagListFromArtB = artHashtagService.getHashtagsFromArtId(artB.getId());
        log.info("\nArtB = {}의 해시태그 목록들 = {}", artB, findHashtagListFromArtB);

        // then
        assertThat(findHashtagListFromArtA.size()).isEqualTo(6);
        assertThat(findHashtagListFromArtA).contains(artHashtag1, artHashtag2, artHashtag3, artHashtag4, artHashtag5, artHashtag6);

        assertThat(findHashtagListFromArtB.size()).isEqualTo(4);
        assertThat(findHashtagListFromArtB).contains(artHashtag7, artHashtag8, artHashtag9, artHashtag10);
    }
}