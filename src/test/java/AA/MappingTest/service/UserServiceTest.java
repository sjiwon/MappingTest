package AA.MappingTest.service;

import AA.MappingTest.domain.Users;
import AA.MappingTest.service.DTO.UserEditForm;
import lombok.RequiredArgsConstructor;
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

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
@RequiredArgsConstructor
@Transactional
class UserServiceTest {
    @Autowired
    UserService userService;

    @Autowired
    private EntityManager em;

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

    // 1. 회원가입 테스트
    @Test
    @DisplayName("User회원가입 + Point Instance 자동 생성되는지")
    void test1() {
        log.info("=== 회원가입 테스트 시작 ===");
        Users joinUser1 = userService.joinUser(new Users(
                "서지원",
                "서지원123",
                "sjiwon",
                "1234",
                "경기대학교",
                "01012345678",
                "경기도 안양시 동안구 비산3동 평촌대로401번길 44-9 덕원아파트 가동 403호",
                LocalDate.of(2000, 1, 18)
        ));
        Assertions.assertThat(joinUser1).isNotNull();

        Users joinUser2 = userService.joinUser(new Users(
                "서지원2",
                "서지원123456",
                "sjiwon1234",
                "12345678",
                "경기대학교2",
                "01099998888",
                "경기도 안양시 동안구 비산3동 평촌대로401번길 44-9 덕원아파트 가동 403호",
                LocalDate.of(2020, 10, 31)
        ));
        Assertions.assertThat(joinUser2).isNotNull();
        log.info("=== 회원가입 테스트 종료 ===");
    }

    // 2. 회원 정보 수정 테스트
    @Test
    @DisplayName("회원 정보 수정 테스트")
    void test2(){
        Users joinUser = userService.joinUser(new Users(
                "서지원",
                "서지원123",
                "sjiwon",
                "1234",
                "경기대학교",
                "01012345678",
                "경기도 안양시 동안구 비산3동 평촌대로401번길 44-9 덕원아파트 가동 403호",
                LocalDate.of(2000, 1, 18)
        ));

        Assertions.assertThat(joinUser).isNotNull();

        log.info("------------------ 회원정보 수정 테스트 시작 ------------------");

        // 1. 닉네임만 변경
        log.info("=== 회원정보 수정 테스트 1) 닉네임만 변경 ===");
        userService.editUser(joinUser.getId(), new UserEditForm("321서지원", "", ""));

        // 2. 닉네임 + 전화번호 변경
        log.info("=== 회원정보 수정 테스트 2) 닉네임 + 전화번호 변경 ===");
        userService.editUser(joinUser.getId(), new UserEditForm("xxx321서지원xxx", "01098765432", ""));

        // 3. 닉네임 + 전화번호 + 주소 변경
        log.info("=== 회원정보 수정 테스트 3) 닉네임 + 전화번호 + 주소 변경 ===");
        userService.editUser(joinUser.getId(), new UserEditForm("xxx서지원xxx", "01000001111", "안양"));

        log.info("------------------ 회원정보 수정 테스트 종료 ------------------");
    }
}