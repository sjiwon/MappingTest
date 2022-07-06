package AA.MappingTest.service;

import AA.MappingTest.domain.Art;
import AA.MappingTest.domain.ArtHashtag;
import AA.MappingTest.domain.Hashtag;
import AA.MappingTest.repository.ArtHashtagRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ArtHashtagService {
    
    private final ArtHashtagRepository artHashtagRepository;

    // (Art & Hashtag) 쌍 등록
    public ArtHashtag addHashtagToArt(Art art, Hashtag hashtag){
        ArtHashtag save = artHashtagRepository.save(new ArtHashtag(art, hashtag));
        log.info("\n(Art & Hashtag) Instance 생성 = {}", save);
        return save;
    }

    // [art_id]의 해시태그 목록 조회
    public List<ArtHashtag> getHashtagsFromArtId(Long id){
        return artHashtagRepository.findHashtagByArtId(id);
    }
}
