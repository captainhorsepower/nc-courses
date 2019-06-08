package com.netcracker.edu.varabey.processor.controller;

import com.netcracker.edu.varabey.processor.controller.client.WebClient;
import com.netcracker.edu.varabey.processor.controller.dto.CategoryDTO;
import com.netcracker.edu.varabey.processor.controller.dto.CustomerDTO;
import com.netcracker.edu.varabey.processor.controller.dto.OfferDTO;
import com.netcracker.edu.varabey.processor.controller.dto.domainspecific.NewOrderDTO;
import com.netcracker.edu.varabey.processor.controller.dto.domainspecific.SimplifiedOrderDTO;
import com.netcracker.edu.varabey.processor.controller.dto.domainspecific.VerboseOrderDTO;
import com.netcracker.edu.varabey.processor.springutils.beanannotation.Logged;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProcessorController {
    protected Logger logger = LoggerFactory.getLogger(ProcessorController.class);

    private final WebClient webClient;

    public ProcessorController(WebClient webClient) {
        this.webClient = webClient;
    }

    @PostMapping("/catalog/offers")
    @ResponseStatus(HttpStatus.CREATED)
    @Logged(messageBefore = "Received request to create new Offer in catalog...", messageAfter = "Offer created.", startFromNewLine = true)
    public OfferDTO createOffer(@RequestBody OfferDTO offerDTO) {
        return webClient.createOffer(offerDTO);
    }

    @GetMapping("/catalog/offers/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Logged(messageBefore = "Received request to retrieve offer by id...", messageAfter = "Offer retrieved.", startFromNewLine = true)
    public OfferDTO getOfferById(@PathVariable("id") Long id) {
        return webClient.findOfferById(id);
    }

    @GetMapping("/catalog/offers")
    @ResponseStatus(HttpStatus.OK)
    @Logged(messageBefore = "Received request to retrieve multiple offers...", messageAfter = "Offers retrieved.", startFromNewLine = true)
    public List<OfferDTO> getAllOffers(
            @RequestParam(name = "category", required = false) String category,
            @RequestParam(name = "tags", required = false) List<String> tags,
            @RequestParam(name = "minPrice", required = false) Double minPrice,
            @RequestParam(name = "maxPrice", required = false) Double maxPrice
    ) {
        logger.info("Filter: category={}, tags={}, price_range=[{}, {}]", category, tags, minPrice, maxPrice);
        return webClient.findAllOffers(category, tags, minPrice, maxPrice);
    }

    @PutMapping("/catalog/offers/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Logged(messageBefore = "Received request to update offer (name and price)...", messageAfter = "Offer was updated.", startFromNewLine = true)
    public OfferDTO updateOfferNameAndPrice(@PathVariable("id") Long id, @RequestBody OfferDTO offerDTO) {
        return webClient.updateOfferNameAndPrice(id, offerDTO);
    }

    @PostMapping("/catalog/offers/{id}/tags")
    @ResponseStatus(HttpStatus.OK)
    @Logged(messageBefore = "Received request to add tags to offer...", messageAfter = "Offer was updated.", startFromNewLine = true)
    public OfferDTO addTagsToOffer(@PathVariable("id") Long id, @RequestBody List<String> tags) {
        return webClient.addTagsToOffer(id, tags);
    }

    @DeleteMapping("/catalog/offers/{id}/tags")
    @ResponseStatus(HttpStatus.OK)
    @Logged(messageBefore = "Received request to remove tags from offer...", messageAfter = "Offer was updated.", startFromNewLine = true)
    public OfferDTO removeTagsFromOffer(@PathVariable("id") Long id, @RequestBody List<String> tags) {
        return webClient.removeTagsFromOffer(id, tags);
    }

    @PutMapping("/catalog/offers/{id}/category")
    @ResponseStatus(HttpStatus.OK)
    @Logged(messageBefore = "Received request to change offer's category...", messageAfter = "Offer was updated.", startFromNewLine = true)
    public OfferDTO changeOfferCategory(@PathVariable("id") Long id, @RequestBody CategoryDTO categoryDTO) {
        return webClient.changeOfferCategory(id, categoryDTO);
    }

    @DeleteMapping("/catalog/offers/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Logged(messageBefore = "Received request to delete offer...", messageAfter = "Offer was deleted.", startFromNewLine = true)
    public void deleteOffer(@PathVariable("id") Long id) {
        webClient.deleteOffer(id);
    }

//    @PostMapping("/categories")
//    @ResponseStatus(HttpStatus.CREATED)
//    public CategoryDTO createCategory(@RequestBody CategoryDTO categoryDTO) {
//        return webClient.createCategory(categoryDTO);
//    }
//
//    @PostMapping("/categories/saveAll")
//    @ResponseStatus(HttpStatus.CREATED)
//    public List<CategoryDTO> createAllCategories(@RequestBody List<CategoryDTO> categoryDTOS) {
//        return webClient.createAllCategories(categoryDTOS);
//    }
//
//    @GetMapping("/categories/{input}")
//    @ResponseStatus(HttpStatus.OK)
//    public CategoryDTO findCategoryById(@PathVariable("input") String input) {
//        return webClient.findCategory(input);
//    }
//
//    @PutMapping("/categories/{id}")
//    @ResponseStatus(HttpStatus.OK)
//    public CategoryDTO updateCategoryName(@PathVariable("id") Long id, @RequestBody CategoryDTO categoryDTO) {
//        return webClient.updateCategoryName(id, categoryDTO);
//    }
//
//    @DeleteMapping("/categories/{id}")
//    @ResponseStatus(HttpStatus.NO_CONTENT)
//    public void deleteCategory(@PathVariable("id") Long id) {
//        webClient.deleteCategory(id);
//    }
//
//    @PostMapping("/tags")
//    @ResponseStatus(HttpStatus.CREATED)
//    public TagDTO createTag(@RequestBody TagDTO tagDTO) {
//        return webClient.createTag(tagDTO);
//    }
//
//    @PostMapping("/tags/saveAll")
//    @ResponseStatus(HttpStatus.CREATED)
//    public List<TagDTO> createAllTags(@RequestBody List<TagDTO> tagDTOS) {
//        return webClient.createAllTags(tagDTOS);
//    }
//
//    @GetMapping("/tags/{input}")
//    @ResponseStatus(HttpStatus.OK)
//    public TagDTO findTagById(@PathVariable("input") String input) {
//        return webClient.findTag(input);
//    }
//
//    @PutMapping("/tags/{id}")
//    @ResponseStatus(HttpStatus.OK)
//    public TagDTO updateTagName(@PathVariable("id") Long id, @RequestBody TagDTO tagDTO) {
//        return webClient.updateTagName(id, tagDTO);
//    }
//
//    @DeleteMapping("/tags/{id}")
//    @ResponseStatus(HttpStatus.NO_CONTENT)
//    public void deleteTag(@PathVariable("id") Long id) {
//        webClient.deleteTag(id);
//    }



    @PostMapping("/customers")
    @ResponseStatus(HttpStatus.CREATED)
    @Logged(messageBefore = "Received request to sign-up new Customer...", messageAfter = "Customer account was created.", startFromNewLine = true)
    public CustomerDTO signUp(@RequestBody CustomerDTO customerDTO) {
        return webClient.signUpUsingEmail(customerDTO);
    }

    @GetMapping("/customers/{emailOrId}")
    @ResponseStatus(HttpStatus.OK)
    @Logged(messageBefore = "Received request to find Customer...", messageAfter = "Customer retrieved.", startFromNewLine = true)
    public CustomerDTO findCustomer(@PathVariable("emailOrId") String emailOrId) {
        return webClient.findCustomer(emailOrId);
    }

    @GetMapping("/customers")
    @ResponseStatus(HttpStatus.OK)
    @Logged(messageBefore = "Received request to find all Customers...", messageAfter = "Customers retrieved.", startFromNewLine = true)
    public List<CustomerDTO> getAllCustomers() {
        return webClient.findAllCustomers();
    }

    @PutMapping("/customers/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Logged(messageBefore = "Received request to update Customer's personal data...", messageAfter = "Customer was updated.", startFromNewLine = true)
    public CustomerDTO editCustomerNameAndAge(@PathVariable("id") Long id, @RequestBody CustomerDTO customerDTO) {
        return webClient.editCustomer(id, customerDTO);
    }

    @DeleteMapping("/customers/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Logged(messageBefore = "Received request to delete Customer...", messageAfter = "Customer account deleted.", startFromNewLine = true)
    public void deleteCustomer(@PathVariable("id") Long id) {
        webClient.deleteCustomer(id);
    }





    @PostMapping("/inventory/orders")
    @ResponseStatus(HttpStatus.CREATED)
    @Logged(messageBefore = "Received request to create new Order...", messageAfter = "Order packed.", startFromNewLine = true)
    public VerboseOrderDTO packNewOrder(@RequestBody SimplifiedOrderDTO newOrderDTO) {
        return webClient.createOrder(newOrderDTO);
    }

    @GetMapping("/inventory/orders/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Logged(messageBefore = "Received request to retrieve order by id...", messageAfter = "Order retrieved.", startFromNewLine = true)
    public VerboseOrderDTO getOrderDetails(@PathVariable("id") Long id) {
        return webClient.findOrder(id);
    }

    @GetMapping("/inventory/orders")
    @ResponseStatus(HttpStatus.OK)
    @Logged(messageBefore = "Received request to find all orders by payment status...", messageAfter = "Orders found.", startFromNewLine = true)
    public List<SimplifiedOrderDTO> findAllOrdersByPaymentStatus(@RequestParam("isPaid") Boolean isPaid) {
        return webClient.findAllOrdersByPaymentStatus(isPaid);
    }

    @GetMapping("/inventory/customers/{email}/orders")
    @ResponseStatus(HttpStatus.OK)
    @Logged(messageBefore = "Received request to all order coupled to email...", messageAfter = "Orders found.", startFromNewLine = true)
    public List<SimplifiedOrderDTO> getAllOrdersByEmail(@PathVariable("email") String email) {
        return webClient.findAllOrdersByEmail(email);
    }

    @GetMapping("/inventory/customers/{email}/orders/totalPrice")
    @ResponseStatus(HttpStatus.OK)
    @Logged(messageBefore = "Received request to get total money spent by Customer...", messageAfter = "Done.", startFromNewLine = true)
    public Double getTotalEmailSpendings(@PathVariable("email") String email) {
        return webClient.getEmailSpendings(email);
    }

    @GetMapping("/inventory/customers/{email}/orders/totalCount")
    @ResponseStatus(HttpStatus.OK)
    @Logged(messageBefore = "Received request to get count off all bought items by Customer...", messageAfter = "Done.", startFromNewLine = true)
    public Integer getEmailOrderCount(@PathVariable("email") String email) {
        return webClient.getEmailOrderCount(email);
    }

    @PutMapping("/inventory/orders/{id}/pay")
    @ResponseStatus(HttpStatus.OK)
    @Logged(messageBefore = "Received request to Order as paid...", messageAfter = "Order payment status updated.", startFromNewLine = true)
    public SimplifiedOrderDTO confirmPaymentForOrder(@PathVariable("id") Long id) {
        return webClient.confirmPaymentForOrder(id);
    }

    @PutMapping("/inventory/orders/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Logged(messageBefore = "Received request to update Order's status...", messageAfter = "Order status updated.", startFromNewLine = true)
    public SimplifiedOrderDTO changeOrderStatus(@PathVariable("id") Long id, @RequestBody NewOrderDTO orderDTO) {
        return webClient.changeOrderStatus(id, orderDTO.getOrderStatus());
    }

    @PostMapping("/inventory/orders/{id}/items")
    @ResponseStatus(HttpStatus.CREATED)
    @Logged(messageBefore = "Received request to add items to the Order...", messageAfter = "Items were added.", startFromNewLine = true)
    public SimplifiedOrderDTO addItemsToOrder(@PathVariable("id") Long orderId, @RequestParam("id") List<Long> offerIds) {
        return webClient.addItemsToOrder(orderId, offerIds);
    }

    @DeleteMapping("/inventory/orders/{id}/items")
    @ResponseStatus(HttpStatus.OK)
    @Logged(messageBefore = "Received request to remove items from the Order...", messageAfter = "Items were removed.", startFromNewLine = true)
    public SimplifiedOrderDTO removeItemsFromOrder(@PathVariable("id") Long orderId, @RequestParam("id") List<Long> itemIds) {
        return webClient.removeItemsFromOrder(orderId, itemIds);
    }
}
