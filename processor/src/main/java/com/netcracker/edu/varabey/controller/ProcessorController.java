package com.netcracker.edu.varabey.controller;

import com.netcracker.edu.varabey.controller.client.WebClient;
import com.netcracker.edu.varabey.dto.CategoryDTO;
import com.netcracker.edu.varabey.dto.CustomerDTO;
import com.netcracker.edu.varabey.dto.OfferDTO;
import com.netcracker.edu.varabey.dto.TagDTO;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProcessorController {
    private final WebClient webClient;

    public ProcessorController(WebClient webClient) {
        this.webClient = webClient;
    }

    @PostMapping("/offers")
    @ResponseStatus(HttpStatus.CREATED)
    public OfferDTO createOffer(@RequestBody OfferDTO offerDTO) {
        return webClient.createOffer(offerDTO);
    }

    @GetMapping("/offers/{id}")
    @ResponseStatus(HttpStatus.OK)
    public OfferDTO getOfferById(@PathVariable("id") Long id) {
        return webClient.findOfferById(id);
    }

    @GetMapping("/offers")
    @ResponseStatus(HttpStatus.OK)
    public List<OfferDTO> getAllOffers(
            @RequestParam(name = "category", required = false) String category,
            @RequestParam(name = "tags", required = false) List<String> tags,
            @RequestParam(name = "minPrice", required = false) Double minPrice,
            @RequestParam(name = "maxPrice", required = false) Double maxPrice
    ) {
        return webClient.findAllOffers(category, tags, minPrice, maxPrice);
    }

    @PutMapping("/offers/{id}")
    @ResponseStatus(HttpStatus.OK)
    public OfferDTO updateOfferNameAndPrice(@PathVariable("id") Long id, @RequestBody OfferDTO offerDTO) {
        return webClient.updateOfferNameAndPrice(id, offerDTO);
    }

    @PutMapping("/offers/{id}/addTags")
    @ResponseStatus(HttpStatus.OK)
    public OfferDTO addTagsToOffer(@PathVariable("id") Long id, @RequestBody List<String> tags) {
        return webClient.addTagsToOffer(id, tags);
    }

    @PutMapping("/offers/{id}/removeTags")
    @ResponseStatus(HttpStatus.OK)
    public OfferDTO removeTagsFromOffer(@PathVariable("id") Long id, @RequestBody List<String> tags) {
        return webClient.removeTagsFromOffer(id, tags);
    }

    @PutMapping("/offers/{id}/changeCategory")
    @ResponseStatus(HttpStatus.OK)
    public OfferDTO changeOfferCategory(@PathVariable("id") Long id, @RequestBody CategoryDTO categoryDTO) {
        return webClient.changeOfferCategory(id, categoryDTO);
    }

    @DeleteMapping("/offers/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteOffer(@PathVariable("id") Long id) {
        webClient.deleteOffer(id);
    }

    @PostMapping("/categories")
    @ResponseStatus(HttpStatus.CREATED)
    public CategoryDTO createCategory(@RequestBody CategoryDTO categoryDTO) {
        return webClient.createCategory(categoryDTO);
    }

    @PostMapping("/categories/saveAll")
    @ResponseStatus(HttpStatus.CREATED)
    public List<CategoryDTO> createAllCategories(@RequestBody List<CategoryDTO> categoryDTOS) {
        return webClient.createAllCategories(categoryDTOS);
    }

    @GetMapping("/categories/{input}")
    @ResponseStatus(HttpStatus.OK)
    public CategoryDTO findCategoryById(@PathVariable("input") String input) {
        return webClient.findCategory(input);
    }

    @PutMapping("/categories/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CategoryDTO updateCategoryName(@PathVariable("id") Long id, @RequestBody CategoryDTO categoryDTO) {
        return webClient.updateCategoryName(id, categoryDTO);
    }

    @DeleteMapping("/categories/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCategory(@PathVariable("id") Long id) {
        webClient.deleteCategory(id);
    }

    @PostMapping("/tags")
    @ResponseStatus(HttpStatus.CREATED)
    public TagDTO createTag(@RequestBody TagDTO tagDTO) {
        return webClient.createTag(tagDTO);
    }

    @PostMapping("/tags/saveAll")
    @ResponseStatus(HttpStatus.CREATED)
    public List<TagDTO> createAllTags(@RequestBody List<TagDTO> tagDTOS) {
        return webClient.createAllTags(tagDTOS);
    }

    @GetMapping("/tags/{input}")
    @ResponseStatus(HttpStatus.OK)
    public TagDTO findTagById(@PathVariable("input") String input) {
        return webClient.findTag(input);
    }

    @PutMapping("/tags/{id}")
    @ResponseStatus(HttpStatus.OK)
    public TagDTO updateTagName(@PathVariable("id") Long id, @RequestBody TagDTO tagDTO) {
        return webClient.updateTagName(id, tagDTO);
    }

    @DeleteMapping("/tags/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTag(@PathVariable("id") Long id) {
        webClient.deleteTag(id);
    }




    @PostMapping(value = "/customers")
    @ResponseStatus(HttpStatus.CREATED)
    public CustomerDTO signUp(@RequestBody CustomerDTO customerDTO) {
        return webClient.signUpUsingEmail(customerDTO);
    }

    @GetMapping(value = "/customers/{emailOrId}")
    @ResponseStatus(HttpStatus.OK)
    public CustomerDTO findCustomer(@PathVariable("emailOrId") String emailOrId) {
        return webClient.findCustomer(emailOrId);
    }

    @GetMapping(value = "/customers")
    @ResponseStatus(HttpStatus.OK)
    public List<CustomerDTO> getAllCustomers() {
        return webClient.findAllCustomers();
    }

    @PutMapping("/customers/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CustomerDTO editCustomerNameAndAge(@PathVariable("id") Long id, @RequestBody CustomerDTO customerDTO) {
        return webClient.editCustomer(id, customerDTO);
    }

    @DeleteMapping("/customers/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCustomer(@PathVariable("id") Long id) {
        webClient.deleteCustomer(id);
    }

//    @PostMapping(value = "/orders")
//    @ResponseStatus(HttpStatus.CREATED)
//    public com.netcracker.edu.varabey.dto.OrderDTO createOrder(@RequestBody OrderInputDTO inputOrderInputDTO) {
//        return webClient.createOrder(inputOrderInputDTO);
//    }

//    @GetMapping(value = "/orders/{id:[\\d]+}")
//    @ResponseStatus(HttpStatus.OK)
//    public OrderInputDTO getOrderById(@PathVariable("id") Long id) {
//        return webClient.getOrderById(id);
//    }
//
//    @GetMapping(value = "/orders")
//    @ResponseStatus(HttpStatus.OK)
//    public List<OrderInputDTO> getAllOrders() {
//        return webClient.getAllOrders();
//    }
//
//    @PutMapping(value = "/orders/{id:[\\d]+}/items")
//    @ResponseStatus(HttpStatus.OK)
//    public OrderInputDTO addItemToOrder(@RequestBody Long itemId, @PathVariable("id") Long id) {
//        return webClient.addItemToOrder(id, itemId);
//    }
//
//    @DeleteMapping(value = "/orders/{id:[\\d]+}/items/{itemId:[\\d]+}")
//    @ResponseStatus(HttpStatus.NO_CONTENT)
//    public void removeItemFromOrder(@PathVariable("id") Long id, @PathVariable("itemId") Long itemId) {
//        webClient.removeItemFromOrder(id, itemId);
//    }
//
//    @PutMapping(value = "/orders/{id:[\\d]+}/pay")
//    @ResponseStatus(HttpStatus.OK)
//    public OrderInputDTO payForOrder(@PathVariable("id") Long id) {
//        return webClient.payForOrder(id);
//    }
//
//
//    @GetMapping(value = "/statuses/{payStatus}")
//    @ResponseStatus(HttpStatus.OK)
//    public List<OrderInputDTO> getAllOrdersByPaymentStatus(@PathVariable("payStatus") String status) {
//        return webClient.getAllOrdersByPaymentStatus(status);
//    }
//
//    @GetMapping(value = "/emails/{email}")
//    @ResponseStatus(HttpStatus.OK)
//    public List<OrderInputDTO> getAllOrdersByEmail(@PathVariable("email") String email) {
//        return webClient.getAllOrdersByEmail(email);
//    }
//
//    @GetMapping(value = "/emails/{email}/amount")
//    @ResponseStatus(HttpStatus.OK)
//    public String getAmountOfItemsBoughtByCustomerWithEmail(@PathVariable("email") String email) {
//        Integer amount = webClient.getAmountOfItemsBoughtByCustomerWithEmail(email);
//        return "The number of offers purchased by the customer with e-mail: \"" + email + "\" is: " + amount + ";";
//    }
//
//    @GetMapping(value = "/emails/{email}/price")
//    @ResponseStatus(HttpStatus.OK)
//    public String GetFullPriceOfItemsBoughtByCustomerWithEmail(@PathVariable("email") String email) {
//        Double fullPrice = webClient.GetFullPriceOfItemsBoughtByCustomerWithEmail(email);
//        return "The total price of goods purchased by the customer with e-mail: \"" + email + "\" is: " + String.format("%.2f", fullPrice) + " Belorussian rubles;";
//    }
//
//    @PutMapping(value = "/orders/{id:[\\d]+}/status/next")
//    @ResponseStatus(HttpStatus.OK)
//    public OrderInputDTO setNextOrderStatus(@PathVariable("id") Long id) {
//        return webClient.setNextOrderStatus(id);
//    }
}
