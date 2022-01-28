package study.datajpa.entity;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED) // 생성자
@ToString(of = {"id", "username", "age"})
@NamedQuery(
        name = "Member.findByusername",
        query = "select m from Member m where m.username = :username"
) // NamedQuery 장점 : WAS 시동시 버그를 SEARCH 가능
@NamedEntityGraph(name = "Member.all", attributeNodes = @NamedAttributeNode("team"))
public class Member extends JpaBaseEntity {

    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;
    private String username;
    private int age;

    @ManyToOne(fetch = FetchType.LAZY) // 실무에서도 역시 LAZY 설정으로 사용
    @JoinColumn(name = "team_id")
    private Team team;

//    // JPA entity 기본 생성자 --> protected로 생성
//    protected Member() {
//    }

    public Member(String username) {
        this.username = username;
    }

    public Member(String username, int age, Team team) {
        this.username = username;
        this.age = age;
        if (team != null) {
            changeTeam(team);
        }
    }

    public Member(String username, int age) {
        this.username = username;
        this.age = age;
    }

    public void changeTeam(Team team) {
        this.team = team;
        team.getMembers().add(this);
    }
}
