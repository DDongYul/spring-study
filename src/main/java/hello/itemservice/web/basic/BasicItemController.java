package hello.itemservice.web.basic;

import hello.itemservice.domain.item.Item;
import hello.itemservice.domain.item.ItemParamDTO;
import hello.itemservice.domain.item.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.PostConstruct;
import java.util.List;

@Controller
@RequestMapping("/basic/items")
@RequiredArgsConstructor
public class BasicItemController {

    private final ItemRepository itemRepository;

    @GetMapping
    public String items(Model model){
        List<Item> items = itemRepository.findAll();
        model.addAttribute("items", items);
        return "basic/items";
    }

    @GetMapping("/{itemId}")
    public String item(@PathVariable long itemId, Model model){
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "basic/item";
    }

    //Get으로 단순히 등록 Form을 보여주는것
    @GetMapping("/add")
    public String addForm(){
        return "basic/addForm";
    }

    //같은 URI에 대해 Get과 Post로 역할을 구분
//    @PostMapping("/add")
    public String addItemV1(@RequestParam String itemName,
                       @RequestParam Integer price,
                       @RequestParam Integer quantity,
                       Model model
    ){
        Item item = new Item(itemName, price, quantity);
        itemRepository.save(item);

        List<Item> items = itemRepository.findAll();
        model.addAttribute("items", items); //이런 경우는 Model 파라미터가 필요할듯!
        return "basic/items";
    }

//    @PostMapping("/add")
    public String addItemV2(@ModelAttribute("item") Item item){
//        Item item = new Item();
//        item.setItemName(itemName);
//        item.setItemPrice(price);
//        item.setItemQuantity(quantity); 전부 ModelAttribute가 자동으로 호출
        itemRepository.save(item);

//        model.addAttribute("item", item); -> ModelAttribute에 지정해준 이름으로 알아서 반환해줌(Model 파라미터 생략 가능)
        return "basic/item";
    }

//    @PostMapping("/add")
    public String addItemV3(@ModelAttribute Item item){
    //Model명은 Item -> item으로 바꿔서 자동으로 등록해줌
        itemRepository.save(item);

        return "basic/item";
    }

//    @PostMapping("/add")
    public String addItemV4(Item item){
    //ModelAttribute 생략 -> Item -> item으로 바꿔서 등록하는건 같음
        itemRepository.save(item);
        return "basic/items";
    }

//    @PostMapping("/add")
    public String addItemV5(Item item){
    //PRG방식으로 수정
        itemRepository.save(item);
        return "redirect:/basic/items/" + item.getId();
    }

    @PostMapping("/add")
    public String addItemV6(Item item, RedirectAttributes redirectAttributes){
        Item savedItem = itemRepository.save(item);
        redirectAttributes.addAttribute("itemId", savedItem.getId());   //치환
        redirectAttributes.addAttribute("status", true);    //치환될게 없으면 query parameter
        return "redirect:/basic/items/{itemId}";
    }

    //상품 수정 폼 단순 조회
    @GetMapping("/{itemId}/edit")
    public String editForm(@PathVariable Long itemId, Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute(item);
        return "basic/editForm";
    }

    @PostMapping("/{itemId}/edit")
    public String updateItem(@PathVariable Long itemId, @ModelAttribute Item item) {
        ItemParamDTO itemParamDTO = new ItemParamDTO(item.getItemName(), item.getPrice(), item.getQuantity());
        itemRepository.update(itemId,itemParamDTO);
        return "redirect:/basic/items/{itemId}"; //redirect를 통해 실제 url을 바꿈 현재 등록에서는 redirect 안해서 url 그대로임
    }


    /**
     * 테스트용 데이터 추가
     */
    @PostConstruct
    public void init() {
        itemRepository.save(new Item("testA", 10000, 10));
        itemRepository.save(new Item("testB", 20000, 20));
    }
}
