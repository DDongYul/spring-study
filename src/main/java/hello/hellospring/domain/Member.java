package hello.hellospring.domain;

import javax.persistence.*;

//jpa가 관리하는 entitiy가 됨
@Entity
public class Member {

    @Id @GeneratedValue(strategy =  GenerationType.IDENTITY)    //DB가 아이디 알아서 생성
    private long id;

    //@Column(name = "username")
    private String name;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
