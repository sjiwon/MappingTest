package AA.MappingTest.repository;

import AA.MappingTest.domain.LikeArtist;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeArtistRepository extends JpaRepository<LikeArtist, Long> {
}
