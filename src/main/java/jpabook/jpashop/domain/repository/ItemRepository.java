package jpabook.jpashop.domain.repository;

import jpabook.jpashop.domain.item.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository//컴포넌트스캔으로 자동으로 스프링 빈으로 관리 된다
@RequiredArgsConstructor
public class ItemRepository {

    private final EntityManager em;


    public void save(Item item){
        if (item.getId() == null){
            em.persist(item);
        }else{ //
            em.merge(item); // Item merge = em.merge(item)에서 파라미터 넘어온 아이템은 영속성으로 안바뀌고 merge 값은 영속성으로 바뀐다.
        }
    }

    public Item findOne(Long id){
        return em.find(Item.class, id);
    }

    public List<Item> findAll(){ // 여러개 찾는 경우에는 sql 작성해야한다.
        return em.createQuery("select i from Item i", Item.class)
                .getResultList();
    }

}
