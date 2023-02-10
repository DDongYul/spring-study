package hello.core.common;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.UUID;

@Component
//가짜 프록시로 싱글톤처럼 유지하면서 진짜 객체 조회를 꼭 필요한 시점까지 지연처리!
@Scope(value = "request",proxyMode = ScopedProxyMode.TARGET_CLASS)
public class MyLogger {

    private String uuid;
    private String requestURL;

    public void setRequestURL(String requestURL) {
        this.requestURL = requestURL;
    }

    public void log(String message) {
        System.out.println("[" + uuid + "]" + "[" + requestURL + "]" + message);
    } //포맷: [UUID][requestURL] {message}

    @PostConstruct
    public void init() {
        uuid = UUID.randomUUID().toString();    //유니크한 숫자 생성
        System.out.println("[" + uuid + "] request scope bean create:"+ this );
    }

    @PreDestroy
    public void close() {
        System.out.println("[" + uuid + "] request scope bean close:"+ this );
    }
}
