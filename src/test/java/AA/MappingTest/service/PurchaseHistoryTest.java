package AA.MappingTest.service;

import AA.MappingTest.domain.*;
import AA.MappingTest.domain.enums.SaleType;
import AA.MappingTest.repository.ArtHashtagRepository;
import AA.MappingTest.repository.PurchaseHistoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
@SpringBootTest
@Transactional
public class PurchaseHistoryTest {
    @Autowired
    UserService userService;

    @Autowired
    ArtService artService;

    @Autowired
    AuctionService auctionService;

    @Autowired
    PurchaseHistoryRepository purchaseHistoryRepository;

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
    void t(){
        Users user = Users.createUser(
                "user",
                "user",
                "user",
                "user",
                "경기대학교",
                "01022225678",
                "경기도 안양",
                LocalDate.of(2000, 1, 18)
        );
        userService.joinUser(user);

        String serverFileNameArtA = UUID.randomUUID().toString().replaceAll("-", "");
        log.info("\nArtA가 서버에 저장되는 이름(UUID) = {}", serverFileNameArtA);
        Art artA = Art.createArt(
                user,
                "너의 모든 순간",
                "이 작품은 사실 노래이고 성시경이 부른 노래입니다...........",
                15000,
                SaleType.AUCTION,
                "artA-UploadName",
                serverFileNameArtA
        );
        artService.registerArt(artA);

        String serverFileNameArtB = UUID.randomUUID().toString().replaceAll("-", "");
        log.info("\nArtB가 서버에 저장되는 이름(UUID) = {}", serverFileNameArtB);
        Art artB = Art.createArt(
                user,
                "희재",
                "이 작품은 사실 노래이고 성시경이 부른 노래입니다...........",
                30000,
                SaleType.GENERAL,
                "artB-UploadName",
                serverFileNameArtB
        );
        artService.registerArt(artB);

        Auction auction = auctionService.registerAuction(
                artA,
                LocalDateTime.of(2022, 7, 7, 13, 0),
                LocalDateTime.of(2022, 7, 8, 13, 0)
        );

        Hashtag hashTag1 = Hashtag.createHashtag("HashtagA");
        Hashtag hashTag2 = Hashtag.createHashtag("HashtagB");
        Hashtag hashTag3 = Hashtag.createHashtag("HashtagC");
        hashtagService.registerHashTag(hashTag1);
        hashtagService.registerHashTag(hashTag2);
        hashtagService.registerHashTag(hashTag3);

        artService.addHashtag(artA.getId(), hashTag1, hashTag2, hashTag3);
        artService.addHashtag(artB.getId(), hashTag1, hashTag3);

        // 일반 구매
        Users userA = Users.createUser(
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

        PurchaseHistory purchaseHistoryByGeneral = purchaseHistoryRepository.save(PurchaseHistory.createPurchaseHistoryByGeneral(userA, artB, artB.getInitPrice()));
        System.out.println(purchaseHistoryByGeneral);

        PurchaseHistory purchaseHistoryByAuction = purchaseHistoryRepository.save(PurchaseHistory.createPurchaseHistoryByAuction(userA, artA, auction, artA.getInitPrice() + 20000000));
        System.out.println(purchaseHistoryByAuction);
    }
}
