package hello.core;

import hello.core.discount.DiscountPolicy;
import hello.core.discount.FixDiscountPolicy;
import hello.core.discount.RateDiscountPolicy;
import hello.core.member.MemberRepository;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;
import hello.core.member.MemoryMemberRepository;
import hello.core.order.OrderService;
import hello.core.order.OrderServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//앱에대한 환경설정을 여기서 다해준다! (디비,할인정책 뭐쓸지)
@Configuration
public class AppConfig {

    @Bean
    public MemberService memberService(){
        return new MemberServiceImpl(MemberRepository());   //멤버 서비스는 변경이 있어도 코드가 수정되지 않음
    }

    @Bean
    public MemberRepository MemberRepository() {
        return new MemoryMemberRepository();    //리포지토리가 바뀌면 이 부분만 수정하면 변경 완료
    }

    @Bean
    public OrderService orderService(){
        return new OrderServiceImpl(MemberRepository(),discountPolicy());   //오더 서비스는 변경이 있어도 코드가 수정되지 않음!
    }

    @Bean
    public DiscountPolicy discountPolicy(){
        //return new FixDiscountPolicy();
        return new RateDiscountPolicy();     //할인 정책이 바뀌면 이 부분만 수정하면 변경 완료
    }
}
