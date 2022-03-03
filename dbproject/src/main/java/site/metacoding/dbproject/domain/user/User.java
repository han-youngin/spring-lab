package site.metacoding.dbproject.domain.user;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.UniqueConstraint;

//JPA 라이브러리는  Java(자바언어) Persistence(영구적인 저장) API(노출되어있는메서드)
// 1. CRUD 메서드를 기본 제공
// 2. 자바코드로 DB를 자동 생성 기능 제공
// 3. ORM 제공!! - 이 부분 지금 몰라도 됨

@Entity // 서버 실행시 해당 클래스로 테이블을 생성해!!
public class User {
    // IDENTITY 전략은 DB에게 번호증가 전략을 위임하는 것!! - 알아서 디비에 맞게 찾아줌
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id; // primary key

    @Column(length = 20, unique = true)
    private String username; // ssar 아이디
    @Column(length = 12, nullable = false)
    private String password;

    @Column(length = 160000)
    private String email;
    private String phone;

    private LocalDateTime createDate;
}