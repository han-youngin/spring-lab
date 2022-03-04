package site.metacoding.dbproject.domain.post;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;

import org.apache.catalina.User;

// 테이블은 뭐다? 엔티티다
@Entity
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id; // primary key

    @Column(length = 100, nullable = false)
    private String title; // ssar 아이디

    @Lob // CLOB 4GB 문자 타입
    @Column(nullable = false)
    private String content;

    @JoinColumn(name = "userId")
    @ManyToOne
    private User user;

    private Integer userId;

    private LocalDateTime createDate;
}
