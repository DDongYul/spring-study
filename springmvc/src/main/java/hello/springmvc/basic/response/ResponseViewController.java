package hello.springmvc.basic.response;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ResponseViewController {

    @RequestMapping("/response-view-v1")
    public ModelAndView responseViewV1() {
        ModelAndView mav = new ModelAndView("response/hello")
                .addObject("data", "hello!");

        return mav;
    }

    //@ResponseBody -> 얘를 쓰면 return의 문자열이 그대로 출력
    @RequestMapping("/response-view-v2")
    public String responseViewV2(Model model) {
        model.addAttribute("data", "hello!");

        return "response/hello";    //뷰의 논리적 이름
    }

    @RequestMapping("/response/hello")  //뷰의 논리적 이름을 경로로하면 return생략 가능(권장하지 않음)
    public void responseViewV3(Model model) {
        model.addAttribute("data", "hello!");
    }
}