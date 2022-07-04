package AA.MappingTest.repository;

import AA.MappingTest.domain.AuctionHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuctionHistoryRepository extends JpaRepository<AuctionHistory, Long> {
}
