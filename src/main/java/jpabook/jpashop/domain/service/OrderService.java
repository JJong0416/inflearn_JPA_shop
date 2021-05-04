package jpabook.jpashop.domain.service;

import jpabook.jpashop.domain.Delivery;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderItem;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.domain.repository.ItemRepository;
import jpabook.jpashop.domain.repository.MemberRepository;
import jpabook.jpashop.domain.repository.OrderRepository;
import jpabook.jpashop.domain.repository.OrderSearch;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;
    // 여기서 리파지토리를 가져오는 이유는 주문할 때, 아이디는 회원의 아이디만, 객체가 아니라, 그리고 상품명도 상품명 아이디만 가져오기 때문에.


    /**
     * 주문
     */
    @Transactional
    public Long order(Long memberId, Long itemId, int count){
        // 엔티티 조회 //
        Member member = memberRepository.findOne(memberId);
        Item item = itemRepository.findOne(itemId);

        // 배송정보 생성 //
        Delivery delivery = new Delivery();
        delivery.setAddress(member.getAddress());

        // 주문 상품 생성 //
        OrderItem orderItem = OrderItem.createOrderItem(item, item.getPrice(), count);

        // 주문 생성
        Order order = Order.createOrder(member, delivery, orderItem);

        // 주문 저장
        orderRepository.save(order); // 원래 딜리버리, 오더 둘 다 따로 생성해야하는데, 딜리버리 리파지토리 있어가지고 딜리버리 리파지토리.SAVE해서 넣어주고
        //  order도 똑같이 그렇게 한 다음에, 해야하는데 orderRepository.save(order) 이거 하나만 했음. 왜그러냐면, CaseCadeType 옵션때문에 그런다
        // cascadeType.all 은 order를 persist하면 여기 들어와있는 리스트들도 다 persist해준다. delivery도 마찬가지.
        // 주인이 private Owner일 때만 사용해야한다 라이프사이클이 동일할 떄.
        // 즉 여기서 orderItem이랑 delivery는 여기서만 쓰기 때문에 이렇게 하는 게 더 좋다 여기서는!
        return order.getId();
    }


    /**
     * 주문취소
     */

    @Transactional
    public void cancelOrder(Long orderId){
        // 주문 엔티티 조회
        Order order = orderRepository.findOne(orderId);
        // 주문 취소
        order.cancel();
    }

    /**
     * 검색
     */

    public List<Order> findOrders(OrderSearch orderSearch){
        return orderRepository.findAllByString(orderSearch);
    }
}
