package AA.MappingTest.service;

import AA.MappingTest.domain.Hashtag;
import AA.MappingTest.repository.HashtagRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class HashtagService {
    private final HashtagRepository hashtagRepository;

    @Transactional
    // 해시태그 등록
    public Hashtag registerHashTag(Hashtag hashtag){
        Hashtag saveHashtag = hashtagRepository.save(hashtag);
        log.info("\n등록된 해시태그 = {}", saveHashtag);
        return saveHashtag;
    }

    // [hashtag_id]로 해시태그이름 찾기
    public Hashtag findHashtagById(Long hashtagId){
        return hashtagRepository.findById(hashtagId).orElseThrow();
    }
}
