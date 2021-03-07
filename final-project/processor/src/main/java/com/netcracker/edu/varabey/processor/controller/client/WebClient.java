package com.netcracker.edu.varabey.processor.controller.client;

import com.netcracker.edu.varabey.processor.controller.dto.CategoryDTO;
import com.netcracker.edu.varabey.processor.controller.dto.CustomerDTO;
import com.netcracker.edu.varabey.processor.controller.dto.OfferDTO;
import com.netcracker.edu.varabey.processor.controller.dto.OrderItemDTO;
import com.netcracker.edu.varabey.processor.controller.dto.domainspecific.InventoryOrderDTO;
import com.netcracker.edu.varabey.processor.controller.dto.domainspecific.SimplifiedOrderDTO;
import com.netcracker.edu.varabey.processor.controller.dto.domainspecific.VerboseOrderDTO;
import com.netcracker.edu.varabey.processor.controller.dto.transformer.Transformer;
import com.netcracker.edu.varabey.processor.springutils.beanannotation.Logged;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class WebClient {
    private final RestTemplate restTemplate;
    private final String inventoryUrl;
    private final String catalogUrl;
    private final String customerManagementUrl;
    private final Transformer<OfferDTO, OrderItemDTO> offerToOrderItemTransformer;
    private final Transformer<InventoryOrderDTO, VerboseOrderDTO> verboseOrderTransformer;
    private final Transformer<InventoryOrderDTO, SimplifiedOrderDTO> simpleOrderTransformer;

    protected Logger log = LoggerFactory.getLogger(WebClient.class);

    public WebClient(@Value("${microservices.inventory.base-url}") String inventoryUrl,
                     @Value("${microservices.catalog.base-url}") String catalogUrl,
                     @Value("${microservices.customer-management.base-url}") String customerManagementUrl,
                     RestTemplate restTemplate,
                     Transformer<OfferDTO, OrderItemDTO> offerToOrderItemTransformer,
                     Transformer<InventoryOrderDTO, VerboseOrderDTO> verboseOrderTransformer,
                     Transformer<InventoryOrderDTO, SimplifiedOrderDTO> simpleOrderTransformer) {
        this.inventoryUrl = inventoryUrl;
        this.catalogUrl = catalogUrl;
        this.customerManagementUrl = customerManagementUrl;
        this.restTemplate = restTemplate;
        this.offerToOrderItemTransformer = offerToOrderItemTransformer;
        this.verboseOrderTransformer = verboseOrderTransformer;
        this.simpleOrderTransformer = simpleOrderTransformer;
    }

    /*****************
     **** CATALOG ****
     *****************/

    @Logged(messageBefore = "Rerouting request to the Catalog microservice...", messageAfter = "Response retrieved.")
    public OfferDTO createOffer(OfferDTO offerDTO) {
        return restTemplate.exchange(
                UriComponentsBuilder
                        .fromHttpUrl(catalogUrl)
                        .path("/offers")
                        .toUriString(),
                HttpMethod.POST,
                new HttpEntity<>(offerDTO),
                new ParameterizedTypeReference<OfferDTO>() {}
        ).getBody();
    }

    @Logged(messageBefore = "Rerouting request to the Catalog microservice...", messageAfter = "Response retrieved.")
    public List<OfferDTO> findAllOffers(String category, List<String> tags, Double minPrice, Double maxPrice) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(catalogUrl + "/offers")
                .queryParam("category", category)
                .queryParam("tags", (tags == null) ? null : tags.toArray())
                .queryParam("minPrice", minPrice)
                .queryParam("maxPrice", maxPrice);
        return restTemplate.exchange(
                builder.toUriString(),
                HttpMethod.GET,
                HttpEntity.EMPTY,
                new ParameterizedTypeReference<List<OfferDTO>>(){})
                .getBody();
    }

    @Logged(messageBefore = "Rerouting request to the Catalog microservice...", messageAfter = "Response retrieved.")
    public OfferDTO findOfferById(Long id) {
        return restTemplate.exchange(
                UriComponentsBuilder
                        .fromHttpUrl(catalogUrl)
                        .path("/offers")
                        .path("/" + id)
                        .toUriString(),
                HttpMethod.GET,
                HttpEntity.EMPTY,
                new ParameterizedTypeReference<OfferDTO>(){})
                .getBody();
    }

    @Logged(messageBefore = "Rerouting request to the Catalog microservice...", messageAfter = "Response retrieved.")
    public OfferDTO updateOfferNameAndPrice(Long id, OfferDTO offerDTO) {
        UriComponentsBuilder builder = UriComponentsBuilder
                .fromHttpUrl(catalogUrl)
                .path("/offers")
                .path("/" + id);
        return restTemplate.exchange(
                builder.toUriString(),
                HttpMethod.PUT,
                new HttpEntity<>(offerDTO),
                new ParameterizedTypeReference<OfferDTO>(){}
        ).getBody();
    }

    @Logged(messageBefore = "Rerouting request to the Catalog microservice...", messageAfter = "Response retrieved.")
    public OfferDTO addTagsToOffer(Long id, List<String> tags) {
        UriComponentsBuilder builder = UriComponentsBuilder
                .fromHttpUrl(catalogUrl)
                .path("/offers")
                .path("/" + id)
                .path("/tags");
        return restTemplate.exchange(
                builder.toUriString(),
                HttpMethod.POST,
                new HttpEntity<>(tags),
                new ParameterizedTypeReference<OfferDTO>(){}
        ).getBody();
    }

    @Logged(messageBefore = "Rerouting request to the Catalog microservice...", messageAfter = "Response retrieved.")
    public OfferDTO removeTagsFromOffer(Long id, List<String> tags) {
        UriComponentsBuilder builder = UriComponentsBuilder
                .fromHttpUrl(catalogUrl)
                .path("/offers")
                .path("/" + id)
                .path("/tags");
        return restTemplate.exchange(
                builder.toUriString(),
                HttpMethod.DELETE,
                new HttpEntity<>(tags),
                new ParameterizedTypeReference<OfferDTO>(){}
        ).getBody();
    }

    @Logged(messageBefore = "Rerouting request to the Catalog microservice...", messageAfter = "Response retrieved.")
    public OfferDTO changeOfferCategory(Long id, CategoryDTO categoryDTO) {
        return restTemplate.exchange(
                UriComponentsBuilder
                        .fromHttpUrl(catalogUrl)
                        .path("/offers")
                        .path("/" + id)
                        .path("/category")
                        .toUriString(),
                HttpMethod.PUT,
                new HttpEntity<>(categoryDTO),
                new ParameterizedTypeReference<OfferDTO>(){}
        ).getBody();
    }

    @Logged(messageBefore = "Rerouting request to the Catalog microservice...", messageAfter = "Done.")
    public void deleteOffer(Long id) {
        restTemplate.delete(
                UriComponentsBuilder
                        .fromHttpUrl(catalogUrl)
                        .path("/offers")
                        .path("/" + id)
                        .toUriString()
        );
    }

    /*****************************
     **** CUSTOMER-MANAGEMENT ****
     *****************************/

    @Logged(messageBefore = "Rerouting request to the Customer-Management microservice...", messageAfter = "Response retrieved.")
    public CustomerDTO signUpUsingEmail(CustomerDTO customer) {
        return restTemplate.exchange(
                UriComponentsBuilder
                        .fromHttpUrl(customerManagementUrl)
                        .path("/customers")
                        .toUriString(),
                HttpMethod.POST,
                new HttpEntity<>(customer),
                new ParameterizedTypeReference<CustomerDTO>(){}
            ).getBody();
    }

    @Logged(messageBefore = "Rerouting request to the Customer-Management microservice...", messageAfter = "Response retrieved.")
    public CustomerDTO findCustomer(String query) {
        return restTemplate.exchange(
                UriComponentsBuilder
                        .fromHttpUrl(customerManagementUrl)
                        .path("/customers")
                        .path("/" + query)
                        .toUriString(),
                HttpMethod.GET,
                HttpEntity.EMPTY,
                new ParameterizedTypeReference<CustomerDTO>(){}
            ).getBody();
    }

    @Logged(messageBefore = "Rerouting request to the Customer-Management microservice...", messageAfter = "Response retrieved.")
    public List<CustomerDTO> findAllCustomers() {
        return restTemplate.exchange(
                UriComponentsBuilder
                        .fromHttpUrl(customerManagementUrl)
                        .path("/customers")
                        .toUriString(),
                HttpMethod.GET,
                HttpEntity.EMPTY,
                new ParameterizedTypeReference<List<CustomerDTO>>(){}
            ).getBody();
    }

    @Logged(messageBefore = "Rerouting request to the Customer-Management microservice...", messageAfter = "Response retrieved.")
    public CustomerDTO editCustomer(Long id, CustomerDTO customerDTO) {
        return restTemplate.exchange(
                UriComponentsBuilder
                        .fromHttpUrl(customerManagementUrl)
                        .path("/customers")
                        .path("/" + id)
                        .toUriString(),
                HttpMethod.PUT,
                new HttpEntity<>(customerDTO),
                new ParameterizedTypeReference<CustomerDTO>(){}
        ).getBody();
    }

    @Logged(messageBefore = "Rerouting request to the Customer-Management microservice...", messageAfter = "Response retrieved.")
    public void deleteCustomer(Long id) {
        // all orders coupled with customers email will remain in the database.
        restTemplate.delete(UriComponentsBuilder
                .fromHttpUrl(customerManagementUrl)
                .path("/customers")
                .path("/" + id)
                .toUriString()
        );
    }


    /*******************
     **** INVENTORY ****
     *******************/


    public VerboseOrderDTO createOrder(SimplifiedOrderDTO simplifiedOrderDTO) {
        log.info("Retrieving customer account from Customer-Management microservice...");
        CustomerDTO customer = findCustomer(simplifiedOrderDTO.getEmail());

        log.info("Parsing Offers to OrderItems...");
        List<OrderItemDTO> items = simplifiedOrderDTO.getOfferIds().stream()
                .map(this::findOfferById)
                .map(offerToOrderItemTransformer::convert)
                .collect(Collectors.toList());

        InventoryOrderDTO orderDTO = new InventoryOrderDTO();
        orderDTO.setPaid(simplifiedOrderDTO.isPaid());
        orderDTO.setOrderStatus(simplifiedOrderDTO.getOrderStatus());
        orderDTO.setCreatedOnDate(simplifiedOrderDTO.getCreatedOnDate());
        orderDTO.setEmail(customer.getEmail());
        orderDTO.setItems(items);

        log.info("Rerouting request to the Inventory microservice...");
        VerboseOrderDTO verboseOrderDTO = verboseOrderTransformer.convert(
                restTemplate.exchange(
                        UriComponentsBuilder
                                .fromHttpUrl(inventoryUrl)
                                .path("/orders")
                                .toUriString(),
                        HttpMethod.POST,
                        new HttpEntity<>(orderDTO),
                        new ParameterizedTypeReference<InventoryOrderDTO>() {
                        }
                ).getBody()
        );
        log.info("Response successfully retrieved.");

        verboseOrderDTO.setCustomer(customer);
        return verboseOrderDTO;
    }

    public VerboseOrderDTO findOrder(Long id) {
        log.info("Rerouting request to the Inventory microservice...");
        InventoryOrderDTO inventoryOrder = restTemplate.exchange(
                UriComponentsBuilder.fromHttpUrl(inventoryUrl)
                        .path("/orders")
                        .path("/" + id)
                        .toUriString(),
                HttpMethod.GET,
                HttpEntity.EMPTY,
                new ParameterizedTypeReference<InventoryOrderDTO>() {}
        ).getBody();
        log.info("Response successfully retrieved.");

        VerboseOrderDTO processorOrder = verboseOrderTransformer.convert(inventoryOrder);

        processorOrder.setCustomer(findCustomerForVerboseOrder(inventoryOrder.getEmail()));
        return processorOrder;
    }

    protected CustomerDTO findCustomerForVerboseOrder(String email) {
        log.info("Retrieving customer from Customer-Management microservice...");
        CustomerDTO customerDTO;
        try {
            customerDTO = findCustomer(email);
            log.info("Customer retrieved.");
        } catch (RuntimeException e) {
            log.info("Customer account with given email was deleted.");
            customerDTO = new CustomerDTO();
            customerDTO.setEmail(email);
            customerDTO.setFio("\'personal data was removed.\'");
        }

        return customerDTO;
    }

    @Logged(messageBefore = "Rerouting request to the Inventory microservice...", messageAfter = "Response retrieved.")
    public List<SimplifiedOrderDTO> findAllOrdersByPaymentStatus(Boolean isPaid) {
        return restTemplate.exchange(
                UriComponentsBuilder
                        .fromHttpUrl(inventoryUrl)
                        .path("/orders")
                        .queryParam("paid", isPaid)
                        .toUriString(),
                HttpMethod.GET,
                HttpEntity.EMPTY,
                new ParameterizedTypeReference<List<InventoryOrderDTO>>() {}
        ).getBody().stream()
                .map(simpleOrderTransformer::convert)
                .collect(Collectors.toList());
    }

    @Logged(messageBefore = "Rerouting request to the Inventory microservice...", messageAfter = "Response retrieved.")
    public List<SimplifiedOrderDTO> findAllOrdersByEmail(String email) {
        return restTemplate.exchange(
                UriComponentsBuilder
                        .fromHttpUrl(inventoryUrl)
                        .path("/orders")
                        .queryParam("email", email)
                        .toUriString(),
                HttpMethod.GET,
                HttpEntity.EMPTY,
                new ParameterizedTypeReference<List<InventoryOrderDTO>>() {}
        ).getBody().stream()
                .map(simpleOrderTransformer::convert)
                .filter(order -> order.getEmail().equals(email))
                .collect(Collectors.toList());
    }

    public Double getTotalMoneySpentByCustomer(String email) {
        return restTemplate.exchange(
                UriComponentsBuilder
                        .fromHttpUrl(inventoryUrl)
                        .path("/orders/metadata/totalPrice")
                        .queryParam("email", email)
                        .toUriString(),
                HttpMethod.GET,
                HttpEntity.EMPTY,
                new ParameterizedTypeReference<Double>() {}
        ).getBody();
    }

    public Long getTotalItemCountBoughtByCustomer(String email) {
        return restTemplate.exchange(
                UriComponentsBuilder
                        .fromHttpUrl(inventoryUrl)
                        .path("/orders/metadata/itemCount")
                        .queryParam("email", email)
                        .toUriString(),
                HttpMethod.GET,
                HttpEntity.EMPTY,
                new ParameterizedTypeReference<Long>() {}
        ).getBody();
    }

    @Logged(messageBefore = "Rerouting request to the Inventory microservice...", messageAfter = "Response retrieved.")
    public SimplifiedOrderDTO confirmPaymentForOrder(Long id) {
        SimplifiedOrderDTO orderWithPayment = new SimplifiedOrderDTO();
        orderWithPayment.setPaid(true);

        return simpleOrderTransformer.convert(restTemplate.exchange(
                UriComponentsBuilder
                        .fromHttpUrl(inventoryUrl)
                        .path("/orders")
                        .path("/" + id)
                        .toUriString(),
                HttpMethod.PUT,
                new HttpEntity<>(orderWithPayment),
                new ParameterizedTypeReference<InventoryOrderDTO>(){}
            ).getBody()
        );
    }


    @Logged(messageBefore = "Rerouting request to the Inventory microservice...", messageAfter = "Response retrieved.")
    public SimplifiedOrderDTO changeOrderStatus(Long id, String status) {
        SimplifiedOrderDTO orderWithStatus = new SimplifiedOrderDTO();
        orderWithStatus.setOrderStatus(status);

        return simpleOrderTransformer.convert(restTemplate.exchange(
                UriComponentsBuilder
                        .fromHttpUrl(inventoryUrl)
                        .path("/orders")
                        .path("/" + id)
                        .toUriString(),
                HttpMethod.PUT,
                new HttpEntity<>(orderWithStatus),
                new ParameterizedTypeReference<InventoryOrderDTO>(){}
            ).getBody()
        );
    }

    @Logged(messageAfter = "Response retrieved.")
    public VerboseOrderDTO addItemsToOrder(Long orderId, List<Long> offerIds) {

        log.info("Parsing offers to OrderItems...");
        List<OrderItemDTO> items = offerIds.stream()
                .map(this::findOfferById)
                .map(offerToOrderItemTransformer::convert)
                .collect(Collectors.toList());

        log.info("Rerouting request to the Inventory microservice...");
        return verboseOrderTransformer.convert(restTemplate.exchange(
                UriComponentsBuilder.
                        fromHttpUrl(inventoryUrl)
                        .path("/orders")
                        .path("/" + orderId)
                        .path("/items")
                        .toUriString(),
                HttpMethod.POST,
                new HttpEntity<>(items),
                new ParameterizedTypeReference<InventoryOrderDTO>() {}
            ).getBody()
        );
    }

    @Logged(messageBefore = "Rerouting request to the Inventory microservice...", messageAfter = "Response retrieved.")
    public VerboseOrderDTO removeItemsFromOrder(Long orderId, List<Long> itemIds) {
        return verboseOrderTransformer.convert(restTemplate.exchange(
                UriComponentsBuilder.
                        fromHttpUrl(inventoryUrl)
                        .path("/orders")
                        .path("/" + orderId)
                        .path("/items")
                        .queryParam("id", itemIds.stream()
                                .map(Object::toString)
                                .collect(Collectors.joining(",")))
                        .toUriString(),
                HttpMethod.DELETE,
                HttpEntity.EMPTY,
                new ParameterizedTypeReference<InventoryOrderDTO>() {}
            ).getBody()
        );
    }

    public SimplifiedOrderDTO setNextOrderStatus(Long id) {
        return simpleOrderTransformer.convert(restTemplate.exchange(
                UriComponentsBuilder.
                        fromHttpUrl(inventoryUrl)
                        .path("/orders")
                        .path("/" + id)
                        .path("/nextStatus")
                        .toUriString(),
                HttpMethod.PUT,
                HttpEntity.EMPTY,
                new ParameterizedTypeReference<InventoryOrderDTO>() {}
                ).getBody()
        );
    }
}