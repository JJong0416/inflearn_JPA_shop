package jpabook.jpashop.domain.service;

import jpabook.jpashop.domain.*;
import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.domain.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    @Transactional // default가 false이다.
    public void saveItem(Item item){
        itemRepository.save(item);
    }

    @Transactional // 변경감지인데 꼭 이렇게 해라 이게 더 안전하다 merge 하지 말고
    public Item updateItem(Long itemId, String name, int price, int stockQuantity){
        Item findItem = itemRepository.findOne(itemId);
        findItem.setPrice(price);
        findItem.setName(name);
        findItem.setStockQuantity(stockQuantity);

        return findItem;
        // itemRepository.save(findItem); 왜 이걸 호출 안해도 돼?
        // 왜냐면 findItem 이 녀석은 영속 상태이다. 그래서 값을 세팅한 후에, 트랜잭셜에 의해 커밋이 되고 jpa가 플러시(영속성 컨텍스트에 변경된 놈들을 찾고)를 날리고
        // 찾아서 올커니 하고 바뀌였네? 그 후에, JPA가 update 쿼리를 날려 변경하는 것을 "변경감지 수정" 이라고 한다.
        // 그렇다면 merge가 뭐냐면, 지금 이 위에 찾아온 데이터를 다 바꿔치기 한다. 그래서 트랜잭션 커밋될 때 다 반영되는 거다.
        // 그렇다면 차이가 뭐냐?
   }

    public List<Item> findItems(){
        return itemRepository.findAll();
    }

    public Item findONE(Long itemId){
        return itemRepository.findOne(itemId);
    }
}
