package jpabook.jpashop.repository;

import jpabook.jpashop.controller.BookForm;
import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.service.UpdateItemDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ItemRepository {

    private final EntityManager em;

    public void save(Item item) {
        if(item.getId()==null){
            em.persist(item);
        }
        else{
            em.merge(item);
        }
    }

    public void update(Long itemId, UpdateItemDto param) {
        Book findItem = (Book) findOne(itemId);
        findItem.setName(param.getName());
        findItem.setPrice(param.getPrice());
        findItem.setStockQuantity(param.getStockQuantity());
        findItem.setAuthor(param.getAuthor());
        findItem.setIsbn(param.getIsbn());
    }

    public Item findOne(Long id) {
        return em.find(Item.class, id);
    }

    public List<Item> findAll(){
        return em.createQuery("select i from Item i", Item.class)
                .getResultList();
    }
}
