package hello.login.web.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Slf4j
public class LogInterceptor implements HandlerInterceptor {

    public static final String LOG_ID = "logId";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String requestURI = request.getRequestURI();
        String uuid = UUID.randomUUID().toString();

        request.setAttribute(LOG_ID,uuid); //afterCompletion에서도 uuid를 사용할 수 있게 request에 넣어둠

        //타입에 따라 넘어오는게 달라서 잘 케스팅 해줘야함
        //@RequestMapping, @Controller: HandlerMethod
        //정적 리소스: ResourceHttpRequestHandler
        if(handler instanceof HandlerMethod){   //@RequestMapping
            HandlerMethod hm = (HandlerMethod) handler;//호출할 컨트롤러 메서드의 모든 정보가 포함되어 있다.
            //hm.get~ 을 사용하면 원하는 정보 꺼내쓸 수 있다.
        }

        log.info("REQUEST [{}][{}][{}]",uuid,requestURI,handler);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

        log.info("REQUEST [{}]",modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        String requestURI = request.getRequestURI();
        String uuid = (String)request.getAttribute(LOG_ID); //preHandle에서 만든 logId(uuid) 받음

        log.info("REQUEST [{}][{}][{}]",uuid,requestURI,handler);
        if (ex != null) {
            log.error("afterCompletion error!!", ex);
        }
    }
}
