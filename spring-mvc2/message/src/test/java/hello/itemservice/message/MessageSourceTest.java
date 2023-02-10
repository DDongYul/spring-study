package hello.itemservice.message;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;

import java.util.Locale;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
public class MessageSourceTest {

    @Autowired
    MessageSource ms;

    //국제화 디폴트
    @Test
    void helloMessage(){
        String result = ms.getMessage("hello",null,null);
        assertThat(result).isEqualTo("안녕");
    }

    //국제화 나라 설정
    @Test
    void helloMessageEn(){
        String result = ms.getMessage("hello",null, Locale.ENGLISH);
        assertThat(result).isEqualTo("hello");
    }

    //메시지 설정 안돼있을 때 오류
    @Test
    void notFoundMessageCode() {
        assertThatThrownBy(() -> ms.getMessage("no_code", null, null))
                .isInstanceOf((NoSuchMessageException.class));
    }

    //설정이 안돼있으면 default message 설정 가능
    @Test
    void notFoundMessageCodeDefaultMessage() {
        assertThat(ms.getMessage("no_code", null, "기본 메시지", null)).isEqualTo("기본 메시지");
    }

    //파라미터가 있는 메시지
    @Test
    void argumentMessage() {
        String message = ms.getMessage("hello.name", new Object[]{"Spring"}, null);
        assertThat(message).isEqualTo("안녕 Spring");
    }

    //default 나라
    @Test
    void defaultLang(){
        assertThat(ms.getMessage("hello",null, null)).isEqualTo("안녕");
        assertThat(ms.getMessage("hello",null, Locale.KOREA)).isEqualTo("안녕");
    }

    //영어
    @Test
    void enLang(){
        assertThat(ms.getMessage("hello",null, Locale.ENGLISH)).isEqualTo("hello");
    }
}
