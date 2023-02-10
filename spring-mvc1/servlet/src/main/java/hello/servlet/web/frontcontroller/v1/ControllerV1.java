package hello.servlet.web.frontcontroller.v1;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface ControllerV1 {

    //service와 유사한 모양
    void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;
}
