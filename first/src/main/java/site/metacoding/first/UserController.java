package site.metacoding.first;

import org.springframework.web.bind.annotation.RestController;

//UserController u = new UserController;

@RestController
public class UserController {
    public UserController() {
        System.out.println(" 생성자 생성됨 ");
    }

    public void home() {
        System.out.println("home~~~~~~");
    }

    public void bye() {
        System.out.println("bye~~~~~");
    }
}
