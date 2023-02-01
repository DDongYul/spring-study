package hello.itemservice.domain.item;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ItemParamDTO {
    private String itemName;
    private Integer price;
    private Integer quantity;

    public ItemParamDTO(String itemName, Integer price, Integer quantity) {
        this.itemName = itemName;
        this.price = price;
        this.quantity = quantity;
    }
}
