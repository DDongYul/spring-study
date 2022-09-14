package hello.hellospring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloController {
    @GetMapping("hello")
    public String hello(Model model) {
        model.addAttribute("data", "hello!!");  //model(name:value) 리턴 (hello.html에서 꺼내 쓸수있음)
        return "hello"; //resources/templates/(ViewName 여기서는 hello).html을 의미
    }

    @GetMapping("hello-mvc")
    public String helloMvc(@RequestParam(value = "name") String name, Model model){
        model.addAttribute("name",name);
        return "hello-template";        //html을 변환한 소스가 나옴
    }

    //Api방식
    @GetMapping("hello-string")
    @ResponseBody       //http body부분에 직접 내용을 넣어주겠다는 뜻
    public String helloString(@RequestParam("name") String name){
        return "hello" + name;  //소스를 보면 html관련 내용 없이 문자만 나옴 (쓸일 거의 없음)
    }

    @GetMapping("hello-api")
    @ResponseBody
    public Hello helloApi(@RequestParam("name") String name){
        Hello hello = new Hello();
        hello.setName(name);
        return hello;       //객체 넘김 (소스 JSON방식)
    }

    static class Hello{
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
