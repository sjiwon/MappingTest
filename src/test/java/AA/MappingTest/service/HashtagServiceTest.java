package AA.MappingTest.service;

import AA.MappingTest.domain.Hashtag;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
@Transactional
class HashtagServiceTest {
    @Autowired
    HashtagService hashtagService;

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

    // 1. 해시태그 등록 테스트
    @Test
    @DisplayName("해시태그 등록")
    void test1(){
        // given
        Hashtag hashTag1 = Hashtag.createHashtag("HashtagA");
        Hashtag hashTag2 = Hashtag.createHashtag("HashtagB");
        Hashtag hashTag3 = Hashtag.createHashtag("HashtagC");

        // when
        Hashtag registerHashTag1 = hashtagService.registerHashTag(hashTag1);
        Hashtag registerHashTag2 = hashtagService.registerHashTag(hashTag2);
        Hashtag registerHashTag3 = hashtagService.registerHashTag(hashTag3);

        // then
        assertThat(registerHashTag1).isEqualTo(hashTag1);
        assertThat(registerHashTag2).isEqualTo(hashTag2);
        assertThat(registerHashTag3).isEqualTo(hashTag3);
    }

    // 2. [hashtag_id]로 해시태그 검색
    @Test
    @DisplayName("[hashtag_id]로 해시태그 검색")
    void test2(){
        // given
        Hashtag hashTag1 = Hashtag.createHashtag("HashtagA");
        Hashtag hashTag2 = Hashtag.createHashtag("HashtagB");
        Hashtag hashTag3 = Hashtag.createHashtag("HashtagC");

        hashtagService.registerHashTag(hashTag1);
        hashtagService.registerHashTag(hashTag2);
        hashtagService.registerHashTag(hashTag3);

        // when
        Hashtag findHashTagById1 = hashtagService.findHashtagById(hashTag1.getId());
        Hashtag findHashTagById2 = hashtagService.findHashtagById(hashTag2.getId());
        Hashtag findHashTagById3 = hashtagService.findHashtagById(hashTag3.getId());

        // then
        assertThat(findHashTagById1).isEqualTo(hashTag1);
        assertThat(findHashTagById2).isEqualTo(hashTag2);
        assertThat(findHashTagById3).isEqualTo(hashTag3);
    }
}