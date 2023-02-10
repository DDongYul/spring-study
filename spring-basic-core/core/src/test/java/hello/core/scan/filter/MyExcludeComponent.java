package hello.core.scan.filter;

import java.lang.annotation.*;
//컴포넌트 스캔에서 제외
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MyExcludeComponent {
    
}
