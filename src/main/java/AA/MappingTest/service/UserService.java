package AA.MappingTest.service;

import AA.MappingTest.domain.*;
import AA.MappingTest.repository.PointHistoryRepository;
import AA.MappingTest.repository.UserRepository;
import AA.MappingTest.service.DTO.UserEditForm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PointHistoryRepository pointHistoryRepository;

    @Transactional
    // 1. 회원가입
    public Users joinUser(Users user){
        Users joinUser = userRepository.save(user);
        PointHistory pointHistory = pointHistoryRepository.save(new PointHistory(joinUser));

        log.info("회원가입 User = {}", joinUser);
        log.info("PointInstance 자동 생성(JOIN) = {} / 생성된 user = {}", pointHistory, pointHistory.getUser());

        return joinUser;
    }

    @Transactional
    // 2. 회원 정보 수정
    public void editUser(Long id, UserEditForm editUser){
        log.info("수정할 User 정보 = {}", editUser);

        Users findUser = userRepository.findById(id).orElseThrow();
        log.info("정보 수정 User = {}", findUser);

        if(StringUtils.hasText(editUser.getNickname())) {
            findUser.setNickname(editUser.getNickname());
        }

        if(StringUtils.hasText(editUser.getPhoneNumber())) {
            findUser.setPhoneNumber(editUser.getPhoneNumber());
        }

        if(StringUtils.hasText(editUser.getAddress())) {
            findUser.setAddress(editUser.getAddress());
        }

        log.info("수정된 User 정보 = {}", findUser);
    }

    // 3. user_id로 회원 찾기
    public Optional<Users> findUserByLoginId(Long id){
        Optional<Users> findUser = userRepository.findById(id);
        log.info("user_id로 찾은 회원 = {}", findUser);
        return findUser;
    }

    // 4. 아이디로 비밀번호 찾기
    public String findPasswordByLoginId(String loginId){
        String passwordByLoginId = userRepository.findPasswordByLoginId(loginId);
        log.info("{}로 찾은 비밀번호 = {}", loginId, passwordByLoginId);
        return passwordByLoginId;
    }

    // 5. 소속 학교 찾기
    public String findSchoolById(Long id){
        String schoolName = Objects.requireNonNull(userRepository.findById(id).orElse(null)).getSchoolName();
        log.info("{}의 소속 학교 = {}", id, schoolName);
        return schoolName;
    }

    // 6. 해당 User의 작품들 조회
    public List<Art> artList(Long id){
        List<Art> artCollections = userRepository.findArtCollections(id);
        log.info("{}의 작품 리스트 = {}", id, artCollections);
        return artCollections;
    }

    // 7. 참여한 경매 내역 확인
    public List<AuctionHistory> auctionHistoryList(Long id){
        List<AuctionHistory> participatedAuction = userRepository.findParticipatedAuction(id);
        log.info("{}의 참여 경매 내역 = {}", id, participatedAuction);
        return participatedAuction;
    }

    // 8. 구매내역 확인
    public List<PurchaseHistory> purchaseHistoryList(Long id){
        List<PurchaseHistory> purchasedHistory = userRepository.findPurchasedHistory(id);
        log.info("{}의 구매내역 = {}", id, purchasedHistory);
        return purchasedHistory;
    }

    // 9. 작품찜 내역 확인
    public List<LikeArt> likeArtList(Long id){
        List<LikeArt> likedArt = userRepository.findLikedArt(id);
        log.info("{}의 작품찜 내역 = {}", id, likedArt);
        return likedArt;
    }

    // 10. 작가찜 내역 확인
    public List<Users> likeArtistList(Long id){
        List<Users> likedArtistList = new ArrayList<>();

        List<LikeArtist> likeArtistList = userRepository.findLikedArtist(id);
        log.info("{}의 작가찜 내역 = {}", id, likeArtistList);
        for (LikeArtist likeArtist : likeArtistList) {
            Users likedArtist = userRepository.findById(likeArtist.getArtist().getId()).orElse(null);
            log.info("찜당한 작가 = {}", likedArtist);
            likedArtistList.add(likedArtist);
        }

        log.info("찜된 작가 목록 = {}", likedArtistList);

        return likedArtistList;
    }

    // 11. 포인트 사용내역 확인


    // 12. 포인트 충전


}
