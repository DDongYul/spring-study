package jpabook.jpashop.api;

import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderItem;
import jpabook.jpashop.domain.OrderStatus;
import jpabook.jpashop.repository.OrderRepository;
import jpabook.jpashop.repository.OrderSearch;
import jpabook.jpashop.repository.order.query.OrderFlatDto;
import jpabook.jpashop.repository.order.query.OrderItemQueryDto;
import jpabook.jpashop.repository.order.query.OrderQueryDto;
import jpabook.jpashop.repository.order.query.OrderQueryRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.*;

@RestController
@RequiredArgsConstructor
public class OrderApiController {

    private final OrderRepository orderRepository;
    private final OrderQueryRepository orderQueryRepository;

    //엔티티 직접 노출 - 많은 문제점, 절대 하면 안됨
    @GetMapping("/api/v1/orders")
    public List<Order> ordersV1() {
        List<Order> orders = orderRepository.findAll(new OrderSearch());
        return orders;
    }

    //DTO로 변환하여 조회, 지연로딩 N+1 문제
    @GetMapping("/api/v2/orders")
    public List<OrderDto> ordersV2() {
        List<Order> orders = orderRepository.findAll(new OrderSearch());
        return orders.stream()
                .map(o -> new OrderDto(o))
                .collect(toList());
    }

    //fetch join 사용 -> 페이징 불가능!!
    @GetMapping("/api/v3/orders")
    public List<OrderDto> ordersV3() {
        List<Order> orders = orderRepository.findAllWithItem();

        return orders.stream()
                .map(o -> new OrderDto(o))
                .collect(toList());
    }

    /**
     * V3.1 엔티티를 조회해서 DTO로 변환 페이징 고려
     * ToOne 관계만 우선 모두 페치 조인으로 최적화
     * 컬렉션 관계는 hibernate.default_batch_fetch_size, @BatchSize로 최적화
     * In query문으로 N번 조회내용을 N/설정값 번으로 조회 횟수 줄임
     */
    @GetMapping("/api/v3.1/orders")
    public List<OrderDto> ordersV3_page(
            @RequestParam(value = "offset",defaultValue = "0") int offset,
            @RequestParam(value = "limit",defaultValue = "100")int limit)
    {

        List<Order> orders = orderRepository.findAllWithMemberDelivery(offset,limit);

        return orders.stream()
                .map(o -> new OrderDto(o))
                .collect(toList());
    }

    /**
     * V4 JPA에서 DTO 직접 조회
     * To one 관계 조회, To many 관계 조회 각각 해줌
     * 반복문을 통해 To one 조회 내용에 To many 내용 set 해줌 -> To many 엔티티 1개당 N번 쿼리 조회
     * N+1 문제 발생
     */
    @GetMapping("api/v4/orders")
    public List<OrderQueryDto> ordersV4() {
        return orderQueryRepository.findOrderQueryDtos();
    }

    /**
     * V5 JPA에서 DTO 직접 조회, N+1문제 해결
     * To one 관계 조회, To many관계를 In query로 한번에 조회
     * To many 관계 조회한 내용을 map으로 변경
     * 반복문을 통해 To one 관계 조회 내용에 넣어줌 -> map의 내용 넣는거라 쿼리문 조회 안함
     */
    @GetMapping("api/v5/orders")
    public List<OrderQueryDto> ordersV5() {
        return orderQueryRepository.findAllByDto_optimization();
    }

    /**
     * V6: query 한방에 처리
     * 추가처리 매우 복잡, 페이징 불가능
     */
//    @GetMapping("api/v6/orders")
//    public List<OrderQueryDto> ordersV6() {
//        List<OrderFlatDto> flats = orderQueryRepository.findAllByDto_flat();
//
////        return flats.stream()
////                .collect(groupingBy(o -> new OrderQueryDto(o.getOrderId(), o.getName(), o.getOrderDate(), o.getOrderStatus(), o.getAddress()),
////                        mapping(o -> new OrderItemQueryDto(o.getOrderId(), o.getItemName(), o.getOrderPrice(),
////                                o.getCount()), toList()))).entrySet().stream()
////                .map(e -> new OrderQueryDto(e.getKey().getOrderId(),e.getKey().getName(), e.getKey().getOrderDate(), e.getKey().getOrderStatus(),
////                        e.getKey().getAddress(), e.getValue()))
////                .collect(toList());
//    }

    @Data
    static class OrderDto {

        private Long orderId;
        private String name;
        private LocalDateTime orderDate;
        private OrderStatus orderStatus;
        private Address address;
        private List<OrderItemDto> orderItems; //엔티티 의존을 확실히 끊어야함!

        public OrderDto(Order order) {
            orderId = order.getId();
            name = order.getMember().getName();
            orderDate = order.getOrderDate();
            orderStatus = order.getStatus();
            address = order.getDelivery().getAddress();
            orderItems = order.getOrderItems().stream()
                    .map(orderItem -> new OrderItemDto(orderItem))
                    .collect(toList());
        }
    }

    @Data
    static class OrderItemDto {
        private String itemName;//상품 명
        private int orderPrice; //주문 가격
        private int count; //주문 수량

        public OrderItemDto(OrderItem orderItem) {
            itemName = orderItem.getItem().getName();
            orderPrice = orderItem.getOrderPrice();
            count = orderItem.getCount();
        }
    }
}
