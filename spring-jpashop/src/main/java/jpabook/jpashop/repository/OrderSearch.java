package jpabook.jpashop.repository;

import jpabook.jpashop.domain.OrderStatus;
import lombok.Getter;

@Getter
public class OrderSearch {

    private String memberName;  //회원명
    private OrderStatus orderStatus;    //주문상 [ORDER,CANCEL]
}
