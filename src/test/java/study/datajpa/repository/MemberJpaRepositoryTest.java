package study.datajpa.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import study.datajpa.entity.Member;

import static org.assertj.core.api.Assertions.assertThat;

// @RunWith(SpringRunner.class) Junit4 일때 사용하던 어노테이션 --> Junit5 x
@SpringBootTest
@Transactional
//@Rollback(false) // --> RollBack이 되지 않게 설정하여 TEST 데이터 확인 가능
class MemberJpaRepositoryTest {

    @Autowired MemberJpaRepository memberJpaRepository;

    @Test
    public void testMember() {
        Member member = new Member("memberA");
        Member savedmember = memberJpaRepository.save(member);

        Member findMember = memberJpaRepository.find(savedmember.getId());

        assertThat(findMember.getId()).isEqualTo(member.getId());
        assertThat(findMember.getUsername()).isEqualTo(member.getUsername());
        assertThat(findMember).isEqualTo(member);
    }
}