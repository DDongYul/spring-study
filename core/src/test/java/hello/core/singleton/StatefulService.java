package hello.core.singleton;

//싱글톤이 상태 유지에 관한 테스트를 위해 만든 클래스
//싱글톤 문제가 발생하는 클래스
//public class StatefulService {
//
//    private int price; //상태를 유지하는 필드
//
//    public void order(String name, int price) {
//        System.out.println("name = " + name + " price= " + price);
//        this.price = price; // 문제 발생
//    }
//
//    public int getPrice() {
//        return price;
//    }
//}

//싱글톤 문제 해결(지역 변수만 사용)
public class StatefulService {

    public int order(String name, int price) {
        System.out.println("name = " + name + " price= " + price);
        return price;
    }
}

