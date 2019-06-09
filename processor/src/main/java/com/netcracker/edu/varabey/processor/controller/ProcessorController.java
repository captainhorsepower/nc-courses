package com.netcracker.edu.varabey.processor.controller;

import com.netcracker.edu.varabey.processor.controller.client.WebClient;
import com.netcracker.edu.varabey.processor.controller.dto.CategoryDTO;
import com.netcracker.edu.varabey.processor.controller.dto.CustomerDTO;
import com.netcracker.edu.varabey.processor.controller.dto.OfferDTO;
import com.netcracker.edu.varabey.processor.controller.dto.domainspecific.NewOrderDTO;
import com.netcracker.edu.varabey.processor.controller.dto.domainspecific.SimplifiedOrderDTO;
import com.netcracker.edu.varabey.processor.controller.dto.domainspecific.VerboseOrderDTO;
import com.netcracker.edu.varabey.processor.springutils.beanannotation.Logged;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Api(tags = {"Syr-Indastriz API"}, value = "none", description = "All API operations combined")
public class ProcessorController {
    protected Logger logger = LoggerFactory.getLogger(ProcessorController.class);

    private final WebClient webClient;

    public ProcessorController(WebClient webClient) {
        this.webClient = webClient;
    }

    /*****************
     **** CATALOG ****
     *****************/

    @ApiOperation(value = "Create new offer in the Catalog", tags = "Catalog")
    @PostMapping("/catalog/offers")
    @ResponseStatus(HttpStatus.CREATED)
    @Logged(messageBefore = "Received request to create new Offer in catalog...", messageAfter = "Offer created.", startFromNewLine = true)
    public OfferDTO createOffer(@RequestBody OfferDTO offerDTO) {
        return webClient.createOffer(offerDTO);
    }

    @ApiOperation(value = "Get offer from the Catalog", tags = "Catalog")
    @GetMapping("/catalog/offers/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Logged(messageBefore = "Received request to retrieve offer by id...", messageAfter = "Offer retrieved.", startFromNewLine = true)
    public OfferDTO getOfferById(@PathVariable("id") Long id) {
        return webClient.findOfferById(id);
    }

    @ApiOperation(value = "Get all offers (filtered or not) from the Catalog", tags = "Catalog",
            notes = "If all request params are null, all the offers will retrieved (unfiltered). \n" +
                    "Otherwise only one of non-null params will be used as filter. So, for the best performance, specify only one thing that you are most interested in.")
    @GetMapping("/catalog/offers")
    @ResponseStatus(HttpStatus.OK)
    @Logged(messageBefore = "Received request to retrieve multiple offers...", messageAfter = "Offers retrieved.", startFromNewLine = true)
    public List<OfferDTO> getAllOffers(@RequestParam(name = "category", required = false) String category, @RequestParam(name = "tags", required = false) List<String> tags, @RequestParam(name = "minPrice", required = false) Double minPrice, @RequestParam(name = "maxPrice", required = false) Double maxPrice) {
        logger.info("Filter: category={}, tags={}, price_range=[{}, {}]", category, tags, minPrice, maxPrice);
        return webClient.findAllOffers(category, tags, minPrice, maxPrice);
    }

    @ApiOperation(value = "Update offer's name and price in the Catalog", tags = "Catalog")
    @PutMapping("/catalog/offers/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Logged(messageBefore = "Received request to update offer (name and price)...", messageAfter = "Offer was updated.", startFromNewLine = true)
    public OfferDTO updateOfferNameAndPrice(@PathVariable("id") Long id, @RequestBody OfferDTO offerDTO) {
        return webClient.updateOfferNameAndPrice(id, offerDTO);
    }

    @ApiOperation(value = "Add tags to the offer in the Catalog", tags = "Catalog")
    @PostMapping("/catalog/offers/{id}/tags")
    @ResponseStatus(HttpStatus.OK)
    @Logged(messageBefore = "Received request to add tags to offer...", messageAfter = "Offer was updated.", startFromNewLine = true)
    public OfferDTO addTagsToOffer(@PathVariable("id") Long id, @RequestBody List<String> tags) {
        return webClient.addTagsToOffer(id, tags);
    }

    @ApiOperation(value = "Remove tags from the offer in the Catalog", tags = "Catalog")
    @DeleteMapping("/catalog/offers/{id}/tags")
    @ResponseStatus(HttpStatus.OK)
    @Logged(messageBefore = "Received request to remove tags from offer...", messageAfter = "Offer was updated.", startFromNewLine = true)
    public OfferDTO removeTagsFromOffer(@PathVariable("id") Long id, @RequestBody List<String> tags) {
        return webClient.removeTagsFromOffer(id, tags);
    }

    @ApiOperation(value = "Change offer's category in the Catalog", tags = "Catalog")
    @PutMapping("/catalog/offers/{id}/category")
    @ResponseStatus(HttpStatus.OK)
    @Logged(messageBefore = "Received request to change offer's category...", messageAfter = "Offer was updated.", startFromNewLine = true)
    public OfferDTO changeOfferCategory(@PathVariable("id") Long id, @RequestBody CategoryDTO categoryDTO) {
        return webClient.changeOfferCategory(id, categoryDTO);
    }

    @ApiOperation(value = "Delete offer from the Catalog", tags = "Catalog")
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


    /*****************************
     **** CUSTOMER-MANAGEMENT ****
     *****************************/

    @ApiOperation(value = "Sign-up new customer account", tags = "Customer-Management")
    @PostMapping("/customers")
    @ResponseStatus(HttpStatus.CREATED)
    @Logged(messageBefore = "Received request to sign-up new Customer...", messageAfter = "Customer account was created.", startFromNewLine = true)
    public CustomerDTO signUp(@RequestBody CustomerDTO customerDTO) {
        return webClient.signUpUsingEmail(customerDTO);
    }

    @ApiOperation(value = "Get customer by email or id", tags = "Customer-Management")
    @GetMapping("/customers/{emailOrId}")
    @ResponseStatus(HttpStatus.OK)
    @Logged(messageBefore = "Received request to find Customer...", messageAfter = "Customer retrieved.", startFromNewLine = true)
    public CustomerDTO findCustomer(@PathVariable("emailOrId") String emailOrId) {
        return webClient.findCustomer(emailOrId);
    }

    @ApiOperation(value = "Get all currently signed-up customers", tags = "Customer-Management")
    @GetMapping("/customers")
    @ResponseStatus(HttpStatus.OK)
    @Logged(messageBefore = "Received request to find all Customers...", messageAfter = "Customers retrieved.", startFromNewLine = true)
    public List<CustomerDTO> getAllCustomers() {
        return webClient.findAllCustomers();
    }

    @ApiOperation(value = "Get customer account details by id", tags = "Customer-Management")
    @PutMapping("/customers/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Logged(messageBefore = "Received request to update Customer's personal data...", messageAfter = "Customer was updated.", startFromNewLine = true)
    public CustomerDTO editCustomerNameAndAge(@PathVariable("id") Long id, @RequestBody CustomerDTO customerDTO) {
        return webClient.editCustomer(id, customerDTO);
    }

    @ApiOperation(value = "Delete customer account from the system", tags = "Customer-Management")
    @DeleteMapping("/customers/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Logged(messageBefore = "Received request to delete Customer...", messageAfter = "Customer account deleted.", startFromNewLine = true)
    public void deleteCustomer(@PathVariable("id") Long id) {
        webClient.deleteCustomer(id);
    }


    /*******************
     **** INVENTORY ****
     *******************/

    @ApiOperation(value = "Pack new order and save it in the Inventory", tags = "Inventory")
    @PostMapping("/inventory/orders")
    @ResponseStatus(HttpStatus.CREATED)
    @Logged(messageBefore = "Received request to create new Order...", messageAfter = "Order packed.", startFromNewLine = true)
    public VerboseOrderDTO packNewOrder(@RequestBody NewOrderDTO newOrderDTO) {
        return webClient.createOrder(newOrderDTO);
    }

    @ApiOperation(value = "Get order by the id from the Inventory", tags = "Inventory")
    @GetMapping("/inventory/orders/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Logged(messageBefore = "Received request to retrieve order by id...", messageAfter = "Order retrieved.", startFromNewLine = true)
    public VerboseOrderDTO getOrderDetails(@PathVariable("id") Long id) {
        return webClient.findOrder(id);
    }

    @ApiOperation(value = "Get all orders by the payment status from the Inventory", tags = "Inventory")
    @GetMapping("/inventory/orders")
    @ResponseStatus(HttpStatus.OK)
    @Logged(messageBefore = "Received request to all orders by payment status...", messageAfter = "Orders found.", startFromNewLine = true)
    public List<SimplifiedOrderDTO> findAllOrdersByPaymentStatus(@RequestParam("isPaid") Boolean isPaid) {
        return webClient.findAllOrdersByPaymentStatus(isPaid);
    }

    @ApiOperation(value = "Get all orders coupled with email from the Inventory", tags = "Inventory")
    @GetMapping("/inventory/customers/{email}/orders")
    @ResponseStatus(HttpStatus.OK)
    @Logged(messageBefore = "Received request to find all orders coupled with the email...", messageAfter = "Orders found.", startFromNewLine = true)
    public List<SimplifiedOrderDTO> getAllOrdersByEmail(@PathVariable("email") String email) {
        return webClient.findAllOrdersByEmail(email);
    }

    @ApiOperation(value = "Get total money spent by the customer", tags = "Inventory")
    @GetMapping("/inventory/customers/{email}/orders/totalPrice")
    @ResponseStatus(HttpStatus.OK)
    @Logged(messageBefore = "Received request to get total money spent by Customer...", messageAfter = "Response retrieved.", startFromNewLine = true)
    public Double getTotalMoneySpentByCustomer(@PathVariable("email") String email) {
        return webClient.getTotalMoneySpentByCustomer(email);
    }

    @ApiOperation(value = "Get total items bought by the customer", tags = "Inventory")
    @GetMapping("/inventory/customers/{email}/orders/itemCount")
    @ResponseStatus(HttpStatus.OK)
    @Logged(messageBefore = "Received request to get count off all bought items by Customer...", messageAfter = "Response retrieved.", startFromNewLine = true)
    public Long getTotalItemCountBoughtByCustomer(@PathVariable("email") String email) {
        return webClient.getTotalItemCountBoughtByCustomer(email);
    }

    @ApiOperation(value = "Set order PaymentStatus=\'payed\'", tags = "Inventory")
    @PutMapping("/inventory/orders/{id}/pay")
    @ResponseStatus(HttpStatus.OK)
    @Logged(messageBefore = "Received request to Order as paid...", messageAfter = "Order payment status updated.", startFromNewLine = true)
    public SimplifiedOrderDTO confirmPaymentForOrder(@PathVariable("id") Long id) {
        return webClient.confirmPaymentForOrder(id);
    }

    @ApiOperation(value = "Update order status", tags = "Inventory")
    @PutMapping("/inventory/orders/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Logged(messageBefore = "Received request to update Order's status...", messageAfter = "Order status updated.", startFromNewLine = true)
    public SimplifiedOrderDTO changeOrderStatus(@PathVariable("id") Long id, @RequestBody NewOrderDTO orderDTO) {
        return webClient.changeOrderStatus(id, orderDTO.getOrderStatus());
    }

    @ApiOperation(value = "Add items from the Catalog to the order in the Inventory", tags = "Inventory")
    @PostMapping("/inventory/orders/{id}/items")
    @ResponseStatus(HttpStatus.CREATED)
    @Logged(messageBefore = "Received request to add items to the Order...", messageAfter = "Items were added.", startFromNewLine = true)
    public VerboseOrderDTO addItemsToOrder(@PathVariable("id") Long orderId, @RequestParam("id") List<Long> offerIds) {
        return webClient.addItemsToOrder(orderId, offerIds);
    }

    @ApiOperation(value = "Remove items from the order in the Inventory", tags = "Inventory")
    @DeleteMapping("/inventory/orders/{id}/items")
    @ResponseStatus(HttpStatus.OK)
    @Logged(messageBefore = "Received request to remove items from the Order...", messageAfter = "Items were removed.", startFromNewLine = true)
    public VerboseOrderDTO removeItemsFromOrder(@PathVariable("id") Long orderId, @RequestParam("id") List<Long> itemIds) {
        return webClient.removeItemsFromOrder(orderId, itemIds);
    }
}
