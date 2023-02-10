package hello.hellospring.controller;

import hello.hellospring.domain.Member;
import hello.hellospring.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class MemberController {

    private final MemberService memberService;

//    @Autowired private MemberService memberService;   //필드주입 , setter주입도 있는데 둘다  쓸일 거의 없음
    //필드는 한번 선언하면 바꿀수가 없고, setter는 public으로 노출된다는 단점

    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }   //생성자 주입
    //스프링 컨터이너에서 memberService 꺼내와서 매칭시켜줌

    @GetMapping("/members/new")     //url넘길떄 (조회할 떄)
    public String createForm(){
        return "members/createMemberForm";
    }

    @PostMapping("/members/new")    //데이터 넘길떄(폼형식) 주로 사용
    public String create(MemberForm form){
        Member member = new Member();
        member.setName(form.getName());

        memberService.join(member);

        return  "redirect:/";       //회원가입을 마치고 홈 화면으로 보냄
    }

    @GetMapping("/members")
    public String list(Model model){
        List<Member> members = memberService.findMembers();
        model.addAttribute("members",members);
        return "members/memberList";

    }
}
