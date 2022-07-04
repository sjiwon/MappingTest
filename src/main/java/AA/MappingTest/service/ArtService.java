package AA.MappingTest.service;

import AA.MappingTest.domain.Art;
import AA.MappingTest.repository.ArtRepository;
import AA.MappingTest.service.DTO.ArtEditForm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ArtService {

    private final ArtRepository artRepository;

    @Transactional
    // 작품 등록
    public Art registerArt(Art art){
        Art saveArt = artRepository.save(art);
        log.info("{} 가 등록한 작품 = {}", saveArt.getUser(), saveArt);
        return saveArt;
    }

    @Transactional
    // 작품 정보 수정(설명)
    public void editArt(Long id, ArtEditForm editArt){
        log.info("수정할 Art 정보 = {}", editArt);

        Art findArt = artRepository.findById(id).orElseThrow();
        log.info("정보 수정 Art = {}", findArt);

        findArt.setDecription(editArt.getDescription());

        log.info("수정된 Art 정보 = {}", findArt);
    }
}
