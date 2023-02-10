package hello.core.order;

import hello.core.AutoAppConfig;
import hello.core.discount.DiscountPolicy;
import hello.core.discount.FixDiscountPolicy;
import hello.core.member.Grade;
import hello.core.member.Member;
import hello.core.member.MemberRepository;
import hello.core.member.MemoryMemberRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.junit.jupiter.api.Assertions.*;

class OrderServiceImplTest {

    //생성자 주입을 통해 만들어야 순수 자바 코드 테스트가 훨씬 용이함
    @Test
    void createOrder() {
//        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AutoAppConfig.class);
        MemberRepository memberRepository = new MemoryMemberRepository();
        memberRepository.save(new Member(1L,"동열", Grade.VIP));
        //세터주입을 사용하면 OrderServiceImpl을 만들때 생성자에 아무것도 넣지 않아, 어떻게 주입해야 하는지 알아보기 쉽지않음
        OrderServiceImpl orderService = new OrderServiceImpl(new MemoryMemberRepository(),new FixDiscountPolicy());
        Order itemA = orderService.createOrder(1L, "itemA", 10000);
        org.assertj.core.api.Assertions.assertThat(itemA.getDiscountPrice()).isEqualTo(1000);
    }

}