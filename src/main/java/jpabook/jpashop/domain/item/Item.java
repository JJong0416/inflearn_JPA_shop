package jpabook.jpashop.domain.item;


import jpabook.jpashop.domain.Category;
import jpabook.jpashop.exception.NotEnoughStockException;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE) // Single_Table, Table_PER_CLASS, JOINED 가 있는데 우린 싱글테이블 전략으로 간다
@DiscriminatorColumn(name = "dtype") // 북이면 어떻게 할꺼야? 앨범이면 어떻게 할거야?
@Getter @Setter
public abstract class Item { // 추상클래스 왜냐면 구현체를 가지고 할 거 기 때문에
    @Id
    @GeneratedValue
    @Column(name = "item_id")
    private Long id;

    private String name;
    private int price;
    private int stockQuantity;

    @ManyToMany(mappedBy = "items")
    private List<Category> categories = new ArrayList<>();

    //==비지니스 로직==//
    // 왜 여기에 넣어? 도메인 주도 설계할 때 엔티티가 자체가 해결 할 수 있을 때는 엔티티안에 비지니스 로직안에 넣는게 더 좋다! //
    // 즉 stockQuantity 있는 이 Entity에 비지니스 로직을 만드는게 더 결집력이 강하다. //

    /*
    * stock 증가
    */
    public void addStock(int quantity){
        this.stockQuantity += quantity;
    }

    /*
    * stock 감소
     */
    public void removeStock(int quantity){
        int restStock = this.stockQuantity - quantity;
        if (restStock < 0){
            throw new NotEnoughStockException("need more stock");
        }
        this.stockQuantity = restStock;
    }

}
