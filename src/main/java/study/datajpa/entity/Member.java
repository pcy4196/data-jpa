package study.datajpa.entity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter @Setter
public class Member {

    @Id @GeneratedValue
    private Long id;
    private String username;

    // JPA entity 기본 생성자 --> protected로 생성
    protected Member() {
    }

    public Member(String username) {
        this.username = username;
    }
}
