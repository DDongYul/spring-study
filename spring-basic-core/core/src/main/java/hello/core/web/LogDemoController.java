package hello.core.web;

import hello.core.common.MyLogger;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequiredArgsConstructor    //생성자 주입 자동으로 해
public class LogDemoController {

    private final LogDemoService logDemoService;
    private final MyLogger myLogger;
//    private final ObjectProvider<MyLogger> myLoggerProvider;

    @RequestMapping("log-demo")
    @ResponseBody
    public String logDemo(HttpServletRequest request) throws InterruptedException {
        String requestURL = request.getRequestURL().toString();
//        MyLogger myLogger = myLoggerProvider.getObject();   //provider로 필요한 시점에 주입
        System.out.println("myLogger = " + myLogger.getClass()); //출력해보면 가짜 프록시 객체를 만들어 주입됨을 알수있음
        myLogger.setRequestURL(requestURL);

        myLogger.log("controller test");
        Thread.sleep(1000); //동시에 들어오는 상황이라 가정하기 위
        logDemoService.logic("testId");
        return "OK";
    }
}
