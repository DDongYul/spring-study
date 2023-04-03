package jpabook.jpashop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
public class Delivery {

    @Id @GeneratedValue
    @Column(name="delivery_id")
    private Long id;

    @OneToOne(mappedBy = "delivery")
    private Order order;

    @Embedded
    private Address address;

    @Enumerated(EnumType.STRING)    //STRING으로 써야 중간에 데이터 들어와도 값 유지 가능(숫자로 하면 값 밀림)
    private DeliveryStatus status; //READY, COMP
}
