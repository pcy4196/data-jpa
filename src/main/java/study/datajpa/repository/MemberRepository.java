package study.datajpa.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import study.datajpa.dto.MemberDto;
import study.datajpa.entity.Member;

import javax.persistence.LockModeType;
import javax.persistence.QueryHint;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long>, MemberRepositoryCustom, JpaSpecificationExecutor<Member>{

    List<Member> findByUsernameAndAgeGreaterThan(String username, int age);

    List<Member> findTop3HelloBy();

    @Query(name = "Member.findByusername")
    List<Member> findByusername(@Param("username") String username);

    @Query("select m from Member m where m.username= :username and m.age = :age")
    List<Member> findUser(@Param("username") String username, @Param("age") int age);

    @Query("select m.username from Member m")
    List<String> findUsernameList();

    @Query("select new study.datajpa.dto.MemberDto(m.id, m.username, t.name) from Member m join m.team t")
    List<MemberDto> findMemeberDto();

    @Query("select m from Member m where m.username in :names")
    List<Member> findByNames(@Param("names") Collection<String> names);

    List<Member> findListByusername(String username);               // 컬렉션
    Member findMemberByusername(String username);                   // 단건
    Optional<Member> findOptionalByusername(String username);       // 단건 Optional

    @Query(value = "select m from Member m left join m.team t",
            countQuery = "select count (m.username) from Member m") // count 쿼리 성능을 높이기 위해 세팅
    Page<Member> findByAge(int age, Pageable pageable);

    @Modifying(clearAutomatically = true)  // .executeUpdate();, clearAutomatically --> flush(),clear()
    @Query("update Member m set m.age = m.age + 1 where m.age >= :age")
    int bulkAgePlus(@Param("age") int age);

    @Query("select m from Member m left join fetch m.team")
    List<Member> findMemberFetchJoin();

    @Override
    @EntityGraph(attributePaths = {"team"}) // fetchJoin
    List<Member> findAll();

    @EntityGraph(attributePaths = {"team"})
    @Query("select m from Member m")
    List<Member> findMemberEntityGraph();

//    @EntityGraph("Member.all")
    @EntityGraph(attributePaths = {"team"})
    List<Member> findEntityGraphByusername(@Param("username") String username);

    @QueryHints(value = @QueryHint(name = "org.hibernate.readOnly", value = "true")) // 변경감지 CHK X --> 조회성 성능 중심
    Member findReadOnlyByUsername(String username);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    List<Member> findLockByUsername(String username);

    List<UsernameOnly> findProjectionsByusername(@Param("username") String username);

    List<UsernameOnlyDto> findProjectionsDtoByusername(@Param("username") String username);

    <T> List<T> findProjectionsByUsername(String username, Class<T> type);
}
