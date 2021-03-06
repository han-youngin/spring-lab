package site.metacoding.dbproject.web;

import java.security.Principal;
import java.sql.PreparedStatement;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.websocket.Session;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import site.metacoding.dbproject.domain.user.User;
import site.metacoding.dbproject.domain.user.UserRepository;

@Controller
public class UserController {

    // 컴퍼지션 (의존성 연결)
    private UserRepository userRepository;
    private HttpSession session;

    // DI 받는 코드!!
    public UserController(UserRepository userRepository, HttpSession session) {
        this.userRepository = userRepository;
        this.session = session;
    }

    // 회원가입 페이지 (정적) - 로그인X
    @GetMapping("/joinForm")
    public String joinForm() {
        return "user/joinForm";
    }

    // username=ssar&password=1234&email=ssar@nate.com (x-www-form)
    // 회원가입 - 로그인X
    @PostMapping("/join")
    public String join(User user) {

        StringBuffer sb = new StringBuffer();
        sb.append("<script>");
        sb.append("alert(값을 제대로 전달받지 못했습니다.);");
        sb.append("history.back()';");

        sb.append("</script>");
        // 1.username,password,email 1.null 체크 2.공백체크
        if (user.getUsername() == null || user.getPassword() == null || user.getEmail() == null) {
            return sb.toString();
        }
        System.out.println("user : " + user);
        User userEntity = userRepository.save(user);
        System.out.println("userEntity : " + userEntity);
        // redirect:매핑주소
        return "redirect:/loginForm"; // 로그인페이지 이동해주는 컨트롤러 메서드를 재활용
        // 여기 다시
    }

    // 로그인 페이지 (정적) - 로그인X
    @GetMapping("/loginForm")
    public String loginForm() {
        return "user/loginForm";
    }

    // SELECT * FROM user WHERE username=? AND password=?
    // 원래 SELECT 는 무조건 get요청
    // 그런데 로그인만 예외 (POST)
    // 이유 : 주소에 패스워드를 남길 수 없으니까!!
    // 로그인 - - 로그인X
    @PostMapping("/login")
    public String login(HttpServletRequest request, User user) {
        HttpSession session = request.getSession(); // 쿠키에 sessionId : 85

        User userEntity = userRepository.mLogin(user.getUsername(), user.getPassword());

        if (userEntity == null) {
            System.out.println("아이디 혹은 패스워드가 틀렸습니다");
        } else {
            System.out.println("로그인 되었습니다");
            session.setAttribute("principal", userEntity);
        }
        // 1. DB연결해서 username, password 있는지 확인
        // 2. 있으면 session 영역에 인증됨 이라고 메시지 하나 넣어두자.
        return "redirect:/"; // PostController 만들고 수정하자.
    }

    // http://localhost:8080/user/1
    // 유저상세 페이지 (동적) - 로그인O
    @GetMapping("/user/{id}")
    public String detail(@PathVariable Integer id, Model model) {
        User principal = (User) session.getAttribute("principal");

        // 1. 인증 체크
        if (principal == null) {
            return "error/page1";
        }

        // 2. 권한체크
        if (principal.getId() != id) {
            return "error/page1";
        }

        // 3. 핵심로직
        Optional<User> userOp = userRepository.findById(id);

        if (userOp.isPresent()) {
            User userEntity = userOp.get();
            model.addAttribute("user", userEntity);
            return "user/detail";
        } else {
            return "error/page1";
        }
    }

    // 유저수정 페이지 (동적) - 로그인O
    @GetMapping("/user/{id}/updateForm")
    public String updateForm() {
        return "user/updateForm";
    }

    // 유저수정 - 로그인O
    @PutMapping("/user/{id}")
    public String update(@PathVariable Integer id) {
        return "redirect:/user/" + id;
    }

    // 로그아웃 - 로그인O
    @GetMapping("/logout")
    public String logout() {
        return "메인페이지를 돌려주면 됨"; // PostController 만들고 수정하자.
    }
}
