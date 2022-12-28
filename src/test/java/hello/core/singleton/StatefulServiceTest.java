package hello.core.singleton;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

//StatefulService에서 두개의 order가 들어왔을 때 상태 변화 테스트
class StatefulServiceTest {

    //싱글톤 문제 발생 테스트
//    @Test
//    void statefulServiceSingleton() {
//        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(TestConfig.class);
//        StatefulService statefulService1 = ac.getBean(StatefulService.class);
//        StatefulService statefulService2 = ac.getBean(StatefulService.class);
//
//        //ThreadA : A사용자 10000원 주문
//        statefulService1.order("userA", 10000);
//        //ThreadB : A사용자 10000원 주문
//        statefulService2.order("userB", 20000);
//
//        int price = statefulService1.getPrice();
//        System.out.println("price= " + price);
//
//        assertThat(statefulService1.getPrice()).isEqualTo(20000);
//    }

    //싱글톤 문제 해결 테스트
    @Test
    void statefulServiceSingleton() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(TestConfig.class);
        StatefulService statefulService1 = ac.getBean(StatefulService.class);
        StatefulService statefulService2 = ac.getBean(StatefulService.class);

        //ThreadA : A사용자 10000원 주문
        int priceA = statefulService1.order("userA", 10000);
        //ThreadB : A사용자 10000원 주문
        int priceB = statefulService2.order("userB", 20000);

        assertThat(priceA).isEqualTo(10000);
    }

    static class TestConfig{

        @Bean
        public StatefulService statefulService(){
            return new StatefulService();
        }
    }
}