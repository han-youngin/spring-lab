package site.metacoding.second.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

//View (글쓰기 페이지, 글 목록 페이지, 글 상세보기 페이지)
@Controller
public class PostController {

    @GetMapping("/post/writeForm")
    public String writeForm() {
        return "post/writeForm";
    }

    @GetMapping("/post/writeForm")
    public String list() {
        return "post/list";
    }

    @GetMapping("/post/writeForm")
    public String detail() {
        return "post/detail";
    }

}
