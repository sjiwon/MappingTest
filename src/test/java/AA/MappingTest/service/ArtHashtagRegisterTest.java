package AA.MappingTest.service;

import AA.MappingTest.domain.Art;
import AA.MappingTest.domain.ArtHashtag;
import AA.MappingTest.domain.Hashtag;
import AA.MappingTest.domain.Users;
import AA.MappingTest.domain.enums.SaleType;
import AA.MappingTest.repository.ArtHashtagRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@Transactional
@SpringBootTest
class ArtHashtagRegisterTest {
    @Autowired
    ArtService artService;

    @Autowired
    UserService userService;

    @Autowired
    HashtagService hashtagService;

    @Autowired
    ArtHashtagRepository artHashtagRepository;

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
                "point_history",
                "purchase_history"
        };

        for (String table : tables) {
            String query = "alter table " + table + " auto_increment = 1";
            em.createNativeQuery(query).executeUpdate();
        }
    }

    // 1. 해시태그 등록
    @Test
    @DisplayName("해시태그 등록 테스트")
    void test1(){
        // given
        Users user = Users.createUser(
                "서지원",
                "Seo Ji Won",
                "sjiwon",
                "1234",
                "경기대학교",
                "01011112222",
                "경기도 안양",
                LocalDate.of(2000, 1, 18)
        );
        userService.joinUser(user);

        Hashtag hashTag1 = Hashtag.createHashtag("HashtagA");
        Hashtag hashTag2 = Hashtag.createHashtag("HashtagB");
        Hashtag hashTag3 = Hashtag.createHashtag("HashtagC");
        Hashtag registerHashTag1 = hashtagService.registerHashTag(hashTag1);
        Hashtag registerHashTag2 = hashtagService.registerHashTag(hashTag2);
        Hashtag registerHashTag3 = hashtagService.registerHashTag(hashTag3);

        // when
        String serverFileNameArtA = UUID.randomUUID().toString().replaceAll("-", "");
        log.info("\nArtA가 서버에 저장되는 이름(UUID) = {}", serverFileNameArtA);
        Art art = Art.createArt(
                user,
                "너의 모든 순간",
                "이 작품은 사실 노래이고 성시경이 부른 노래입니다...........",
                15000,
                SaleType.GENERAL,
                "artA-UploadName",
                serverFileNameArtA
        );
        artService.registerArt(art);
        artService.addHashtag(art.getId(), hashTag1, hashTag2, hashTag3); // 해시태그 3개 등록

        // 등록하고 ArtHashtag 조회
        List<ArtHashtag> artHashtagListA = artHashtagRepository.findHashtagByArtId(art.getId());

        for (ArtHashtag artHashtag : artHashtagListA) {
            assertThat(artHashtag.getHashtag()).isIn(registerHashTag1, registerHashTag2, registerHashTag3);
            System.out.println(artHashtag.getHashtag());
        }

        assertThat(artHashtagListA.size()).isEqualTo(3);

        // 삭제하고 ArtHashtag 조회
        artService.removeHashtag(art.getId(), hashTag2);
        List<ArtHashtag> artHashtagListB = artHashtagRepository.findHashtagByArtId(art.getId());

        for (ArtHashtag artHashtag : artHashtagListB) {
            assertThat(artHashtag.getHashtag()).isIn(registerHashTag1, registerHashTag2, registerHashTag3);
            System.out.println(artHashtag.getHashtag());
        }

        assertThat(artHashtagListB.size()).isEqualTo(2);
    }
}
