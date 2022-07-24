package AA.MappingTest.service;

import AA.MappingTest.domain.*;
import AA.MappingTest.domain.enums.SaleType;
import AA.MappingTest.repository.ArtHashtagRepository;
import AA.MappingTest.repository.LikeArtRepository;
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

@Slf4j
@SpringBootTest
@Transactional
class LikeArtRegisterTest {
    @Autowired
    ArtService artService;

    @Autowired
    UserService userService;

    @Autowired
    LikeArtRepository likeArtRepository;

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

    @Test
    @DisplayName("작품찜 테스트")
    void test1(){
        // given
        Users artUser = Users.createUser(
                "서지원",
                "Seo Ji Won",
                "sjiwon",
                "1234",
                "경기대학교",
                "01011112222",
                "경기도 안양",
                LocalDate.of(2000, 1, 18)
        );
        userService.joinUser(artUser);

        Hashtag hashTag1 = Hashtag.createHashtag("HashtagA");
        Hashtag hashTag2 = Hashtag.createHashtag("HashtagB");
        Hashtag hashTag3 = Hashtag.createHashtag("HashtagC");
        hashtagService.registerHashTag(hashTag1);
        hashtagService.registerHashTag(hashTag2);
        hashtagService.registerHashTag(hashTag3);

        String serverFileNameArtA = UUID.randomUUID().toString().replaceAll("-", "");
        log.info("\nArtA가 서버에 저장되는 이름(UUID) = {}", serverFileNameArtA);
        Art artA = Art.createArt(
                artUser,
                "너의 모든 순간",
                "이 작품은 사실 노래이고 성시경이 부른 노래입니다...........",
                15000,
                SaleType.GENERAL,
                "artA-UploadName",
                serverFileNameArtA
        );
        artService.registerArt(artA);
        artService.addHashtag(artA.getId(), hashTag1, hashTag2);

        List<ArtHashtag> hashtagByArtAId = artHashtagRepository.findHashtagByArtId(artA.getId());
        System.out.println("artA의 해시태그 목록들");
        for (ArtHashtag artHashtag : hashtagByArtAId) {
            System.out.println(artHashtag.getHashtag());
        }


        String serverFileNameArtB = UUID.randomUUID().toString().replaceAll("-", "");
        log.info("\nArtB가 서버에 저장되는 이름(UUID) = {}", serverFileNameArtB);
        Art artB = Art.createArt(
                artUser,
                "희재",
                "이 작품은 사실 노래이고 성시경이 부른 노래입니다...........",
                30000,
                SaleType.GENERAL,
                "artB-UploadName",
                serverFileNameArtB
        );
        artService.registerArt(artB);
        artService.addHashtag(artB.getId(), hashTag1, hashTag3);

        List<ArtHashtag> hashtagByArtBId = artHashtagRepository.findHashtagByArtId(artB.getId());
        for (ArtHashtag artHashtag : hashtagByArtBId) {
            System.out.println(artHashtag.getHashtag());
        }


        String serverFileNameArtC = UUID.randomUUID().toString().replaceAll("-", "");
        log.info("\nArtC가 서버에 저장되는 이름(UUID) = {}", serverFileNameArtC);
        Art artC = Art.createArt(
                artUser,
                "안녕 나의 사랑",
                "이 작품은 사실 노래이고 성시경이 부른 노래입니다...........",
                18000,
                SaleType.GENERAL,
                "artC-UploadName",
                serverFileNameArtC
        );
        artService.registerArt(artC);
        artService.addHashtag(artC.getId(), hashTag2, hashTag3);

        List<ArtHashtag> hashtagByArtCId = artHashtagRepository.findHashtagByArtId(artC.getId());
        for (ArtHashtag artHashtag : hashtagByArtCId) {
            System.out.println(artHashtag.getHashtag());
        }


        // when
        Users userA = Users.createUser(
                "userA",
                "userA",
                "userA",
                "userA",
                "경기대학교",
                "01033334444",
                "경기도 안양",
                LocalDate.of(1980, 1, 18)
        );
        userService.joinUser(userA);

        Users userB = Users.createUser(
                "userB",
                "userB",
                "userB",
                "userB",
                "경기대학교",
                "01055556666",
                "경기도 안양",
                LocalDate.of(1999, 1, 18)
        );
        userService.joinUser(userB);

        // 작품 찜
        userService.addLikeArt(userA.getId(), artA, artB);
        userService.addLikeArt(userB.getId(), artB, artC);

        // then
        System.out.println("=== userA의 작품찜 목록들 ===");
        List<LikeArt> artListByUserAId = likeArtRepository.findArtListByUserId(userA.getId());
        for (LikeArt likeArt : artListByUserAId) {
            System.out.println(likeArt.getArt());
        }

        System.out.println("=== userB의 작품찜 목록들 ===");
        List<LikeArt> artListByUserBId = likeArtRepository.findArtListByUserId(userB.getId());
        for (LikeArt likeArt : artListByUserBId) {
            System.out.println(likeArt.getArt());
        }

        // userA의 찜에서 artB 삭제
        userService.removeLikeArt(userA.getId(), artB);
        System.out.println("=== userA의 작품찜 목록들 ===");
        List<LikeArt> artListByUserAAId = likeArtRepository.findArtListByUserId(userA.getId());
        for (LikeArt likeArt : artListByUserAAId) {
            System.out.println(likeArt.getArt());
        }
    }
}
