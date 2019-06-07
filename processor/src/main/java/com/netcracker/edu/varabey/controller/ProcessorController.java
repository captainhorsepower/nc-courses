package com.netcracker.edu.varabey.controller;

import com.netcracker.edu.varabey.controller.client.WebClient;
import com.netcracker.edu.varabey.controller.dto.CategoryDTO;
import com.netcracker.edu.varabey.controller.dto.CustomerDTO;
import com.netcracker.edu.varabey.controller.dto.OfferDTO;
import com.netcracker.edu.varabey.controller.dto.TagDTO;
import com.netcracker.edu.varabey.controller.dto.domainspecific.NewOrderDTO;
import com.netcracker.edu.varabey.controller.dto.domainspecific.SimplifiedOrderDTO;
import com.netcracker.edu.varabey.controller.dto.domainspecific.VerboseOrderDTO;
import com.netcracker.edu.varabey.util.custom.beanannotation.Logged;
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
        logger.info("Filters : category={}, tags={}, price_range=[{}, {}]", category, tags, minPrice, maxPrice);
        return webClient.findAllOffers(category, tags, minPrice, maxPrice);
    }

    @PutMapping("/catalog/offers/{id}")
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



    @Logged(messageBefore = "Request to create new customer...",
            messageAfter = "Customer created successfully!",
            startFromNewLine = true)
    @PostMapping("/customers")
    @ResponseStatus(HttpStatus.CREATED)
    public CustomerDTO signUp(@RequestBody CustomerDTO customerDTO) {
        return webClient.signUpUsingEmail(customerDTO);
    }

    @Logged(messageBefore = "Request to find customer by unique identifier...",
            messageAfter = "Customer found!",
            startFromNewLine = true)
    @GetMapping("/customers/{emailOrId}")
    @ResponseStatus(HttpStatus.OK)
    public CustomerDTO findCustomer(@PathVariable("emailOrId") String emailOrId) {
        return webClient.findCustomer(emailOrId);
    }

    @Logged(messageBefore = "Request to get all customers...",
            messageAfter = "Successfully retrieved all customers!",
            startFromNewLine = true)
    @GetMapping("/customers")
    @ResponseStatus(HttpStatus.OK)
    public List<CustomerDTO> getAllCustomers() {
        return webClient.findAllCustomers();
    }

    @Logged(messageBefore = "Request to edit customer...",
            messageAfter = "Customer edited and saved.",
            startFromNewLine = true)
    @PutMapping("/customers/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CustomerDTO editCustomerNameAndAge(@PathVariable("id") Long id, @RequestBody CustomerDTO customerDTO) {
        return webClient.editCustomer(id, customerDTO);
    }

    @Logged(messageBefore = "Request to delete customer...",
            messageAfter = "Customer deleted.",
            startFromNewLine = true)
    @DeleteMapping("/customers/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCustomer(@PathVariable("id") Long id) {
        webClient.deleteCustomer(id);
    }



    @PostMapping("/orders")
    @ResponseStatus(HttpStatus.CREATED)
    public VerboseOrderDTO packNewOrder(@RequestBody NewOrderDTO newOrderDTO) {
        return webClient.createOrder(newOrderDTO);
    }

    @GetMapping("/orders/{id}")
    @ResponseStatus(HttpStatus.OK)
    public VerboseOrderDTO getOrderDetails(@PathVariable("id") Long id) {
        return webClient.findOrder(id);
    }

    @GetMapping("/orders")
    @ResponseStatus(HttpStatus.OK)
    public List<SimplifiedOrderDTO> findAllOrdersByPaymentStatus(@RequestParam("isPaid") Boolean isPaid) {
        return webClient.findAllOrdersByPaymentStatus(isPaid);
    }

    @GetMapping("/customers/{email}/orders")
    @ResponseStatus(HttpStatus.OK)
    public List<SimplifiedOrderDTO> getAllOrdersByEmail(@PathVariable("email") String email) {
        return webClient.findAllOrdersByEmail(email);
    }

    @GetMapping("/customers/{email}/orders/totalPrice")
    @ResponseStatus(HttpStatus.OK)
    public Double getTotalEmailSpendings(@PathVariable("email") String email) {
        return webClient.getEmailSpendings(email);
    }

    @GetMapping("/customers/{email}/orders/count")
    @ResponseStatus(HttpStatus.OK)
    public Integer getEmailOrderCount(@PathVariable("email") String email) {
        return webClient.getEmailOrderCount(email);
    }

    @PutMapping("/orders/{id}/pay")
    @ResponseStatus(HttpStatus.OK)
    public SimplifiedOrderDTO confirmPaymentForOrder(@PathVariable("id") Long id) {
        return webClient.confirmPaymentForOrder(id);
    }

    @PutMapping("/orders/{id}")
    @ResponseStatus(HttpStatus.OK)
    public SimplifiedOrderDTO changeOrderStatus(@PathVariable("id") Long id, @RequestBody NewOrderDTO orderDTO) {
        return webClient.changeOrderStatus(id, orderDTO.getOrderStatus());
    }

    @PostMapping("/orders/{id}/items")
    @ResponseStatus(HttpStatus.CREATED)
    public SimplifiedOrderDTO addItemsToOrder(@PathVariable("id") Long orderId, @RequestParam("id") List<Long> offerIds) {
        return webClient.addItemsToOrder(orderId, offerIds);
    }

    @DeleteMapping("/orders/{id}/items")
    @ResponseStatus(HttpStatus.OK)
    public SimplifiedOrderDTO removeItemsFromOrder(@PathVariable("id") Long orderId, @RequestParam("id") List<Long> itemIds) {
        return webClient.removeItemsFromOrder(orderId, itemIds);
    }
}
