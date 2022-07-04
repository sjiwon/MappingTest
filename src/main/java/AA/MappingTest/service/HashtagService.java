package AA.MappingTest.service;

import AA.MappingTest.domain.Hashtag;
import AA.MappingTest.repository.HashtagRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class HashtagService {
    private final HashtagRepository hashtagRepository;

    @Transactional
    // 해시태그 등록
    public Hashtag registerHashTag(Hashtag hashtag){
        Hashtag saveHashtag = hashtagRepository.save(hashtag);
        log.info("등록된 해시태그 = {}", saveHashtag);
        return saveHashtag;
    }

    @Transactional
    // ID로 해시태그이름 찾기
    public Optional<Hashtag> findHashtagById(Long id){
        return hashtagRepository.findById(id);
    }
}