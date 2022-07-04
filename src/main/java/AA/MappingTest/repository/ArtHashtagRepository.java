package AA.MappingTest.repository;

import AA.MappingTest.domain.ArtHashtag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArtHashtagRepository extends JpaRepository<ArtHashtag, Long> {
}
