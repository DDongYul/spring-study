package hello.core.common;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.UUID;

@Component
@Scope(value = "request") //request 스코프 (HTTP request가 들어오고 나갈때까지가 생명주기)
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
