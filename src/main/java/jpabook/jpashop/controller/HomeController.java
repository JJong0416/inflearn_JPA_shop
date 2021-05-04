package jpabook.jpashop.controller;


import lombok.extern.slf4j.Slf4j;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@Slf4j //Logger log = LoggerFactory.getLogger(getClass()); 랑 같은 거
public class HomeController {
    @RequestMapping("/") // Home 화면 첫번째
    public String home(){
        log.info("home controller");
        return "home";
    }
}
