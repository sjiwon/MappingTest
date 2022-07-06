package AA.MappingTest.service;

import AA.MappingTest.domain.Art;
import AA.MappingTest.domain.Auction;
import AA.MappingTest.domain.AuctionHistory;
import AA.MappingTest.domain.Users;
import AA.MappingTest.enums.SaleType;
import AA.MappingTest.exception.MoreBidPrice;
import AA.MappingTest.exception.NoAuctionTypeException;
import AA.MappingTest.exception.NoBidMyArt;
import AA.MappingTest.service.DTO.AuctionBidForm;
import AA.MappingTest.service.DTO.AuctionHighestUserForm;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
@Transactional
class AuctionServiceTest {

    @Autowired
    AuctionService auctionService;

    @Autowired
    UserService userService;

    @Autowired
    ArtService artService;

    @Autowired
    AuctionHistoryService auctionHistoryService;

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
                "like_artist",
                "point_history",
                "purchase_history"
        };

        for (String table : tables) {
            String query = "alter table " + table + " auto_increment = 1";
            em.createNativeQuery(query).executeUpdate();
        }
    }

    // 1-1. 경매 등록 테스트 (정상 등록)
    @Test
    @DisplayName("경매 등록 테스트 (정상)")
    void test1() {
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

        String storageNameArtA = UUID.randomUUID().toString().replaceAll("-", "");
        System.out.println("서버에 저장되는 Art 이름 = " + storageNameArtA);
        Art art = new Art(
                "너의 모든 순간",
                "이 노래는 성시경이 불렀습니다",
                10000,
                SaleType.AUCTION,
                storageNameArtA,
                user
        );
        artService.registerArt(art);

        // when
        Auction auction = auctionService.registerAuction(
                user,
                art,
                LocalDateTime.of(2022, 7, 7, 13, 0),
                LocalDateTime.of(2022, 7, 8, 13, 0)
        );

        // then
        List<AuctionHistory> findAuctionHistoryFromAuctionId = auctionHistoryService.getAuctionListFromArtId(auction.getId());
        for (AuctionHistory auctionHistory : findAuctionHistoryFromAuctionId) {
            System.out.println(auctionHistory);
        }

        assertThat(auction.getUser()).isEqualTo(user);
        assertThat(auction.getArt()).isEqualTo(art);
    }

    // 1.1 경매 등록 (예외 발생)
    @Test
    @DisplayName("경매 등록 테스트 (예외)")
    void test1_1() {
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

        String storageNameArtA = UUID.randomUUID().toString().replaceAll("-", "");
        System.out.println("서버에 저장되는 Art 이름 = " + storageNameArtA);
        Art art = new Art(
                "너의 모든 순간",
                "이 노래는 성시경이 불렀습니다",
                10000,
                SaleType.GENERAL,
                storageNameArtA,
                user
        );
        artService.registerArt(art);

        // when
        assertThatThrownBy(() -> auctionService.registerAuction(
                user,
                art,
                LocalDateTime.of(2022, 7, 7, 13, 0),
                LocalDateTime.of(2022, 7, 8, 13, 0)
        )).isInstanceOf(NoAuctionTypeException.class);

//        // then
//        List<AuctionHistory> findAuctionHistoryFromAuctionId = auctionHistoryService.getAuctionListFromArtId(auction.getId());
//        for (AuctionHistory auctionHistory : findAuctionHistoryFromAuctionId) {
//            System.out.println(auctionHistory);
//        }
//
//        assertThat(auction.getUser()).isEqualTo(user);
//        assertThat(auction.getArt()).isEqualTo(art);
    }

    // 2. 경매 끝났는지 여부
    @Test
    @DisplayName("경매 끝났는지 여부")
    void test2() {
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

        String storageNameArtA = UUID.randomUUID().toString().replaceAll("-", "");
        System.out.println("서버에 저장되는 Art 이름 = " + storageNameArtA);
        Art art = new Art(
                "너의 모든 순간",
                "이 노래는 성시경이 불렀습니다",
                10000,
                SaleType.AUCTION,
                storageNameArtA,
                user
        );
        artService.registerArt(art);

        Auction auction = auctionService.registerAuction(
                user,
                art,
                LocalDateTime.of(2022, 7, 7, 13, 0),
                LocalDateTime.of(2022, 7, 8, 13, 0)
        );

        // when
        boolean auctionFinished1 = auctionService.isAuctionFinished(auction.getId(), LocalDateTime.of(2022, 7, 7, 13, 1));
        boolean auctionFinished2 = auctionService.isAuctionFinished(auction.getId(), LocalDateTime.of(2022, 7, 8, 13, 1));

        // then
        assertThat(auctionFinished1).isFalse();
        assertThat(auctionFinished2).isTrue();
    }

    // 3. 현재 경매 최고가 user 정보 & bid 정보 (정상 비드)
    @Test
    @DisplayName("경매 최고 bid에 대한 정보")
    void test3() {
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

        String storageNameArtA = UUID.randomUUID().toString().replaceAll("-", "");
        System.out.println("서버에 저장되는 Art 이름 = " + storageNameArtA);
        Art art = new Art(
                "너의 모든 순간",
                "이 노래는 성시경이 불렀습니다",
                10000,
                SaleType.AUCTION,
                storageNameArtA,
                user
        );
        artService.registerArt(art);

        Auction auction = auctionService.registerAuction(
                user,
                art,
                LocalDateTime.of(2022, 7, 7, 13, 0),
                LocalDateTime.of(2022, 7, 8, 13, 0)
        );

        // 경매에 비드할 2명의 사용자
        Users userB = new Users(
                "서지원2",
                "Seo Ji Won2",
                "sjiwon2",
                "12342",
                "경기대학교2",
                "01098765432",
                "경기도 안양2",
                LocalDate.of(2000, 1, 18)
        );
        userService.joinUser(userB);

        Users userC = new Users(
                "서지원3",
                "Seo Ji Won3",
                "sjiwon3",
                "1234",
                "경기대학교",
                "01013253948",
                "경기도 안양3",
                LocalDate.of(2000, 1, 18)
        );
        userService.joinUser(userC);


        // when
        auctionService.executeBid(auction.getId(), new AuctionBidForm(userB, 12000));
        auctionService.executeBid(auction.getId(), new AuctionBidForm(userC, 13000));
        auctionService.executeBid(auction.getId(), new AuctionBidForm(userB, 22000));
        auctionService.executeBid(auction.getId(), new AuctionBidForm(userC, 32000));
        auctionService.executeBid(auction.getId(), new AuctionBidForm(userB, 50000));

        // then
        List<AuctionHistory> auctionListFromArtId = auctionHistoryService.getAuctionListFromArtId(auction.getId());
        for (AuctionHistory auctionHistory : auctionListFromArtId) {
            System.out.println(auctionHistory);
        }
        assertThat(auctionListFromArtId.size()).isEqualTo(6);

        AuctionHighestUserForm info = auctionService.getInfo(auction.getId());
        assertThat(info.getUser()).isEqualTo(userB);
        assertThat(info.getPrice()).isEqualTo(50000);
    }

    // 3-1 경매 비드 (본인 작품에 비드 = 예외)
    @Test
    @DisplayName("경매 최고 bid에 대한 정보 (예외 : 본인 작품에 비드)")
    void test3_1() {
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

        String storageNameArtA = UUID.randomUUID().toString().replaceAll("-", "");
        System.out.println("서버에 저장되는 Art 이름 = " + storageNameArtA);
        Art art = new Art(
                "너의 모든 순간",
                "이 노래는 성시경이 불렀습니다",
                10000,
                SaleType.AUCTION,
                storageNameArtA,
                user
        );
        artService.registerArt(art);

        Auction auction = auctionService.registerAuction(
                user,
                art,
                LocalDateTime.of(2022, 7, 7, 13, 0),
                LocalDateTime.of(2022, 7, 8, 13, 0)
        );

        // 경매에 비드할 2명의 사용자
        Users userB = new Users(
                "서지원2",
                "Seo Ji Won2",
                "sjiwon2",
                "12342",
                "경기대학교2",
                "01098765432",
                "경기도 안양2",
                LocalDate.of(2000, 1, 18)
        );
        userService.joinUser(userB);

        Users userC = new Users(
                "서지원3",
                "Seo Ji Won3",
                "sjiwon3",
                "1234",
                "경기대학교",
                "01013253948",
                "경기도 안양3",
                LocalDate.of(2000, 1, 18)
        );
        userService.joinUser(userC);


        // when
        auctionService.executeBid(auction.getId(), new AuctionBidForm(userB, 12000));
        auctionService.executeBid(auction.getId(), new AuctionBidForm(userC, 13000));
        auctionService.executeBid(auction.getId(), new AuctionBidForm(userB, 22000));
        assertThatThrownBy(() -> auctionService.executeBid(auction.getId(), new AuctionBidForm(user, 32000)))
                .isInstanceOf(NoBidMyArt.class);

        // then
        List<AuctionHistory> auctionListFromArtId = auctionHistoryService.getAuctionListFromArtId(auction.getId());
        for (AuctionHistory auctionHistory : auctionListFromArtId) {
            System.out.println(auctionHistory);
        }
        assertThat(auctionListFromArtId.size()).isEqualTo(4);

        AuctionHighestUserForm info = auctionService.getInfo(auction.getId());
        assertThat(info.getUser()).isEqualTo(userB);
        assertThat(info.getPrice()).isEqualTo(22000);
    }

    // 3-2 경매 비드 (경매 최근 비드보다 작게 비드 = 예외)
    @Test
    @DisplayName("경매 최고 bid에 대한 정보 (예외 : 본인 작품에 비드)")
    void test3_2() {
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

        String storageNameArtA = UUID.randomUUID().toString().replaceAll("-", "");
        System.out.println("서버에 저장되는 Art 이름 = " + storageNameArtA);
        Art art = new Art(
                "너의 모든 순간",
                "이 노래는 성시경이 불렀습니다",
                10000,
                SaleType.AUCTION,
                storageNameArtA,
                user
        );
        artService.registerArt(art);

        Auction auction = auctionService.registerAuction(
                user,
                art,
                LocalDateTime.of(2022, 7, 7, 13, 0),
                LocalDateTime.of(2022, 7, 8, 13, 0)
        );

        // 경매에 비드할 2명의 사용자
        Users userB = new Users(
                "서지원2",
                "Seo Ji Won2",
                "sjiwon2",
                "12342",
                "경기대학교2",
                "01098765432",
                "경기도 안양2",
                LocalDate.of(2000, 1, 18)
        );
        userService.joinUser(userB);

        Users userC = new Users(
                "서지원3",
                "Seo Ji Won3",
                "sjiwon3",
                "1234",
                "경기대학교",
                "01013253948",
                "경기도 안양3",
                LocalDate.of(2000, 1, 18)
        );
        userService.joinUser(userC);


        // when
        assertThatThrownBy(() -> auctionService.executeBid(auction.getId(), new AuctionBidForm(userB, 9999)))
                .isInstanceOf(MoreBidPrice.class);

        // then
        List<AuctionHistory> auctionListFromArtId = auctionHistoryService.getAuctionListFromArtId(auction.getId());
        for (AuctionHistory auctionHistory : auctionListFromArtId) {
            System.out.println(auctionHistory);
        }

        assertThat(auctionListFromArtId.size()).isEqualTo(1);
    }

    // 4. 경매 대상 작품 확인
    @Test
    @DisplayName("경매 대상 작품 확인")
    void test4(){
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

        String storageNameArtA = UUID.randomUUID().toString().replaceAll("-", "");
        System.out.println("서버에 저장되는 Art 이름 = " + storageNameArtA);
        Art art = new Art(
                "너의 모든 순간",
                "이 노래는 성시경이 불렀습니다",
                10000,
                SaleType.AUCTION,
                storageNameArtA,
                user
        );
        artService.registerArt(art);

        Auction auction = auctionService.registerAuction(
                user,
                art,
                LocalDateTime.of(2022, 7, 7, 13, 0),
                LocalDateTime.of(2022, 7, 8, 13, 0)
        );

        // when
        Art findArtFromAuctionId = auctionService.getArtFromAuctionId(auction.getId());

        // then
        assertThat(findArtFromAuctionId).isEqualTo(art);
    }
}