package jpabook.jpashop;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HelloController {

    @GetMapping("hello")
    public String hello(Model model){ // 모델은 데이타를 넣어서 컨트롤러에서 뷰에 넘길 수 있는 역할
        model.addAttribute("data", "helllo!!!"); // name이 data라는 곳에 hello를 넘긴다
        return "hello"; // 리턴은 화면 이름이다!! .html이 자동으로 붙는다
        // 즉 리턴값을 통해 resource -> templates까지 자기가 알아서 찾아간다.
        // 이게 타임리프가 viewName을 매핑해주는 것이다!

    }
}
