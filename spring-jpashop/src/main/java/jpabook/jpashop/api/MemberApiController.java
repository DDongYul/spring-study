package jpabook.jpashop.api;

import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.service.MemberService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class MemberApiController {

    private final MemberService memberService;

//    //api 직접 반환하면 절대 안됨!!!!!!!!!!!!
//    @GetMapping("api/v1/members")
//    public List<Member> membersV1(){
//        return memberService.findMembers();
//    }

    @GetMapping("/members")
    public MemberList membersV2(){
        List<Member> findMembers = memberService.findMembers();
        List<MemberDTO> collect = findMembers.stream()
                .map(member -> new MemberDTO(member.getName(),member.getAddress()))
                .collect(Collectors.toList());

        return new MemberList(collect);

    }

    @Data
    @AllArgsConstructor
    static class MemberList<T>{
        private T data;
    }

    @Data
    @AllArgsConstructor
    static class MemberDTO{
        private String name;
        private Address address;
    }

//    //Entity가 변경되면 Api 자체가 바뀌어야 하고 부가적인 문제가 많음 파라미터로 엔티티 그대로 받는건 안좋음
//    @PostMapping("/api/v1/members")
//    public CreateMemberResponse saveMemberV1(@RequestBody @Valid Member member) {
//        Long id = memberService.join(member);
//        return new CreateMemberResponse(id);    //객체반환(@RestController) -> 객체의 내용을 MessageBody에 직접 담음
//    }

    @PostMapping("/members")
    public CreateMemberResponse saveMemberV2(@RequestBody @Valid CreateMemberRequest request) {
        Member member = new Member();
        member.setName(request.getName());
        member.setAddress(request.getAddress());

        Long id = memberService.join(member);
        return new CreateMemberResponse(id);
    }

    @PutMapping("/members/{id}")
    public UpdateMemberResponse updateMemberV2(
            @PathVariable("id") Long id,
            @RequestBody @Valid UpdateMemberRequest request){

        memberService.update(id, request.getName(),request.getAddress());
        Member findMember = memberService.findOne(id);

        return new UpdateMemberResponse(findMember.getId(),findMember.getName(),findMember.getAddress());
    }





    @Data
    @AllArgsConstructor
    static class UpdateMemberResponse{
        private Long id;
        private String name;
        private Address address;
    }

    @Data
    static class UpdateMemberRequest{
        private String name;
        private Address address;
    }

    //API 스펙 내용 명확히하고 검증도 여기서 할 수 있음
    @Data
    static class CreateMemberRequest{

        @NotEmpty
        private String name;
        private Address address;
    }

    @Data
    static class CreateMemberResponse{
        private Long id;

        public CreateMemberResponse(Long id) {
            this.id = id;
        }
    }
}
