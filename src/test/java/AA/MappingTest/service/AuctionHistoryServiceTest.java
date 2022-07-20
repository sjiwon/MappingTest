package AA.MappingTest.service;

import AA.MappingTest.domain.Art;
import AA.MappingTest.domain.Auction;
import AA.MappingTest.domain.AuctionHistory;
import AA.MappingTest.domain.Users;
import AA.MappingTest.enums.SaleType;
import AA.MappingTest.service.DTO.AuctionBidForm;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.DiscriminatorValue;
import javax.persistence.EntityManager;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
@Transactional
class AuctionHistoryServiceTest {

    @Autowired
    AuctionHistoryService auctionHistoryService;

    @Autowired
    UserService userService;

    @Autowired
    ArtService artService;

    @Autowired
    AuctionService auctionService;

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


    // 1. [user_id]가 참여한 경매 목록 조회
    @Test
    @DisplayName("[user_id]가 참여한 경매 목록")
    void test1(){
        // given
        Users userA = new Users(
                "서지원",
                "Seo Ji Won",
                "sjiwon",
                "1234",
                "경기대학교",
                "01012345678",
                "경기도 안양",
                LocalDate.of(2000, 1, 18)
        );
        userService.joinUser(userA);

        String storageNameArtA = UUID.randomUUID().toString().replaceAll("-", "");
        System.out.println("서버에 저장되는 Art 이름 = " + storageNameArtA);
        Art art = new Art(
                "너의 모든 순간",
                "이 노래는 성시경이 불렀습니다",
                10000,
                SaleType.AUCTION,
                "art-UploadName",
                storageNameArtA
        );
        art.addUser(userA);
        artService.registerArt(art);

        Auction auction = auctionService.registerAuction(
                userA,
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

        auctionService.executeBid(auction.getId(), new AuctionBidForm(userB, 12000));
        auctionService.executeBid(auction.getId(), new AuctionBidForm(userC, 13000));
        auctionService.executeBid(auction.getId(), new AuctionBidForm(userB, 22000));
        auctionService.executeBid(auction.getId(), new AuctionBidForm(userC, 32000));
        auctionService.executeBid(auction.getId(), new AuctionBidForm(userB, 50000));

        // when
        List<AuctionHistory> auctionListFromUserA = auctionHistoryService.getAuctionListFromUserId(userA.getId());
        System.out.println("===== userA의 경매 비드 내역 =====");
        for (AuctionHistory auctionHistory : auctionListFromUserA) {
            System.out.println(auctionHistory);
        }

        List<AuctionHistory> auctionListFromUserB = auctionHistoryService.getAuctionListFromUserId(userB.getId());
        System.out.println("===== userB의 경매 비드 내역 =====");
        for (AuctionHistory auctionHistory : auctionListFromUserB) {
            System.out.println(auctionHistory);
        }

        List<AuctionHistory> auctionListFromUserC = auctionHistoryService.getAuctionListFromUserId(userC.getId());
        System.out.println("===== userC의 경매 비드 내역 =====");
        for (AuctionHistory auctionHistory : auctionListFromUserC) {
            System.out.println(auctionHistory);
        }


        // then
        assertThat(auctionListFromUserA.size()).isEqualTo(0);
        assertThat(auctionListFromUserB.size()).isEqualTo(3);
        assertThat(auctionListFromUserB.stream().max(Comparator.comparingInt(AuctionHistory::getBidPrice)).orElseThrow().getBidPrice()).isEqualTo(50000);
        assertThat(auctionListFromUserC.size()).isEqualTo(2);
        assertThat(auctionListFromUserC.stream().max(Comparator.comparingInt(AuctionHistory::getBidPrice)).orElseThrow().getBidPrice()).isEqualTo(32000);
    }

    // 2. [art_id]의 경매내역
    @Test
    @DisplayName("[art_id]의 경매내역 조회")
    void test2(){
// given
        Users userA = new Users(
                "서지원",
                "Seo Ji Won",
                "sjiwon",
                "1234",
                "경기대학교",
                "01012345678",
                "경기도 안양",
                LocalDate.of(2000, 1, 18)
        );
        userService.joinUser(userA);

        String storageNameArtA = UUID.randomUUID().toString().replaceAll("-", "");
        System.out.println("서버에 저장되는 Art 이름 = " + storageNameArtA);
        Art artA = new Art(
                "너의 모든 순간",
                "이 노래는 성시경이 불렀습니다",
                10000,
                SaleType.AUCTION,
                "artA-UploadName",
                storageNameArtA
        );
        artA.addUser(userA);
        artService.registerArt(artA);

        String storageNameArtB = UUID.randomUUID().toString().replaceAll("-", "");
        System.out.println("서버에 저장되는 Art 이름 = " + storageNameArtB);
        Art artB = new Art(
                "희재",
                "이 노래는 성시경이 불렀습니다",
                100500,
                SaleType.AUCTION,
                "artB-UploadName",
                storageNameArtB
        );
        artB.addUser(userA);
        artService.registerArt(artB);

        Auction auctionA = auctionService.registerAuction(
                userA,
                artA,
                LocalDateTime.of(2022, 7, 7, 13, 0),
                LocalDateTime.of(2022, 7, 8, 13, 0)
        );

        Auction auctionB = auctionService.registerAuction(
                userA,
                artB,
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

        auctionService.executeBid(auctionA.getId(), new AuctionBidForm(userB, 12000));
        auctionService.executeBid(auctionA.getId(), new AuctionBidForm(userC, 13000));
        auctionService.executeBid(auctionA.getId(), new AuctionBidForm(userB, 22000));
        auctionService.executeBid(auctionA.getId(), new AuctionBidForm(userC, 32000));
        auctionService.executeBid(auctionA.getId(), new AuctionBidForm(userB, 50000));

        auctionService.executeBid(auctionB.getId(), new AuctionBidForm(userB, 30000));
        auctionService.executeBid(auctionB.getId(), new AuctionBidForm(userC, 350000));

        // when
        List<AuctionHistory> auctionListFromArtA = auctionHistoryService.getAuctionListFromArtId(artA.getId());
        System.out.println("===== ArtA의 경매 내역 =====");
        for (AuctionHistory auctionHistory : auctionListFromArtA) {
            System.out.println(auctionHistory);
        }

        List<AuctionHistory> auctionListFromArtB = auctionHistoryService.getAuctionListFromArtId(artB.getId());
        System.out.println("===== ArtB의 경매 내역 =====");
        for (AuctionHistory auctionHistory : auctionListFromArtB) {
            System.out.println(auctionHistory);
        }

        // then
        assertThat(auctionListFromArtA.size()).isEqualTo(5);
        assertThat(auctionListFromArtB.size()).isEqualTo(2);
    }


    // 3. [auction_id]의 bid 내역
    @Test
    @DisplayName("[auction_id]의 bid 내역")
    void test3(){
        // given
        Users userA = new Users(
                "서지원",
                "Seo Ji Won",
                "sjiwon",
                "1234",
                "경기대학교",
                "01012345678",
                "경기도 안양",
                LocalDate.of(2000, 1, 18)
        );
        userService.joinUser(userA);

        String storageNameArtA = UUID.randomUUID().toString().replaceAll("-", "");
        System.out.println("서버에 저장되는 Art 이름 = " + storageNameArtA);
        Art art = new Art(
                "너의 모든 순간",
                "이 노래는 성시경이 불렀습니다",
                10000,
                SaleType.AUCTION,
                "art-UploadName",
                storageNameArtA
        );
        art.addUser(userA);
        artService.registerArt(art);

        Auction auction = auctionService.registerAuction(
                userA,
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

        auctionService.executeBid(auction.getId(), new AuctionBidForm(userB, 12000));
        auctionService.executeBid(auction.getId(), new AuctionBidForm(userC, 13000));
        auctionService.executeBid(auction.getId(), new AuctionBidForm(userB, 22000));
        auctionService.executeBid(auction.getId(), new AuctionBidForm(userC, 32000));
        auctionService.executeBid(auction.getId(), new AuctionBidForm(userB, 50000));

        // when
        List<AuctionHistory> auctionListFromAuction = auctionHistoryService.getAuctionListFromAuctionId(auction.getId());
        System.out.println("===== Auction의 bid 내역 =====");
        for (AuctionHistory auctionHistory : auctionListFromAuction) {
            System.out.println(auctionHistory);
        }

        // then
        assertThat(auctionListFromAuction.size()).isEqualTo(5);
    }
}