package hello.springmvc.basic;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController //간단한 테스트용으로 쓸 때 음
public class LogTestController {

//    private final Logger log = LoggerFactory.getLogger(getClass()); //lombok의 @Slf4j로 변경하면 그냥 쓸 수 있음

    @RequestMapping("/log-test")
    public String logTest() {
        String name = "Spring";
        System.out.println("name = " + name);

        log.trace("trace log={}", name);    //log.trace("trace log" + name)과 같이 쓰면 trace 이전에 문자열끼리 연산이 먼저 돼서 메모리 낭비!!
        log.debug("debug log={}",name);
        log.info("info log={}",name);
        log.warn("warn log={}",name);
        log.error("error log={}",name);


        return "ok";

    }
}
