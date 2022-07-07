package AA.MappingTest.service;

import AA.MappingTest.domain.*;
import AA.MappingTest.enums.DealType;
import AA.MappingTest.exception.NoMoneyException;
import AA.MappingTest.repository.PointHistoryRepository;
import AA.MappingTest.repository.UserRepository;
import AA.MappingTest.service.DTO.PointTransferForm;
import AA.MappingTest.service.DTO.UserEditForm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PointHistoryRepository pointHistoryRepository;

    @Transactional
    // 1. 회원가입 (최초 회원가입시 Point Instance도 자동 생성)
    public Users joinUser(Users user){
        Users joinUser = userRepository.save(user);
        PointHistory pointHistory = pointHistoryRepository.save(new PointHistory(user));

        log.info("\n회원가입 User = {}", joinUser);
        log.info("\nPointInstance 생성(JOIN) = {} / 생성된 user = {}", pointHistory, pointHistory.getUser());

        return joinUser;
    }

    @Transactional
    // UserServiceTest 6을 위한 joinUser2
    public Users joinUser2(Users user, Integer point){
        Users joinUser = userRepository.save(user);
        PointHistory pointHistory = pointHistoryRepository.save(new PointHistory(user, point));

        log.info("\n회원가입 User = {}", joinUser);
        log.info("\nPointInstance 생성(JOIN) = {} / 생성된 user = {}", pointHistory, pointHistory.getUser());

        return joinUser;
    }

    @Transactional
    // 2. 회원 정보 수정
    public void editUser(Long id, UserEditForm editUser){
        log.info("\n수정할 User 정보 = {}", editUser);

        Users findUser = userRepository.findById(id).orElseThrow();
        log.info("\n정보 수정 User = {}", findUser);

        if(StringUtils.hasText(editUser.getNickname())) {
            findUser.changeNickname(editUser.getNickname());
        }

        if(StringUtils.hasText(editUser.getPhoneNumber())) {
            findUser.changePhoneNumber(editUser.getPhoneNumber());
        }

        if(StringUtils.hasText(editUser.getAddress())) {
            findUser.changeAddress(editUser.getAddress());
        }

        log.info("\n수정된 User 정보 = {}", findUser);
    }

    // 3. user_id로 회원 정보 조회
    public Optional<Users> findUserById(Long id){
        Optional<Users> findUser = userRepository.findById(id);
        log.info("\n[user_id : {}]로 회원 조회한 결과 = {}", id, findUser);
        return findUser;
    }

    // 4. 아이디로 비밀번호 찾기
    public String findPasswordByLoginId(String loginId){
        String passwordByLoginId = userRepository.findPasswordByLoginId(loginId);
        log.info("\n{}로 찾은 비밀번호 = {}", loginId, passwordByLoginId);
        return passwordByLoginId;
    }

    // 5. 소속 학교 찾기
    public String findSchoolById(Long id){
        String schoolName = Objects.requireNonNull(userRepository.findById(id).orElseThrow()).getSchoolName();
        log.info("\n[user_id = {}] -> 소속 학교 = {}", id, schoolName);
        return schoolName;
    }

    // 6. 포인트 거래 (충전/환불/사용)
    @Transactional
    public void pointTransfer(Long id, PointTransferForm transferForm){
        Users findUser = userRepository.findById(id).orElse(null);
        log.info("\n포인트 거래할 회원 정보 = {}", findUser);

        Integer findUserPoint = Objects.requireNonNull(pointHistoryRepository.findPointHistoryByUserId(id)
                        .stream()
                        .max(Comparator.comparing(PointHistory::getDealDate))
                        .orElse(null))
                        .getPoint();
        log.info("\n{}의 현재 포인트 보유량 = {}", findUser, findUserPoint);

        if(transferForm.getDealType() == DealType.CHARGE){
            PointHistory pointHistory = pointHistoryRepository.save(new PointHistory(
                    transferForm.getDealType(),
                    transferForm.getDealAmount(),
                    findUserPoint + transferForm.getDealAmount(),
                    findUser
            ));
            log.info("\n{}의 포인트 충전 내역 = {}", findUser, pointHistory);
        }
        else if (transferForm.getDealType() == DealType.REFUND) {
            if(transferForm.getDealAmount() > findUserPoint){
                log.info("\n실패한 환불 요청 {}", transferForm);
                throw new NoMoneyException("보유한 포인트량을 초과해서 환불할 수 없습니다");
            } else {
                PointHistory pointHistory = pointHistoryRepository.save(new PointHistory(
                        transferForm.getDealType(),
                        transferForm.getDealAmount(),
                        findUserPoint - transferForm.getDealAmount(),
                        findUser
                ));
                log.info("\n{}의 포인트 환불 내역 = {}", findUser, pointHistory);
            }
        }
        else {
            if(transferForm.getDealAmount() > findUserPoint){
                log.info("\n실패한 사용 요청 {}", transferForm);
                throw new NoMoneyException("보유한 포인트량을 초과해서 사용할 수 없습니다");
            } else {
                PointHistory pointHistory = pointHistoryRepository.save(new PointHistory(
                        transferForm.getDealType(),
                        transferForm.getDealAmount(),
                        findUserPoint - transferForm.getDealAmount(),
                        findUser
                ));
                log.info("\n{}의 포인트 사용 내역 = {}", findUser, pointHistory);
            }
        }
    }
}
