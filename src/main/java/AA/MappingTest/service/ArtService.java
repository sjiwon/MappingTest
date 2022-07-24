package AA.MappingTest.service;

import AA.MappingTest.domain.Art;
import AA.MappingTest.repository.ArtRepository;
import AA.MappingTest.service.DTO.ArtEditDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ArtService {

    private final ArtRepository artRepository;

    @Transactional
    // 작품 등록
    public Art registerArt(Art art){
        Art saveArt = artRepository.save(art);
        log.info("\n{} 가 등록한 작품 = {}", saveArt.getUser(), saveArt);
        return saveArt;
    }

    @Transactional
    // 작품 정보 수정(설명)
    public void editArt(Long id, ArtEditDTO editArt){
        log.info("\n수정할 정보(설명) = {}", editArt);

        Art findArt = artRepository.findById(id).orElseThrow();
        log.info("\n수정할 Art = {}", findArt);

        findArt.changeDescription(editArt.getDescription());

        log.info("\n수정된 Art 정보 = {}", findArt);
    }

    // 작품 판매 타입 (경매/일반)
    public String getSaleType(Long id){
        String type = artRepository.isAuctionOrGeneral(id).name();
        log.info("\n{}의 판매 타입 = {}", id, type);
        return type;
    }

    // 특정 회원의 작품리스트 검색
    public List<Art> artListFromUserId(Long id){
        List<Art> artListByUserId = artRepository.findArtListByUserId(id);
        log.info("\n=== user_id : {}의 작품 리스트 ===", id);
        for (Art art : artListByUserId) {
            System.out.println(art);
        }
        return artListByUserId;
    }
}
