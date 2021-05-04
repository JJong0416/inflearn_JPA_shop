package jpabook.jpashop.domain;

import jpabook.jpashop.domain.item.Item;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Category {
    @Id @GeneratedValue
    @Column(name = "category_id")
    private Long id;

    private String name;

    @ManyToMany
    @JoinTable(name = "category_item",
        joinColumns = @JoinColumn(name = "category_id"), // 이놈은 중간 테이블에서 category_id로 가는거
            inverseJoinColumns = @JoinColumn(name = "item_id") // 이놈은 중간테이블에서 item_id로 가는거 .
    ) // 다대다는 JoinTable이 필요하다. 중간테이블 매핑한다라는 개념
    // 객체는 다대다 가능하지만, rmdbs 는 다대다가 안되서 일대다 다대일로 풀어야 해서 중간테이블 생성 이거 중요하다!
    // 실무에서 쓰지 말아야 하는 이유는 이런 그림의 엔티티에서만 가능하기 때문에. 더 추가적인게 불가능해 ㅠㅠ.. 필드추가도 안돼!


    private List<Item> items = new ArrayList <>();

    @ManyToOne(fetch =  FetchType.LAZY) // 내 부모니깐 당연히 아이들은 많고 엄마 아빠는 정해져 있으니 manyToOne이다
    @JoinColumn(name = "parent_id")
    private Category parent;

    // 같은 엔티티에서 서로 연관관계를 걸었다고 보면 된다.

    @OneToMany(mappedBy = "parent") // 그럼 아이들은 ? 아이들은 여려명 가질 수 있기에 원투메니
    private List<Category> child = new ArrayList<>();

    // === 연관관계 메서드 === //
    public void addChildCategory(Category child){
        this.child.add(child);
        child.setParent(this);
    }

}
