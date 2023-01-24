package hello.springmvc.basic.request;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@Slf4j
@Controller
public class RequestParamController {

    @RequestMapping("/request-param-v1")
    public void requestParamV1(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String username = request.getParameter("username");
        int age = Integer.parseInt(request.getParameter("age"));
        log.info("username={}, age={}", username, age);

        response.getWriter().write("ok");
    }

    @ResponseBody
    @RequestMapping("/request-param-v2")
    public String requestParamV2(
            @RequestParam("username") String memberName,
            @RequestParam("age") int memberAge
    ){
        log.info("username={}, age={}", memberName, memberAge);
        return "ok";
    }

    //생략하려면 변수명과 파라미터가 동일해야함
    @ResponseBody
    @RequestMapping("/request-param-v3")
    public String requestParamV3(
            @RequestParam String username,
            @RequestParam int age
    ){
        log.info("username={}, age={}", username, age);
        return "ok";
    }

    //String int Inteager등의 단 타입이면 @RequestParam도 생략가능
    @ResponseBody
    @RequestMapping("/request-param-v4")
    public String requestParamV4(String username,int age){
        log.info("username={}, age={}", username, age);
        return "ok";
    }

    //필수 항목을 설정할 수 있고 이 값이 안들어오면 에러가 남
    @ResponseBody
    @RequestMapping("/request-param-required")
    public String requestParamRequired(
            @RequestParam(required = true) String username, //null과 ""은 다른값임! 값을 안넣으면 ""이 들어옴
            @RequestParam(required = false) Integer age){   //int를 사용하면 null값을 넣을 수 없음.
                                                            // 값이 안들어오면 null 값을 넣어줘야 하기 떄문에 Inteager사용
        log.info("username={}, age={}", username, age);
        return "ok";
    }

    @ResponseBody
    @RequestMapping("/request-param-default")
    public String requestParamDefault(
            @RequestParam(required = true, defaultValue = "guest") String username, //default값이 있기 때문에 required는 사실상 필요없
            @RequestParam(required = false, defaultValue = "-1") int age){   //값이 안들어오면 -1이기 때문에 int 사용가능
                                                                            //빈문자 ""도 default로 처리
        log.info("username={}, age={}", username, age);
        return "ok";
    }

    //파라미터 값이 1개가 화길하면 Map을 사용해도 되지만 그렇지 않으면 MultiValueMap을 사용(보통은 1개)
    @ResponseBody
    @RequestMapping("/request-param-map")
    public String requestParamMap(@RequestParam Map<String,Object> paramMap){
        log.info("username={}, age={}", paramMap.get("username"), paramMap.get("age"));
        return "ok";
    }


}
