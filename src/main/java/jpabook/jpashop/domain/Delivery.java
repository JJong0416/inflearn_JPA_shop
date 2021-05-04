package jpabook.jpashop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
public class Delivery {

    @Id @GeneratedValue
    @Column(name = "delivery_id")
    private Long id;

    @OneToOne(mappedBy = "delivery", fetch = FetchType.LAZY)
    private Order order; // Delivery랑 order 매핑

    @Embedded
    private Address address;

    @Enumerated(EnumType.STRING) // Enum타입은 이 어노테이션 넣어야하고 , ordinal은 숫자, string은 문자열 규칙 꼭 지켜
    private DeliveryStatus status; // READY 배송준비 , COMP 배송


}
