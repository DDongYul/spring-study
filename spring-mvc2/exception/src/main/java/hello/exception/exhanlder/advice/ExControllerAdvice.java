package hello.exception.exhanlder.advice;

import hello.exception.exception.UserException;
import hello.exception.exhanlder.ErrorResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ExControllerAdvice {
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    public ErrorResult illegalExHandler(IllegalArgumentException e) {
        log.error("[exceptionHandler] ex", e);
        return new ErrorResult("BAD", e.getMessage());
    }   //컨트롤러 내부에서 에러가 발생하면 이 로직 호출, RestController기 때문에 그대로 JSON return ,
    // 정상흐름으로 처리->400으로 뜨게 하고 싶으면 @ResponseStatus로 상태코드 바꿔줘여함

    @ExceptionHandler //(아래 파라미터로 UserException들어와서 생략 가능)
    public ResponseEntity<ErrorResult> UserExHandler(UserException e){
        log.error("[exceptionHandler] ex", e);
        ErrorResult errorResult = new ErrorResult("User-EX", e.getMessage());
        return new ResponseEntity(errorResult, HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler
    public ErrorResult exHandler(Exception e){  //exception이 제일 부모여서 놓친 에러들을 잡아 (여기서는 ReuntimeException이 처리 안됨)
        log.error("[exceptionHandler] ex", e);
        return new ErrorResult("EX", "내부오류");
    }
}

