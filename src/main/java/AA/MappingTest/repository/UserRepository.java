package AA.MappingTest.repository;

import AA.MappingTest.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository extends JpaRepository<Users, Long> {

    // loginId로 password 찾기
    @Query("select u.loginPassword from Users u where u.loginId = :loginId")
    String findPasswordByLoginId(@Param("loginId") String loginId);

    // [user_id]로 소속 학교 찾기
    @Query("select u.schoolName from Users u where u.id = :id")
    String findSchoolByUserId(@Param("id") Long id);
}
