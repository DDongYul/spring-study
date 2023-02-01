package hello.itemservice.domain.item;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class ItemRepositoryTest {

    ItemRepository itemRepository = new ItemRepository();

    @AfterEach
    void afterEach() {
        itemRepository.clearStore();
    }

    @Test
    void save() {
        //given
        Item item = new Item("itemA", 10000, 10);
        //when
        Item savedItem = itemRepository.save(item);
        //then
        assertThat(itemRepository.findById(savedItem.getId())).isEqualTo(item);
    }

    @Test
    void findById() {
        //given
        Item item = new Item("itemA", 10000, 10);
        //when
        Item savedItem = itemRepository.save(item);
        //then
        assertThat(itemRepository.findById(item.getId())).isEqualTo(savedItem);
    }

    @Test
    public void findAll() {
        //given
        Item item1 = new Item("item1", 3000, 30);
        Item item2 = new Item("item2", 4000, 40);
        itemRepository.save(item1);
        itemRepository.save(item2);
        //when
        List<Item> items = itemRepository.findAll();
        //then
        assertThat(items.size()).isEqualTo(2);
        assertEquals(2, items.size());
        assertThat(items).contains(item1, item2);
    }

    @Test
    void update() {
        //given
        Item item = new Item("item1", 10000, 10);
        Item savedItem = itemRepository.save(item);
        Long itemId = savedItem.getId();

        //when
        ItemParamDTO itemParamDTO = new ItemParamDTO("item2", 20000, 20);
        itemRepository.update(itemId, itemParamDTO);

        //then
        Item findItem = itemRepository.findById(itemId);
    }

    @Test
    void clearStore() {
    }
}