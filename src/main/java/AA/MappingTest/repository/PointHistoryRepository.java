package AA.MappingTest.repository;

import AA.MappingTest.domain.PointHistory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PointHistoryRepository extends JpaRepository<PointHistory, Long> {

    // [user_id]의 포인트 내역 정보 조회
    @Query("select ph from PointHistory ph where ph.user.id=:id order by ph.point desc")
    List<PointHistory> findPointHistoryByUserId(@Param("id") Long id);
}
