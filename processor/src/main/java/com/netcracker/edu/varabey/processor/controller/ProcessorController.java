package com.netcracker.edu.varabey.processor.controller;

import com.netcracker.edu.varabey.processor.controller.client.WebClient;
import com.netcracker.edu.varabey.processor.controller.dto.CategoryDTO;
import com.netcracker.edu.varabey.processor.controller.dto.CustomerDTO;
import com.netcracker.edu.varabey.processor.controller.dto.OfferDTO;
import com.netcracker.edu.varabey.processor.controller.dto.domainspecific.SimplifiedOrderDTO;
import com.netcracker.edu.varabey.processor.controller.dto.domainspecific.VerboseOrderDTO;
import com.netcracker.edu.varabey.processor.springutils.beanannotation.Logged;
import com.netflix.discovery.converters.Auto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static com.netcracker.edu.varabey.processor.controller.ProcessorController.LinkDelegate.*;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
@Api(tags = {"Order-Entry API"}, value = "none", description = "All API operations combined")
public class ProcessorController {
    static class LinkDelegate {

        /*****************
         **** CATALOG ****
         *****************/

        static Resource<OfferDTO> getCreatedOfferResource(OfferDTO offerDTO) {
            Resource<OfferDTO> resource = new Resource<>(offerDTO);

            resource.add(getSelfLink(offerDTO));
            resource.add(getSameCategoryLink(offerDTO));
            resource.add(getSimilarOffersLink(offerDTO));

            return resource;
        }

        static Resource<OfferDTO> getFoundOfferResource(OfferDTO offerDTO) {
            return getCreatedOfferResource(offerDTO);
        }

        static Resources<Resource<OfferDTO>> getFoundOffersResourceList(List<OfferDTO> offers) {
            List<Resource<OfferDTO>> offerResources = offers.stream()
                    .map(Resource<OfferDTO>::new)
                    .peek(r -> r.add(getSelfLink(r.getContent())))
                    .collect(Collectors.toList());
            return new Resources<>(offerResources);
        }

        static Resource<OfferDTO> getSelfLinkedOfferResource(OfferDTO offerDTO) {
            Resource<OfferDTO> r = new Resource<>(offerDTO);
            r.add(getSelfLink(offerDTO));
            return r;
        }

        static Link getSelfLink(OfferDTO offerDTO) {
            return linkTo(methodOn(ProcessorController.class).getOfferById(offerDTO.getId())).withSelfRel();
        }

        static Link getSameCategoryLink(OfferDTO offerDTO) {
            return linkTo(methodOn(ProcessorController.class).getAllOffers(offerDTO.getCategory(), null, null, null)).withRel("same category").expand();
        }

        static Link getSimilarOffersLink(OfferDTO offerDTO) {
            List<String> tags = new ArrayList<>(offerDTO.getTags());
            Collections.shuffle(tags);
            tags = tags.stream().limit(2).collect(Collectors.toList());
            return linkTo(methodOn(ProcessorController.class)
                    .getAllOffers(null, null, null, null))
                    .withRel("similar offers")
                    .expand(null, String.join(",", tags), null, null);
        }

        /*****************************
         **** CUSTOMER-MANAGEMENT ****
         *****************************/

        static Resource<CustomerDTO> getSelfLinkedCustomerResource(CustomerDTO customerDTO) {
            return new Resource<>(customerDTO, getSelfLink(customerDTO));
        }

        static Resource<CustomerDTO> getFoundCustomerResource(CustomerDTO customerDTO) {
            Resource<CustomerDTO> r = new Resource<>(customerDTO);
            r.add(getSelfLink(customerDTO), getLinkToAllOrders(customerDTO), getTotalMoneySpentLink(customerDTO), getTotalItemsBoughtLink(customerDTO));
            return r;
        }

        static Resources<Resource<CustomerDTO>> getFoundCustomerResourcesList(List<CustomerDTO> customers) {
            return new Resources<>(customers.stream()
                    .map(LinkDelegate::getSelfLinkedCustomerResource)
                    .collect(Collectors.toCollection(ArrayList::new)));
        }

        static Link getSelfLink(CustomerDTO customerDTO) {
            return linkTo(methodOn(ProcessorController.class).findCustomer(null)).withSelfRel().expand(customerDTO.getId());
        }

        static Link getLinkToAllOrders(CustomerDTO customerDTO) {
            return linkTo(methodOn(ProcessorController.class).getAllOrdersByEmail(customerDTO.getEmail())).withRel("all orders");
        }

        static Link getTotalMoneySpentLink(CustomerDTO customerDTO) {
            Method method = null;
            try {
                method = ProcessorController.class.getMethod("getTotalMoneySpentByCustomer", String.class);
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
            return linkTo(method, customerDTO.getEmail()).withRel("total money spent");
        }

        static Link getTotalItemsBoughtLink(CustomerDTO customerDTO) {
            Method method = null;
            try {
                method = ProcessorController.class.getMethod("getTotalItemCountBoughtByCustomer", String.class);
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
            return linkTo(method, customerDTO.getEmail()).withRel("total items bought");
        }

        /*******************
         **** INVENTORY ****
         *******************/

        static Resource<VerboseOrderDTO> getSelfLinkedVerboseOrderResource(VerboseOrderDTO orderDTO) {
            return new Resource<>(orderDTO, getSelfLink(orderDTO));
        }

        static Resource<VerboseOrderDTO> getOrderDetailsResource(VerboseOrderDTO orderDTO) {
            Resource<VerboseOrderDTO> r = getSelfLinkedVerboseOrderResource(orderDTO);
            if (!orderDTO.isPaid()) {
                r.add(
                        getOrderPaymentLink(orderDTO),
                        getLinkToAddOrderItems(orderDTO),
                        getLinkToRemoveOrderItems(orderDTO)
                );
            }
            if (!"DELIVERED".equalsIgnoreCase(orderDTO.getOrderStatus())) {
                r.add(getLinkToSetNextOrderStatus(orderDTO));
                r.add(getLinkToUpdateOrderStatus(orderDTO));
            }

            r.add(getLinkToOtherOrdersByCustomer(orderDTO));

            if (orderDTO.getCustomer().getFio() != null && !orderDTO.getCustomer().getFio().isEmpty()) {
                r.add(getCustomerLink(orderDTO));
            }

            return r;
        }

        static Resources<Resource<SimplifiedOrderDTO>> getSelfLinkedSimpleOrderResourcesList(List<SimplifiedOrderDTO> orders) {
            return new Resources<>(orders
                    .stream()
                    .map(o -> new Resource<>(o, getSelfLink(o)))
                    .collect(Collectors.toList())
            );
        }

        static Resource<SimplifiedOrderDTO> getSelfLinkedSimpleOrderResource(SimplifiedOrderDTO orderDTO) {
            return new Resource<>(orderDTO, getSelfLink(orderDTO));
        }

        static Link getSelfLink(VerboseOrderDTO orderDTO) {
            return linkTo(methodOn(ProcessorController.class).getOrderDetails(orderDTO.getId())).withSelfRel();
        }

        static Link getSelfLink(SimplifiedOrderDTO orderDTO) {
            return linkTo(methodOn(ProcessorController.class).getOrderDetails(orderDTO.getId())).withSelfRel();
        }

        static Link getOrderPaymentLink(VerboseOrderDTO orderDTO) {
            return linkTo(methodOn(ProcessorController.class).confirmPaymentForOrder(orderDTO.getId())).withRel("pay for the order");
        }

        static Link getLinkToAddOrderItems(VerboseOrderDTO orderDTO) {
            return linkTo(methodOn(ProcessorController.class).addItemsToOrder(orderDTO.getId(), null)).withRel("add items");
        }

        static Link getLinkToRemoveOrderItems(VerboseOrderDTO orderDTO) {
            return linkTo(methodOn(ProcessorController.class).removeItemsFromOrder(orderDTO.getId(), null)).withRel("remove items");
        }

        static Link getLinkToSetNextOrderStatus(VerboseOrderDTO orderDTO) {
            return linkTo(methodOn(ProcessorController.class).setNextOrderStatus(orderDTO.getId())).withRel("set next status");
        }

        static Link getLinkToUpdateOrderStatus(VerboseOrderDTO orderDTO) {
            return linkTo(methodOn(ProcessorController.class).changeOrderStatus(orderDTO.getId(), new SimplifiedOrderDTO())).withRel("change status");
        }

        static Link getCustomerLink(VerboseOrderDTO orderDTO) {
            return linkTo(methodOn(ProcessorController.class).findCustomer(orderDTO.getCustomer().getEmail())).withRel("customer");
        }

        static Link getLinkToOtherOrdersByCustomer(VerboseOrderDTO orderDTO) {
            return linkTo(methodOn(ProcessorController.class).getAllOrdersByEmail(orderDTO.getCustomer().getEmail())).withRel("other orders by customer");
        }





    }

    private Logger logger = LoggerFactory.getLogger(ProcessorController.class);

    private final WebClient webClient;

    @Autowired
    public ProcessorController(WebClient webClient) {
        this.webClient = webClient;
    }

    /*****************
     **** CATALOG ****
     *****************/

    @ApiOperation(value = "Add a new offer to the Catalog", tags = "Catalog")
    @ApiResponses({
    })
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "/catalog/offers", produces = "application/json", consumes = "application/json")
    @Logged(messageBefore = "Received request to create new Offer in catalog...", messageAfter = "Offer created.", startFromNewLine = true)
    public Resource<OfferDTO> createOffer(
            @ApiParam(value = "Offer object that needs to be added to the Catalog", required = true) @RequestBody OfferDTO offerDTO) {
        return getCreatedOfferResource(webClient.createOffer(offerDTO));
    }

    @ApiOperation(value = "Get offer from the Catalog by ID", tags = "Catalog")
    @ApiResponses({
    })
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/catalog/offers/{id}", produces = "application/json")
    @Logged(messageBefore = "Received request to retrieve offer by id...", messageAfter = "Offer retrieved.", startFromNewLine = true)
    public Resource<OfferDTO> getOfferById(
            @ApiParam(value = "Unique id of an offer", required = true) @PathVariable("id") Long id) {
        return getFoundOfferResource(webClient.findOfferById(id));
    }

    @ApiOperation(
            value = "Get all offers (filtered or not) from the Catalog", tags = "Catalog",
            notes = "Not more than one query param will be taken into account! Shall you specify no filters, you will get all available offers."
    )
    @ApiResponses({
    })
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/catalog/offers", produces = "application/json")
    @Logged(messageBefore = "Received request to retrieve multiple offers...", messageAfter = "Offers retrieved.", startFromNewLine = true)
    public Resources<Resource<OfferDTO>> getAllOffers(
            @ApiParam(value = "Category that that will be present at all found offers") @RequestParam(name = "category", required = false) String category,
            @ApiParam(value = "Array of tags that will be present at all found offers") @RequestParam(name = "tags", required = false) List<String> tags,
            @ApiParam(value = "Minimum price (inclusive), use only in pair with maxPrice") @RequestParam(name = "minPrice", required = false) Double minPrice,
            @ApiParam(value = "Maximum price (inclusive), use only in pair with minPrice") @RequestParam(name = "maxPrice", required = false) Double maxPrice) {
        logger.info("Filter: category={}, tags={}, price_range=[{}, {}]", category, tags, minPrice, maxPrice);
        return getFoundOffersResourceList(webClient.findAllOffers(category, tags, minPrice, maxPrice));
    }

    @ApiOperation(value = "Update offer's name and price", tags = "Catalog")
    @ApiResponses({
    })
    @ResponseStatus(HttpStatus.OK)
    @PutMapping(value = "/catalog/offers/{id}", consumes = "application/json", produces = "application/json")
    @Logged(messageBefore = "Received request to update offer (name and price)...", messageAfter = "Offer was updated.", startFromNewLine = true)
    public Resource<OfferDTO> updateOfferNameAndPrice(
            @ApiParam(value = "ID of the offer that needs to be updated", required = true) @PathVariable("id") Long id,
            @ApiParam(value = "Offer object representing updates to the name and price", required = true) @RequestBody OfferDTO offerDTO) {
        return getSelfLinkedOfferResource(webClient.updateOfferNameAndPrice(id, offerDTO));
    }

    @ApiOperation(value = "Add tags to the offer", tags = "Catalog")
    @ApiResponses({
    })
    @ResponseStatus(HttpStatus.OK)
    @PostMapping(value = "/catalog/offers/{id}/tags", produces = "application/json", consumes = "application/json")
    @Logged(messageBefore = "Received request to add tags to offer...", messageAfter = "Offer was updated.", startFromNewLine = true)
    public Resource<OfferDTO> addTagsToOffer(
            @ApiParam(value = "ID of the offer that needs to be updated", required = true) @PathVariable("id") Long id,
            @ApiParam(value = "Collection of tags that needs to be added to the offer", required = true) @RequestBody List<String> tags) {
        return getSelfLinkedOfferResource(webClient.addTagsToOffer(id, tags));
    }

    @ApiOperation(value = "Remove tags from the offer", tags = "Catalog")
    @ApiResponses({
    })
    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping(value = "/catalog/offers/{id}/tags", produces = "application/json", consumes = "application/json")
    @Logged(messageBefore = "Received request to remove tags from offer...", messageAfter = "Offer was updated.", startFromNewLine = true)
    public Resource<OfferDTO> removeTagsFromOffer(
            @ApiParam(value = "ID of the offer that needs to be updated", required = true) @PathVariable("id") Long id,
            @ApiParam(value = "Collection of tags that needs to be removed from the offer", required = true) @RequestBody List<String> tags) {
        return getSelfLinkedOfferResource(webClient.removeTagsFromOffer(id, tags));
    }

    @ApiOperation(value = "Change offer's category", tags = "Catalog")
    @ApiResponses({
    })
    @ResponseStatus(HttpStatus.OK)
    @PutMapping(value = "/catalog/offers/{id}/category", consumes = "application/json", produces = "application/json")
    @Logged(messageBefore = "Received request to change offer's category...", messageAfter = "Offer was updated.", startFromNewLine = true)
    public Resource<OfferDTO> changeOfferCategory(
            @ApiParam(value = "ID of the offer that needs to be updated", required = true) @PathVariable("id") Long id,
            @ApiParam(value = "Category object with valid name", required = true) @RequestBody CategoryDTO categoryDTO) {
        return getSelfLinkedOfferResource(webClient.changeOfferCategory(id, categoryDTO));
    }

    @ApiOperation(value = "Delete offer from the Catalog", tags = "Catalog")
    @ApiResponses({
    })
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/catalog/offers/{id}")
    @Logged(messageBefore = "Received request to delete offer...", messageAfter = "Offer was deleted.", startFromNewLine = true)
    public void deleteOffer(
            @ApiParam(value = "ID of the offer that needs to be removed", required = true)@PathVariable("id") Long id) {
        webClient.deleteOffer(id);
    }

    /*****************************
     **** CUSTOMER-MANAGEMENT ****
     *****************************/

    @ApiOperation(value = "Sign-up new customer account", tags = "Customer-Management")
    @ApiResponses({
    })
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "/customers", consumes = "application/json", produces = "application/json")
    @Logged(messageBefore = "Received request to sign-up new Customer...", messageAfter = "Customer account was created.", startFromNewLine = true)
    public Resource<CustomerDTO> signUp(
            @ApiParam(value = "Customer Account object that needs to be signed-up", required = true) @RequestBody CustomerDTO customerDTO) {
        return getSelfLinkedCustomerResource(webClient.signUpUsingEmail(customerDTO));
    }

    @ApiOperation(value = "Get customer account details by email or id", tags = "Customer-Management")
    @ApiResponses({
    })
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/customers/{emailOrId}", produces = "application/json")
    @Logged(messageBefore = "Received request to find Customer...", messageAfter = "Customer retrieved.", startFromNewLine = true)
    public Resource<CustomerDTO> findCustomer(
            @ApiParam(value = "Unique identifier of customer account. Can be either email as String or id as Long", required = true) @PathVariable("emailOrId") String emailOrId) {
        return getFoundCustomerResource(webClient.findCustomer(emailOrId));
    }

    @ApiOperation(value = "Get all currently signed-up customers", tags = "Customer-Management")
    @ApiResponses({
    })
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/customers", produces = "application/json")
    @Logged(messageBefore = "Received request to find all Customers...", messageAfter = "Customers retrieved.", startFromNewLine = true)
    public Resources<Resource<CustomerDTO>> getAllCustomers() {
        return getFoundCustomerResourcesList(webClient.findAllCustomers());
    }

    @ApiOperation(value = "Edit customer's personal info", tags = "Customer-Management")
    @ApiResponses({
    })
    @ResponseStatus(HttpStatus.OK)
    @PutMapping(value = "/customers/{id}", produces = "application/json", consumes = "application/json")
    @Logged(messageBefore = "Received request to update Customer's personal data...", messageAfter = "Customer was updated.", startFromNewLine = true)
    public Resource<CustomerDTO> editCustomerNameAndAge(
            @ApiParam(value = "ID of customer account that needs to be updated", required = true) @PathVariable("id") Long id,
            @ApiParam(value = "Customer Account object that might hold updates to the name and aga of the customer", required = true) @RequestBody CustomerDTO customerDTO) {
        return getSelfLinkedCustomerResource(webClient.editCustomer(id, customerDTO));
    }

    @ApiOperation(value = "Delete customer account", tags = "Customer-Management")
    @ApiResponses({
    })
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/customers/{id}")
    @Logged(messageBefore = "Received request to delete Customer...", messageAfter = "Customer account deleted.", startFromNewLine = true)
    public void deleteCustomer(
            @ApiParam(value = "ID of account that needs to be deleted") @PathVariable("id") Long id) {
        webClient.deleteCustomer(id);
    }


    /*******************
     **** INVENTORY ****
     *******************/

    @ApiOperation(value = "Pack new order and save it in the Inventory", tags = "Inventory")
    @ApiResponses({
    })
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "/inventory/orders", consumes = "application/json", produces = "application/json")
    @Logged(messageBefore = "Received request to create new Order...", messageAfter = "Order packed.", startFromNewLine = true)
    public Resource<VerboseOrderDTO> packNewOrder(
            @ApiParam(value = "Order metadata object, that must contain customer's email and ids of offers from the Catalog", required = true) @RequestBody SimplifiedOrderDTO simplifiedOrderDTO) {
        return getSelfLinkedVerboseOrderResource(webClient.createOrder(simplifiedOrderDTO));
    }

    @ApiOperation(value = "Get order by the ID", tags = "Inventory")
    @ApiResponses({
    })
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/inventory/orders/{id}", produces = "application/json")
    @Logged(messageBefore = "Received request to retrieve order by id...", messageAfter = "Order retrieved.", startFromNewLine = true)
    public Resource<VerboseOrderDTO> getOrderDetails(
            @ApiParam(value = "ID of the order that needs to be found", required = true) @PathVariable("id") Long id) {
        return getOrderDetailsResource(webClient.findOrder(id));
    }

    @ApiOperation(value = "Get all orders by the payment status", tags = "Inventory")
    @ApiResponses({
    })
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/inventory/orders", produces = "application/json")
    @Logged(messageBefore = "Received request to all orders by payment status...", messageAfter = "Orders found.", startFromNewLine = true)
    public Resources<Resource<SimplifiedOrderDTO>> findAllOrdersByPaymentStatus(
            @ApiParam(value = "Payment status that might be 'true' for paid orders, orElse 'false'", required = true) @RequestParam("isPaid") Boolean isPaid) {
        return getSelfLinkedSimpleOrderResourcesList(webClient.findAllOrdersByPaymentStatus(isPaid));
    }

    @ApiOperation(
            value = "Get all orders coupled with email", tags = "Inventory",
            notes = "Customer account with given email might be deleted, but Orders will still remain in the Inventory"
    )
    @ApiResponses({
    })
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/inventory/customers/{email}/orders", produces = "application/json")
    @Logged(messageBefore = "Received request to find all orders coupled with the email...", messageAfter = "Orders found.", startFromNewLine = true)
    public Resources<Resource<SimplifiedOrderDTO>> getAllOrdersByEmail(
            @ApiParam(value = "Valid email that will be used as filter", required = true) @PathVariable("email") String email) {
        return getSelfLinkedSimpleOrderResourcesList(webClient.findAllOrdersByEmail(email));
    }

    @ApiOperation(value = "Get total money spent by the customer", tags = "Inventory")
    @ApiResponses({
    })
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/inventory/customers/{email}/orders/totalPrice", produces = "application/json")
    @Logged(messageBefore = "Received request to get total money spent by Customer...", messageAfter = "Response retrieved.", startFromNewLine = true)
    public Double getTotalMoneySpentByCustomer(
            @ApiParam(value = "Valid email whose money need to be calculated", required = true) @PathVariable("email") String email) {
        return webClient.getTotalMoneySpentByCustomer(email);
    }

    @ApiOperation(value = "Get total items bought by the customer", tags = "Inventory")
    @ApiResponses({
    })
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/inventory/customers/{email}/orders/itemCount", produces = "application/json")
    @Logged(messageBefore = "Received request to get count off all bought items by Customer...", messageAfter = "Response retrieved.", startFromNewLine = true)
    public Long getTotalItemCountBoughtByCustomer(
            @ApiParam(value = "Email of the account, whose bought items need to be counted", required = true) @PathVariable("email") String email) {
        return webClient.getTotalItemCountBoughtByCustomer(email);
    }

    @ApiOperation(
            value = "Set order PaymentStatus=\'payed\'", tags = "Inventory",
            notes = "Marks order as paid. Paid order items cannot be modified. No refunds"
    )
    @ApiResponses({
    })
    @ResponseStatus(HttpStatus.OK)
    @PutMapping(value = "/inventory/orders/{id}/pay", produces = "application/json")
    @Logged(messageBefore = "Received request to Order as paid...", messageAfter = "Order payment status updated.", startFromNewLine = true)
    public Resource<SimplifiedOrderDTO> confirmPaymentForOrder(
            @ApiParam(value = "ID of the order that needs to get paid", required = true) @PathVariable("id") Long id) {
        return getSelfLinkedSimpleOrderResource(webClient.confirmPaymentForOrder(id));
    }

    @ApiOperation(value = "Update order status", tags = "Inventory")
    @ApiResponses({
    })
    @ResponseStatus(HttpStatus.OK)
    @PutMapping(value = "/inventory/orders/{id}", produces = "application/json", consumes = "application/json")
    @Logged(messageBefore = "Received request to update Order's status...", messageAfter = "Order status updated.", startFromNewLine = true)
    public Resource<SimplifiedOrderDTO> changeOrderStatus(
            @ApiParam(value = "ID of the order that needs an update", required = true) @PathVariable("id") Long id,
            @ApiParam(value = "Order object that may contain only OrderStatus, as other fields will be ignored. To see what values are valid for OrderStatus consult Model section") @RequestBody SimplifiedOrderDTO orderDTO) {
        return getSelfLinkedSimpleOrderResource(webClient.changeOrderStatus(id, orderDTO.getOrderStatus()));
    }

    @ApiOperation(value = "Set next order status", tags = "Inventory")
    @ApiResponses({
    })
    @ResponseStatus(HttpStatus.OK)
    @PutMapping(value = "/inventory/orders/{id}/nextStatus", produces = "application/json")
    @Logged(messageBefore = "Received request to update Order's status...", messageAfter = "Order status updated.", startFromNewLine = true)
    public Resource<SimplifiedOrderDTO> setNextOrderStatus(
            @ApiParam(value = "ID of the order that needs next status", required = true) @PathVariable("id") Long id) {
        return getSelfLinkedSimpleOrderResource(webClient.setNextOrderStatus(id));
    }

    @ApiOperation(value = "Add items from the Catalog to the order", tags = "Inventory")
    @ApiResponses({
    })
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "/inventory/orders/{id}/items", produces = "application/json")
    @Logged(messageBefore = "Received request to add items to the Order...", messageAfter = "Items were added.", startFromNewLine = true)
    public Resource<VerboseOrderDTO> addItemsToOrder(
            @ApiParam(value = "ID of the order that needs an update", required = true) @PathVariable("id") Long orderId,
            @ApiParam(value = "Collection of IDs of offers that need to be added to the order", required = true) @RequestParam("offerIds") List<Long> offerIds) {
        return getSelfLinkedVerboseOrderResource(webClient.addItemsToOrder(orderId, offerIds));
    }

    @ApiOperation(value = "Remove items from the order", tags = "Inventory")
    @ApiResponses({
    })
    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping(value = "/inventory/orders/{id}/items", produces = "application/json")
    @Logged(messageBefore = "Received request to remove items from the Order...", messageAfter = "Items were removed.", startFromNewLine = true)
    public Resource<VerboseOrderDTO> removeItemsFromOrder(
            @ApiParam(value = "ID of the order that needs an update", required = true) @PathVariable("id") Long orderId,
            @ApiParam(value = "Collection of OrderItem ids that need to be removed from the order", required = true) @RequestParam("itemIds") List<Long> itemIds) {
        return getSelfLinkedVerboseOrderResource(webClient.removeItemsFromOrder(orderId, itemIds));
    }
}
