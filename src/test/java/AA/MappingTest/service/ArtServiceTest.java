package AA.MappingTest.service;

import AA.MappingTest.domain.Art;
import AA.MappingTest.domain.Users;
import AA.MappingTest.domain.enums.SaleType;
import AA.MappingTest.service.DTO.ArtEditDTO;
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
@SpringBootTest
@Transactional
class ArtServiceTest {

    @Autowired
    ArtService artService;

    @Autowired
    UserService userService;

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

    // 1. 작품 등록 테스트
    @Test
    @DisplayName("작품 등록")
    void test1() {
        // given
        Users userA = new Users(
                "서지원",
                "서지원123",
                "sjiwon",
                "1234",
                "경기대학교",
                "01012345678",
                "경기도 안양시 동안구 비산3동 평촌대로401번길 44-9 덕원아파트 가동 403호",
                LocalDate.of(2000, 1, 18)
        );

        Users userB = new Users(
                "서지원2",
                "서지원123456",
                "sjiwon1234",
                "12345678",
                "경기대학교2",
                "01099998888",
                "경기도 안양시 동안구 비산3동 평촌대로401번길 44-9 덕원아파트 가동 403호",
                LocalDate.of(2020, 10, 31)
        );

        userService.joinUser(userA);
        userService.joinUser(userB);

        String serverFileNameArtA = UUID.randomUUID().toString().replaceAll("-", "");
        log.info("\nArtA가 서버에 저장되는 이름(UUID) = {}", serverFileNameArtA);
        Art artA = new Art(
                "너의 모든 순간",
                "이 작품은 사실 노래이고 성시경이 부른 노래입니다...........",
                15000,
                SaleType.GENERAL,
                "artA-UploadName",
                serverFileNameArtA
        );
        artA.addUser(userA);

        String serverFileNameArtB = UUID.randomUUID().toString().replaceAll("-", "");
        log.info("\nArtB가 서버에 저장되는 이름(UUID) = {}", serverFileNameArtB);
        Art artB = new Art(
                "희재",
                "이 작품은 사실 노래이고 성시경이 부른 노래입니다...........",
                30000,
                SaleType.GENERAL,
                "artB-UploadName",
                serverFileNameArtB
        );
        artB.addUser(userA);

        String serverFileNameArtC = UUID.randomUUID().toString().replaceAll("-", "");
        log.info("\nArtC가 서버에 저장되는 이름(UUID) = {}", serverFileNameArtC);
        Art artC = new Art(
                "안녕 나의 사랑",
                "이 작품은 사실 노래이고 성시경이 부른 노래입니다...........",
                18000,
                SaleType.GENERAL,
                "artC-UploadName",
                serverFileNameArtC
        );
        artC.addUser(userA);

        String serverFileNameArtD = UUID.randomUUID().toString().replaceAll("-", "");
        log.info("\nArtD가 서버에 저장되는 이름(UUID) = {}", serverFileNameArtD);
        Art artD = new Art(
                "어디에도",
                "이 작품은 사실 노래이고 엠씨더맥스가 부른 노래입니다...........",
                45000,
                SaleType.GENERAL,
                "artD-UploadName",
                serverFileNameArtD
        );
        artD.addUser(userB);

        String serverFileNameArtE = UUID.randomUUID().toString().replaceAll("-", "");
        log.info("\nArtE가 서버에 저장되는 이름(UUID) = {}", serverFileNameArtE);
        Art artE = new Art(
                "Returns",
                "이 작품은 사실 노래이고 엠씨더맥스가 부른 노래입니다...........",
                35000,
                SaleType.GENERAL,
                "artE-UploadName",
                serverFileNameArtE
        );
        artE.addUser(userB);

        // when
        Art registerArtA = artService.registerArt(artA);
        Art registerArtB = artService.registerArt(artB);
        Art registerArtC = artService.registerArt(artC);
        Art registerArtD = artService.registerArt(artD);
        Art registerArtE = artService.registerArt(artE);

        // then
        assertThat(registerArtA).isEqualTo(artA);
        assertThat(registerArtB).isEqualTo(artB);
        assertThat(registerArtC).isEqualTo(artC);
        assertThat(registerArtD).isEqualTo(artD);
        assertThat(registerArtE).isEqualTo(artE);
    }

    // 2. 작품 정보 수정 테스트
    @Test
    @DisplayName("작품 정보 수정")
    void test2() {
        // given
        Users userA = new Users(
                "서지원",
                "Seo Ji Won",
                "sjiwon",
                "1234",
                "경기대학교",
                "01011112222",
                "경기도 안양",
                LocalDate.of(2000, 1, 18)
        );
        userService.joinUser(userA);

        String serverFileNameArtA = UUID.randomUUID().toString().replaceAll("-", "");
        log.info("\nArtA가 서버에 저장되는 이름(UUID) = {}", serverFileNameArtA);
        Art artA = new Art(
                "너의 모든 순간",
                "이 작품은 사실 노래이고 성시경이 부른 노래입니다...........",
                15000,
                SaleType.GENERAL,
                "artA-UploadName",
                serverFileNameArtA
        );
        artA.addUser(userA);
        artService.registerArt(artA);

        // when
        ArtEditDTO artEditDTO = new ArtEditDTO("작품 : 노래 / 가수 : 성시경");
        artService.editArt(artA.getId(), artEditDTO);

        // then
        assertThat(artA.getDescription()).isEqualTo(artEditDTO.getDescription());
    }

    // 3. 작품 판매 타입 (경매/일반)
    @Test
    @DisplayName("작품 판매 타입(경매/일반)")
    void test3() {
        // given
        Users userA = new Users(
                "서지원",
                "Seo Ji Won",
                "sjiwon",
                "1234",
                "경기대학교",
                "01011112222",
                "경기도 안양",
                LocalDate.of(2000, 1, 18)
        );
        userService.joinUser(userA);

        String serverFileNameArtA = UUID.randomUUID().toString().replaceAll("-", "");
        log.info("\nArtA가 서버에 저장되는 이름(UUID) = {}", serverFileNameArtA);
        Art artA = new Art(
                "너의 모든 순간",
                "이 작품은 사실 노래이고 성시경이 부른 노래입니다...........",
                15000,
                SaleType.GENERAL,
                "artA-UploadName",
                serverFileNameArtA

        );
        artA.addUser(userA);
        artService.registerArt(artA);

        String serverFileNameArtB = UUID.randomUUID().toString().replaceAll("-", "");
        log.info("\nArtB가 서버에 저장되는 이름(UUID) = {}", serverFileNameArtB);
        Art artB = new Art(
                "희재",
                "이 작품은 사실 노래이고 성시경이 부른 노래입니다...........",
                30000,
                SaleType.AUCTION,
                "artB-UploadName",
                serverFileNameArtB
        );
        artB.addUser(userA);
        artService.registerArt(artB);

        // when
        String saleTypeArtA = artService.getSaleType(artA.getId());
        String saleTypeArtB = artService.getSaleType(artB.getId());

        // then
        assertThat(saleTypeArtA).isEqualTo("GENERAL");
        assertThat(saleTypeArtB).isEqualTo("AUCTION");
    }

    // 4. [user_id]의 작품 리스트 조회 테스트
    @Test
    @DisplayName("[user_id]의 작품 리스트 조회")
    void test4(){
        // given
        Users userA = new Users(
                "서지원",
                "Seo Ji Won",
                "sjiwon",
                "1234",
                "경기대학교",
                "01011112222",
                "경기도 안양",
                LocalDate.of(2000, 1, 18)
        );
        userService.joinUser(userA);

        String serverFileNameArtA = UUID.randomUUID().toString().replaceAll("-", "");
        log.info("\nArtA가 서버에 저장되는 이름(UUID) = {}", serverFileNameArtA);
        Art artA = new Art(
                "너의 모든 순간",
                "이 작품은 사실 노래이고 성시경이 부른 노래입니다...........",
                15000,
                SaleType.GENERAL,
                "artA-UploadName",
                serverFileNameArtA
        );
        artA.addUser(userA);
        artService.registerArt(artA);

        String serverFileNameArtB = UUID.randomUUID().toString().replaceAll("-", "");
        log.info("\nArtB가 서버에 저장되는 이름(UUID) = {}", serverFileNameArtB);
        Art artB = new Art(
                "희재",
                "이 작품은 사실 노래이고 성시경이 부른 노래입니다...........",
                30000,
                SaleType.GENERAL,
                "artB-UploadName",
                serverFileNameArtB
        );
        artB.addUser(userA);
        artService.registerArt(artB);

        String serverFileNameArtC = UUID.randomUUID().toString().replaceAll("-", "");
        log.info("\nArtC가 서버에 저장되는 이름(UUID) = {}", serverFileNameArtC);
        Art artC = new Art(
                "안녕 나의 사랑",
                "이 작품은 사실 노래이고 성시경이 부른 노래입니다...........",
                18000,
                SaleType.GENERAL,
                "artC-UploadName",
                serverFileNameArtC
        );
        artC.addUser(userA);
        artService.registerArt(artC);

        String serverFileNameArtD = UUID.randomUUID().toString().replaceAll("-", "");
        log.info("\nArtD가 서버에 저장되는 이름(UUID) = {}", serverFileNameArtD);
        Art artD = new Art(
                "어디에도",
                "이 작품은 사실 노래이고 엠씨더맥스가 부른 노래입니다...........",
                45000,
                SaleType.GENERAL,
                "artD-UploadName",
                serverFileNameArtD
        );
        artD.addUser(userA);
        artService.registerArt(artD);

        // when
        List<Art> artListFromUserA = artService.artListFromUserId(userA.getId());

        // then
        assertThat(artListFromUserA.size()).isEqualTo(4);
        assertThat(artListFromUserA).contains(artA, artB, artC, artD);
    }
}