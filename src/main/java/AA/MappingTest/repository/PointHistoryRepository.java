package AA.MappingTest.repository;

import AA.MappingTest.domain.PointHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PointHistoryRepository extends JpaRepository<PointHistory, Long> {

    // [user_id]의 현재 포인트량 조회
    @Query("select ph from PointHistory ph join fetch ph.user where ph.user.id = :id")
    List<PointHistory> findPointHistoryByUserId(@Param("id") Long id);
}
