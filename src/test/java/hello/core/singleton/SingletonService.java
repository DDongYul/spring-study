package hello.core.singleton;

public class SingletonService {

    //private: 외부 접근 불가 , static: 한번 메모리에 할당되면 종료때 까지 유지(객체를 생성해도 값이 새로 할당되지 않음) , final: 종료시까지 값 고정
    // static final을 통해 변하지 않는값을 static을 사용하여 종료시까지 유지하겠다는 선언(한번 생성된 instance를 계속 사용하겠다는 뜻!!)
    private static final SingletonService instance = new SingletonService();

    public static SingletonService getInstance() {
        return instance;
    }

    //생성자를 Private!! :외부에서 이 클래스를 접근하지 못함!! 오직 getInstance를 통해서만 객체를 받을 수 있음(하나의 instance 반환)
    //외부에서 new 사용해서 생성하려 그러면 컴파일 오류 뜸
    private SingletonService(){
    }

    public void logic(){
        System.out.println("싱글톤 객체 로직 호출");
    }
}
