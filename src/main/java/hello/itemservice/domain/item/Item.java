package hello.itemservice.domain.item;

import lombok.Data;

//@Getter @Setter
@Data //실무에서는 조심해서 써야함
public class Item {
    private long id;
    private String itemName;
    private Integer price;
    private Integer quantity;

    public Item() {
    }

    public Item(String itemName, Integer price, Integer quantity) {
        this.itemName = itemName;
        this.price = price;
        this.quantity = quantity;
    }
}
