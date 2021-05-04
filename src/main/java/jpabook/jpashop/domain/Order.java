package jpabook.jpashop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders") // 테이블을 적어줘야한다.
@Getter @Setter
public class Order {

    @Id @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY) // Order의 입장에서 다 대 1 관계라서 ManyToMany, 만약에 다 대 다 관계면은 ManyToMany로 매핑
    @JoinColumn(name = "member_id") // 매핑을 뭘로 할 것이냐 !? foreign key 이름이 member_id로 한다는 뜻이다.
    private Member member;

    @OneToMany(mappedBy= "order",cascade = CascadeType.ALL)
    // persist(orderItemA)
    // persist(orderItemB)          => persist(order) 즉 cascade가 모든걸 전파해주는 역할이므로 한번에 보내진다
    // persist(orderItemC)
    private List<OrderItem> orderItems = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL) // OneToOne은 엑세스를 많이 하는 거에 FK를 주는 것이 좋은 경우가 많다 - 김영한
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;

    private LocalDateTime orderDate;    // 자바8에서 나온 LocalDateTime이고, 주문시간

    @Enumerated(EnumType.STRING)
    private OrderStatus status; // 주문상태 [ORDER, CANCEL]

    //==연관관계 메서드==//
    //== 연관관계 메서드는 Control 하는 쪽이 들고 있는 것이 편하다. //

    public void setMember(Member member){
        this.member = member;
        member.getOrders().add(this);
        // 메인에서 member.getOrder().add(order);
        // order.setMember(member) 할 것을 줄여준 것이다 ! 까먹을 수 있기 떄문에 이렇게 편리하게 한것.
    }
    public void addOrderItem(OrderItem orderItem){
        orderItems.add(orderItem);
        orderItem.setOrder(this);
    }

    public void setDelivery(Delivery delivery){
        this.delivery = delivery;
        delivery.setOrder(this);
    }


    //==핵심 비지니스 로직 ==//



    // 생성 메서드 //  -> 기본적으로 밖에서 order를 setsetset 하는게 아니라 그냥 createOrder로 해서 생성 메서드로 대부분의 것을 완결시킨다.
    public static Order createOrder(Member member, Delivery delivery, OrderItem... orderItems){
        Order order = new Order();
        order.setMember(member);
        order.setDelivery(delivery);
        for (OrderItem orderItem : orderItems){
            order.addOrderItem(orderItem);
        } // 세팅 끝
        order.setStatus(OrderStatus.ORDER);
        order.setOrderDate(LocalDateTime.now());
        return order;
    }

    // 비지니스 로직 //
    /**
     * 주문취소
     */
    public void cancel(){
        if ( delivery.getStatus() == DeliveryStatus.COMP){
            throw new IllegalStateException("이미 배송완료된 상품은 취소가 불가능합니다.");
        }
        this.setStatus(OrderStatus.CANCEL);

        for (OrderItem orderItem : orderItems) {
            orderItem.cancle() ; // OrderItem을 취소하는 이유는 고객이 한번 주문할 때, 상품 2개를 주문 할 수 있기 때문에.
        }
    }

    //==조회 로직==//
    /**
     * 전체 주문 가격 조회
     */

    public int getTotalPrice(){
        int totalPrice = 0;

        for (OrderItem orderItem : orderItems) {
            totalPrice += orderItem.getTotalPrice();
        }
        return totalPrice;

    }
}
