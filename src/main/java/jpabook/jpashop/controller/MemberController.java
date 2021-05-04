package jpabook.jpashop.controller;

import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @GetMapping("/members/new")
    public String createForm(Model model){ // 모델이라고 하면
        model.addAttribute("memberForm",new MemberForm()); //컨트롤러에서 뷰로 넘어갈 때 데이터를 싣는다 MemberForm이라는 빈 껍데기를 가져간다.
        return "members/createMemberForm"; // 이제 이 members/createMemberForm.html로 작업해야한다.
    }

    @PostMapping("/members/new")
    public String create(@Valid MemberForm form, BindingResult result){
        // 벨리데이션 해준다는 어노테이션 + BindingResult 를 통해서 오류가 발생 시, 오류룰 result에 담아서 실행해줌.
        // BindingResult가 form에 들고와서 다 쓸 수 있게 해줌.

        if (result.hasErrors()){
            return "members/createMemberForm";
        }

        Address address = new Address(form.getCity(), form.getStreet(), form.getZipcode());

        Member member = new Member();
        member.setName(form.getName());
        member.setAddress(address);

        memberService.join(member);
        return "redirect:/"; // 저장이 끝나면 뒤로 보내자! 즉 첫번째 페이지로 넘긴다는 뜻이다.
    }
            // Entity는 최대한 순수하게 유지하고 DTO를 주로 사용하자.
    @GetMapping("/members")
    public String list(Model model){
        List<Member> members = memberService.findMembers();
        model.addAttribute("members",members);
        return "members/memberList";
    }
}