package jpabook.jpashop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Member {

    @Id @GeneratedValue // GenerateValue 쓰는 건 Sequence 값을 가지기 위해 사용
    @Column(name = "member_id") // 맵핑할 테이블의 컬럼 이름을 member_id로 지정
    private Long id;

    private String name;

    @Embedded // 내장타입이다는 것을 표기
    private Address address;

    @OneToMany(mappedBy = "member") // 난 이 연관관계의 주인이 아니라는 것을 표시해야한다. 그래서
    // 주인은 Order 테이블에 있는 멤버필드 member에 의해서. 맵핑당하는 거울이다.
    private List<Order> orders = new ArrayList<>();
}
