package AA.MappingTest.repository;

import AA.MappingTest.domain.Art;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArtRepository extends JpaRepository<Art, Long> {
}
