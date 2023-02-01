package hello.itemservice.domain.item;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ItemRepository {

    private static Map<Long, Item> store = new HashMap<>(); //여러 쓰레드 동시에 접근할떄는 ConcurrentHashMap<>() 사용해야함
    private static long sequence = 0L; //static 동시에 접근할떄 다른 long 써야함

    public Item save(Item item) {
        item.setId(++sequence);
        store.put(item.getId(), item);
        return item;
    }

    public Item findById(Long id) {
        return store.get(id);
    }

    public List<Item> findAll() {
        return new ArrayList<>(store.values()); //Arraylist로 안전하게 감쌈
    }

    //설계상 명확하게 하기 위해 ItemParamDTO객체를 만들어줌
    public void update(Long itemId, ItemParamDTO updateParam) {
        Item findItem = findById(itemId);
        findItem.setItemName(updateParam.getItemName());
        findItem.setPrice(updateParam.getPrice());
        findItem.setQuantity(updateParam.getQuantity());
    }

    //테스트용
    public void clearStore() {
        store.clear();
    }


}
