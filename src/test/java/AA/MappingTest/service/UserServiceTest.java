package AA.MappingTest.service;

import AA.MappingTest.domain.PointHistory;
import AA.MappingTest.domain.Users;
import AA.MappingTest.enums.DealType;
import AA.MappingTest.exception.NoMoneyException;
import AA.MappingTest.repository.PointHistoryRepository;
import AA.MappingTest.service.DTO.PointTransferForm;
import AA.MappingTest.service.DTO.UserEditForm;
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
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Slf4j
@SpringBootTest
@Transactional
class UserServiceTest {
    @Autowired
    UserService userService;

    @Autowired
    PointHistoryRepository pointHistoryRepository;

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

    // 1. 회원가입 테스트
    @Test
    @DisplayName("User회원가입 + Point Instance 자동 생성되는지")
    void test1() {
        log.info("------------------ 회원가입 테스트 시작 ------------------");

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

        // when
        Users joinUserA = userService.joinUser(userA);
        Users joinUserB = userService.joinUser(userB);

        // then
        assertThat(joinUserA).isEqualTo(userA);
        assertThat(joinUserB).isEqualTo(userB);
        log.info("------------------ 회원가입 테스트 종료 ------------------");
    }

    // 2. 회원 정보 수정 테스트
    @Test
    @DisplayName("회원 정보 수정 테스트")
    void test2() {
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
        assertThat(joinUser).isNotNull();

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

    // 3. [user_id]로 회원 정보 조회 테스트
    @Test
    @DisplayName("[user_id]로 회원 정보 조회")
    void test3() {
        // given
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

        assertThat(joinUser1).isNotNull();
        assertThat(joinUser2).isNotNull();

        log.info("------------------ 회원 조회 테스트 시작 ------------------");
        // when
        Optional<Users> findUser1 = userService.findUserById(joinUser1.getId());
        Optional<Users> findUser2 = userService.findUserById(joinUser2.getId());

        // then
        assertThat(findUser1).isNotNull();
        assertThat(findUser2).isNotNull();
        assertThat(findUser1).contains(joinUser1);
        assertThat(findUser2).contains(joinUser2);

        log.info("------------------ 회원 조회 테스트 종료 ------------------");
    }

    // 4. loginId로 password 조회 테스트
    @Test
    @DisplayName("loginId로 Password 조회")
    void test4() {
        // given
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

        log.info("------------------ LoginId로 Password 찾기 테스트 시작 ------------------");
        // when
        String passwordForUser1 = userService.findPasswordByLoginId(joinUser1.getLoginId());
        String passwordForUser2 = userService.findPasswordByLoginId(joinUser2.getLoginId());

        // then
        assertThat(passwordForUser1).isEqualTo(joinUser1.getLoginPassword());
        assertThat(passwordForUser2).isEqualTo(joinUser2.getLoginPassword());
        log.info("------------------ LoginId로 Password 찾기 테스트 종료 ------------------");
    }

    // 5. [user_id]로 소속 학교 찾기 테스트
    @Test
    @DisplayName("[user_id]로 소속 학교 찾기")
    void test5() {
        // given
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

        log.info("------------------ [user_id]로 소속 학교 찾기 테스트 시작 ------------------");
        // when
        String schoolByJoinUser1 = userService.findSchoolById(joinUser1.getId());
        String schoolByJoinUser2 = userService.findSchoolById(joinUser2.getId());

        // then
        assertThat(schoolByJoinUser1).isEqualTo(joinUser1.getSchoolName());
        assertThat(schoolByJoinUser2).isEqualTo(joinUser2.getSchoolName());
        log.info("------------------ [user_id]로 소속 학교 찾기 테스트 종료 ------------------");
    }

    // 6. [user_id] -> 포인트 거래
    @Test
    @DisplayName("6-1 : 충전")
    void test6_1() {
        // given
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

        // when
        log.info("------------------ [user_id] 포인트 충전 테스트 시작 ------------------");
        int chargeAmout = 50000;
        
        userService.pointTransfer(joinUser.getId(), new PointTransferForm(DealType.CHARGE, chargeAmout));

        // then
        List<PointHistory> pointHistoryByUserId = pointHistoryRepository.findPointHistoryByUserId(joinUser.getId());
        System.out.println("## user_id : " + joinUser.getId() + " 의 포인트 거래내역 ##");
        for (PointHistory pointHistory : pointHistoryByUserId) {
            System.out.println(pointHistory);
        }
//        log.info("[user_id : {}]의 포인트 거래내역 = {}", joinUser.getId(), pointHistoryByUserId);
        assertThat(pointHistoryByUserId
                        .stream()
                        .filter(pointHistory -> pointHistory.getPoint() == chargeAmout)
                        .findFirst()
                        .orElseThrow()
                        .getPoint()
                ).isEqualTo(chargeAmout);

        log.info("------------------ [user_id] 포인트 충전 테스트 종료 ------------------");
    }

    @Test
    @DisplayName("6-2 : 정상 환불")
    void test6_2() {
        // given
        int initPoint = 50000;

        Users joinUser = userService.joinUser2(new Users(
                "서지원",
                "서지원123",
                "sjiwon",
                "1234",
                "경기대학교",
                "01012345678",
                "경기도 안양시 동안구 비산3동 평촌대로401번길 44-9 덕원아파트 가동 403호",
                LocalDate.of(2000, 1, 18)
        ), initPoint);

        // when
        log.info("------------------ [user_id] 포인트 환불 테스트 시작 ------------------");
        int refundAmount = 49999;

        userService.pointTransfer(joinUser.getId(), new PointTransferForm(DealType.REFUND, refundAmount));

        // then
        List<PointHistory> pointHistoryByUserId = pointHistoryRepository.findPointHistoryByUserId(joinUser.getId());
        System.out.println("## user_id : " + joinUser.getId() + " 의 포인트 거래내역 ##");
        for (PointHistory pointHistory : pointHistoryByUserId) {
            System.out.println(pointHistory);
        }
//        log.info("[user_id : {}]의 포인트 거래내역 = {}", joinUser.getId(), pointHistoryByUserId);
        assertThat(pointHistoryByUserId
                .stream()
                .filter(pointHistory -> pointHistory.getDealAmount() == refundAmount)
                .findFirst()
                .orElseThrow()
                .getDealAmount()
        ).isEqualTo(refundAmount);

        assertThat(pointHistoryByUserId
                .stream()
                .filter(pointHistory -> pointHistory.getPoint() == (initPoint - refundAmount))
                .findFirst()
                .orElseThrow()
                .getPoint()
        ).isEqualTo(1);

        log.info("------------------ [user_id] 포인트 환불 테스트 종료 ------------------");
    }

    @Test
    @DisplayName("6-3 : 환불 예외")
    void test6_3() {
        // given
        int initPoint = 50000;

        Users joinUser = userService.joinUser2(new Users(
                "서지원",
                "서지원123",
                "sjiwon",
                "1234",
                "경기대학교",
                "01012345678",
                "경기도 안양시 동안구 비산3동 평촌대로401번길 44-9 덕원아파트 가동 403호",
                LocalDate.of(2000, 1, 18)
        ), initPoint);

        // when
        log.info("------------------ [user_id] 포인트 환불 테스트 시작 ------------------");
        int refundAmount = 50001;

        assertThatThrownBy(() -> userService.pointTransfer(joinUser.getId(), new PointTransferForm(DealType.REFUND, refundAmount)))
                .isInstanceOf(NoMoneyException.class);
//        userService.pointTransfer(joinUser.getId(), new PointTransferForm(DealType.REFUND, refundAmount));

        // then
        List<PointHistory> pointHistoryByUserId = pointHistoryRepository.findPointHistoryByUserId(joinUser.getId());
        System.out.println("## user_id : " + joinUser.getId() + " 의 포인트 거래내역 ##");
        for (PointHistory pointHistory : pointHistoryByUserId) {
            System.out.println(pointHistory);
        }
//        log.info("[user_id : {}]의 포인트 거래내역 = {}", joinUser.getId(), pointHistoryByUserId);

        assertThat(pointHistoryByUserId.size()).isEqualTo(1);

        log.info("------------------ [user_id] 포인트 환불 테스트 종료 ------------------");
    }

    @Test
    @DisplayName("6-4 : 정상 사용")
    void test6_4() {
        // given
        int initPoint = 50000;

        Users joinUser = userService.joinUser2(new Users(
                "서지원",
                "서지원123",
                "sjiwon",
                "1234",
                "경기대학교",
                "01012345678",
                "경기도 안양시 동안구 비산3동 평촌대로401번길 44-9 덕원아파트 가동 403호",
                LocalDate.of(2000, 1, 18)
        ), initPoint);

        // when
        log.info("------------------ [user_id] 포인트 사용 테스트 시작 ------------------");
        int useAmount = 30000;

        userService.pointTransfer(joinUser.getId(), new PointTransferForm(DealType.USE, useAmount));

        // then
        List<PointHistory> pointHistoryByUserId = pointHistoryRepository.findPointHistoryByUserId(joinUser.getId());
        System.out.println("## user_id : " + joinUser.getId() + " 의 포인트 거래내역 ##");
        for (PointHistory pointHistory : pointHistoryByUserId) {
            System.out.println(pointHistory);
        }
//        log.info("[user_id : {}]의 포인트 거래내역 = {}", joinUser.getId(), pointHistoryByUserId);
        assertThat(pointHistoryByUserId
                .stream()
                .filter(pointHistory -> pointHistory.getDealAmount() == useAmount)
                .findFirst()
                .orElseThrow()
                .getDealAmount()
        ).isEqualTo(useAmount);

        assertThat(pointHistoryByUserId
                .stream()
                .filter(pointHistory -> pointHistory.getPoint() == (initPoint - useAmount))
                .findFirst()
                .orElseThrow()
                .getPoint()
        ).isEqualTo(20000);

        log.info("------------------ [user_id] 포인트 사용 테스트 종료 ------------------");
    }

    @Test
    @DisplayName("6-5 : 사용 예외")
    void test6_5(){
        // given
        int initPoint = 50000;

        Users joinUser = userService.joinUser2(new Users(
                "서지원",
                "서지원123",
                "sjiwon",
                "1234",
                "경기대학교",
                "01012345678",
                "경기도 안양시 동안구 비산3동 평촌대로401번길 44-9 덕원아파트 가동 403호",
                LocalDate.of(2000, 1, 18)
        ), initPoint);

        // when
        log.info("------------------ [user_id] 포인트 사용 테스트 시작 ------------------");
        int useAmount = 70000;

        assertThatThrownBy(() -> userService.pointTransfer(joinUser.getId(), new PointTransferForm(DealType.REFUND, useAmount)))
                .isInstanceOf(NoMoneyException.class);
//        userService.pointTransfer(joinUser.getId(), new PointTransferForm(DealType.USE, useAmount));

        // then
        List<PointHistory> pointHistoryByUserId = pointHistoryRepository.findPointHistoryByUserId(joinUser.getId());
        System.out.println("## user_id : " + joinUser.getId() + " 의 포인트 거래내역 ##");
        for (PointHistory pointHistory : pointHistoryByUserId) {
            System.out.println(pointHistory);
        }
//        log.info("[user_id : {}]의 포인트 거래내역 = {}", joinUser.getId(), pointHistoryByUserId);

        assertThat(pointHistoryByUserId.size()).isEqualTo(1);

        log.info("------------------ [user_id] 포인트 사용 테스트 종료 ------------------");
    }
}