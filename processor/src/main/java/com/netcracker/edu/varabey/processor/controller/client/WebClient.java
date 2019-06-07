package com.netcracker.edu.varabey.processor.controller.client;

import com.netcracker.edu.varabey.processor.controller.dto.*;
import com.netcracker.edu.varabey.processor.controller.dto.domainspecific.InventoryOrderDTO;
import com.netcracker.edu.varabey.processor.controller.dto.domainspecific.NewOrderDTO;
import com.netcracker.edu.varabey.processor.controller.dto.domainspecific.SimplifiedOrderDTO;
import com.netcracker.edu.varabey.processor.controller.dto.domainspecific.VerboseOrderDTO;
import com.netcracker.edu.varabey.processor.controller.dto.transformer.Transformer;
import com.netcracker.edu.varabey.processor.custom.beanannotation.Logged;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
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

    public WebClient(@Value("${inventory.url}") String inventoryUrl,
                     @Value("${catalog.url}") String catalogUrl,
                     @Value("${customer-management.url}") String customerManagementUrl,
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

    @Logged(messageBefore = "Rerouting request to the Catalog microservice...", messageAfter = "Response retrieved")
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

    @Logged(messageBefore = "Rerouting request to the Catalog microservice...", messageAfter = "Response retrieved")
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

    @Logged(messageBefore = "Rerouting request to the Catalog microservice...", messageAfter = "Response retrieved")
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

    @Logged(messageBefore = "Rerouting request to the Catalog microservice...", messageAfter = "Response retrieved")
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

    @Logged(messageBefore = "Rerouting request to the Catalog microservice...", messageAfter = "Response retrieved")
    public OfferDTO addTagsToOffer(Long id, List<String> tags) {
        UriComponentsBuilder builder = UriComponentsBuilder
                .fromHttpUrl(catalogUrl)
                .path("/offers")
                .path("/" + id)
                .path("/addTags");
        return restTemplate.exchange(
                builder.toUriString(),
                HttpMethod.PUT,
                new HttpEntity<>(tags),
                new ParameterizedTypeReference<OfferDTO>(){}
        ).getBody();
    }

    @Logged(messageBefore = "Rerouting request to the Catalog microservice...", messageAfter = "Response retrieved")
    public OfferDTO removeTagsFromOffer(Long id, List<String> tags) {
        UriComponentsBuilder builder = UriComponentsBuilder
                .fromHttpUrl(catalogUrl)
                .path("/offers")
                .path("/" + id)
                .path("/removeTags");
        return restTemplate.exchange(
                builder.toUriString(),
                HttpMethod.PUT,
                new HttpEntity<>(tags),
                new ParameterizedTypeReference<OfferDTO>(){}
        ).getBody();
    }

    @Logged(messageBefore = "Rerouting request to the Catalog microservice...", messageAfter = "Response retrieved")
    public OfferDTO changeOfferCategory(Long id, CategoryDTO categoryDTO) {
        return restTemplate.exchange(
                UriComponentsBuilder
                        .fromHttpUrl(catalogUrl)
                        .path("/offers")
                        .path("/" + id)
                        .path("/changeCategory")
                        .toUriString(),
                HttpMethod.PUT,
                new HttpEntity<>(categoryDTO),
                new ParameterizedTypeReference<OfferDTO>(){}
        ).getBody();
    }

    public void deleteOffer(Long id) {
        restTemplate.delete(
                UriComponentsBuilder
                        .fromHttpUrl(catalogUrl)
                        .path("/offers")
                        .path("/" + id)
                        .toUriString()
        );
    }

    @Logged(messageBefore = "Rerouting request to the Catalog microservice...", messageAfter = "Response retrieved")
    public CategoryDTO createCategory(CategoryDTO categoryDTO) {
        return restTemplate.exchange(
                UriComponentsBuilder
                        .fromHttpUrl(catalogUrl)
                        .path("/categories")
                        .toUriString(),
                HttpMethod.POST,
                new HttpEntity<>(categoryDTO),
                new ParameterizedTypeReference<CategoryDTO>() {}
        ).getBody();
    }

    @Logged(messageBefore = "Rerouting request to the Catalog microservice...", messageAfter = "Response retrieved")
    public List<CategoryDTO> createAllCategories(List<CategoryDTO> categoryDTOS) {
        return restTemplate.exchange(
                UriComponentsBuilder
                        .fromHttpUrl(catalogUrl)
                        .path("/categories")
                        .path("/saveAll")
                        .toUriString(),
                HttpMethod.POST,
                new HttpEntity<>(categoryDTOS),
                new ParameterizedTypeReference<List<CategoryDTO>>() {}
        ).getBody();
    }

    public CategoryDTO findCategory(String input) {
        return restTemplate.exchange(
                UriComponentsBuilder
                        .fromHttpUrl(catalogUrl)
                        .path("/categories")
                        .path("/" + input)
                        .toUriString(),
                HttpMethod.GET,
                HttpEntity.EMPTY,
                new ParameterizedTypeReference<CategoryDTO>(){}
        ).getBody();
    }

    public CategoryDTO updateCategoryName(Long id, CategoryDTO categoryDTO) {
        return restTemplate.exchange(
                UriComponentsBuilder
                        .fromHttpUrl(catalogUrl)
                        .path("/categories")
                        .path("/" + id)
                        .toUriString(),
                HttpMethod.PUT,
                new HttpEntity<>(categoryDTO),
                new ParameterizedTypeReference<CategoryDTO>(){}
        ).getBody();
    }

    public void deleteCategory(Long id) {
        restTemplate.delete(
                UriComponentsBuilder
                        .fromHttpUrl(catalogUrl)
                        .path("/categories")
                        .path("/" + id)
                        .toUriString()
        );
    }

    public TagDTO createTag(TagDTO tagDTO) {
        return restTemplate.exchange(
                UriComponentsBuilder
                        .fromHttpUrl(catalogUrl)
                        .path("/tags")
                        .toUriString(),
                HttpMethod.POST,
                new HttpEntity<>(tagDTO),
                new ParameterizedTypeReference<TagDTO>() {}
        ).getBody();
    }

    public List<TagDTO> createAllTags(List<TagDTO> tagDTOS) {
        return restTemplate.exchange(
                UriComponentsBuilder
                        .fromHttpUrl(catalogUrl)
                        .path("/tags")
                        .path("/saveAll")
                        .toUriString(),
                HttpMethod.POST,
                new HttpEntity<>(tagDTOS),
                new ParameterizedTypeReference<List<TagDTO>>() {}
        ).getBody();
    }

    public TagDTO findTag(String input) {
        return restTemplate.exchange(
                UriComponentsBuilder
                        .fromHttpUrl(catalogUrl)
                        .path("/tags")
                        .path("/" + input)
                        .toUriString(),
                HttpMethod.GET,
                HttpEntity.EMPTY,
                new ParameterizedTypeReference<TagDTO>(){}
        ).getBody();
    }

    public TagDTO updateTagName(Long id, TagDTO tagDTO) {
        return restTemplate.exchange(
                UriComponentsBuilder
                        .fromHttpUrl(catalogUrl)
                        .path("/tags")
                        .path("/" + id)
                        .toUriString(),
                HttpMethod.PUT,
                new HttpEntity<>(tagDTO),
                new ParameterizedTypeReference<TagDTO>(){}
        ).getBody();
    }

    public void deleteTag(Long id) {
        restTemplate.delete(
                UriComponentsBuilder
                        .fromHttpUrl(catalogUrl)
                        .path("/tags")
                        .path("/" + id)
                        .toUriString()
        );
    }


    @Logged(messageBefore = "Rerouting requst to customer-management server...",
            messageAfter = "Recieved response from customer-management server.")
    public CustomerDTO signUpUsingEmail(CustomerDTO customer) {
        ResponseEntity<CustomerDTO> response = restTemplate.exchange(
                UriComponentsBuilder
                        .fromHttpUrl(customerManagementUrl)
                        .path("/customers")
                        .toUriString(),
                HttpMethod.POST,
                new HttpEntity<>(customer),
                new ParameterizedTypeReference<CustomerDTO>(){}
        );
        return response.getBody();
    }

    public CustomerDTO findCustomer(String query) {
        ResponseEntity<CustomerDTO> response = restTemplate.exchange(
                UriComponentsBuilder
                        .fromHttpUrl(customerManagementUrl)
                        .path("/customers")
                        .path("/" + query)
                        .toUriString(),
                HttpMethod.GET,
                HttpEntity.EMPTY,
                new ParameterizedTypeReference<CustomerDTO>(){});
        return response.getBody();
    }

    public List<CustomerDTO> findAllCustomers() {
        ResponseEntity<List<CustomerDTO>> response = restTemplate.exchange(
                UriComponentsBuilder
                        .fromHttpUrl(customerManagementUrl)
                        .path("/customers")
                        .toUriString(),
                HttpMethod.GET,
                HttpEntity.EMPTY,
                new ParameterizedTypeReference<List<CustomerDTO>>(){});
        return response.getBody();
    }

    public CustomerDTO editCustomer(Long id, CustomerDTO customerDTO) {
        ResponseEntity<CustomerDTO> response = restTemplate.exchange(
                UriComponentsBuilder
                        .fromHttpUrl(customerManagementUrl)
                        .path("/customers")
                        .path("/" + id)
                        .toUriString(),
                HttpMethod.PUT,
                new HttpEntity<>(customerDTO),
                new ParameterizedTypeReference<CustomerDTO>(){}
        );
        return response.getBody();
    }

    public void deleteCustomer(Long id) {
        // all orders coupled with customers email will remain in the database.
        restTemplate.delete(UriComponentsBuilder
                .fromHttpUrl(customerManagementUrl)
                .path("/customers")
                .path("/" + id)
                .toUriString()
        );
    }

    public VerboseOrderDTO createOrder(NewOrderDTO inputOrderInputDTO) {
        CustomerDTO customer = findCustomer(inputOrderInputDTO.getEmail());

        List<OrderItemDTO> items = new ArrayList<>();
        inputOrderInputDTO.getOfferIds().stream()
                .map(this::findOfferById)
                .map(offerToOrderItemTransformer::convert)
                .forEach(items::add);

        InventoryOrderDTO orderDTO = new InventoryOrderDTO();
        orderDTO.setPaid(inputOrderInputDTO.getPaid());
        orderDTO.setOrderStatus(inputOrderInputDTO.getOrderStatus());
        orderDTO.setCreatedOnDate(inputOrderInputDTO.getCreatedOnDate());
        orderDTO.setEmail(customer.getEmail());
        orderDTO.setItems(items);

        ResponseEntity<VerboseOrderDTO> response = restTemplate.exchange(
                UriComponentsBuilder
                        .fromHttpUrl(inventoryUrl)
                        .path("/orders")
                        .toUriString(),
                HttpMethod.POST,
                new HttpEntity<>(orderDTO),
                new ParameterizedTypeReference<VerboseOrderDTO>(){}
        );
        response.getBody().setCustomer(customer);
        return response.getBody();
    }

    public VerboseOrderDTO findOrder(Long id) {
        ResponseEntity<InventoryOrderDTO> response = restTemplate.exchange(
                UriComponentsBuilder.fromHttpUrl(inventoryUrl)
                        .path("/orders")
                        .path("/" + id)
                        .toUriString(),
                HttpMethod.GET,
                HttpEntity.EMPTY,
                new ParameterizedTypeReference<InventoryOrderDTO>() {}
        );
        InventoryOrderDTO inventoryOrder = response.getBody();
        VerboseOrderDTO processorOrder = verboseOrderTransformer.convert(inventoryOrder);

        CustomerDTO customerDTO = findCustomer(inventoryOrder.getEmail());
        processorOrder.setCustomer(customerDTO);
        return processorOrder;
    }

    public List<SimplifiedOrderDTO> findAllOrdersByPaymentStatus(Boolean isPaid) {
        ResponseEntity<List<InventoryOrderDTO>> response = restTemplate.exchange(
                UriComponentsBuilder
                        .fromHttpUrl(inventoryUrl)
                        .path("/orders")
                        .toUriString(),
                HttpMethod.GET,
                HttpEntity.EMPTY,
                new ParameterizedTypeReference<List<InventoryOrderDTO>>() {}
        );
        return response.getBody().stream()
                .filter(order -> order.isPaid() == isPaid)
                .map(simpleOrderTransformer::convert)
                .collect(Collectors.toList());
    }

    public List<SimplifiedOrderDTO> findAllOrdersByEmail(String email) {
        ResponseEntity<List<InventoryOrderDTO>> response = restTemplate.exchange(
                UriComponentsBuilder
                        .fromHttpUrl(inventoryUrl)
                        .path("/orders")
                        .toUriString(),
                HttpMethod.GET,
                HttpEntity.EMPTY,
                new ParameterizedTypeReference<List<InventoryOrderDTO>>() {}
        );
        return response.getBody().stream()
                .map(simpleOrderTransformer::convert)
                .filter(order -> order.getEmail().equals(email))
                .collect(Collectors.toList());
    }

    public Double getEmailSpendings(String email) {
        List<SimplifiedOrderDTO> orders = findAllOrdersByEmail(email);
        return orders.stream()
                .mapToDouble(SimplifiedOrderDTO::getTotalPrice)
                .reduce(0, Double::sum);
    }

    public Integer getEmailOrderCount(String email) {
        List<SimplifiedOrderDTO> orders = findAllOrdersByEmail(email);
        return orders.size();
    }

    public SimplifiedOrderDTO confirmPaymentForOrder(Long id) {
        SimplifiedOrderDTO orderWithPayment = new SimplifiedOrderDTO();
        orderWithPayment.setPaid(true);

        ResponseEntity<InventoryOrderDTO> response = restTemplate.exchange(
                UriComponentsBuilder
                        .fromHttpUrl(inventoryUrl)
                        .path("/orders")
                        .path("/" + id)
                        .toUriString(),
                HttpMethod.PUT,
                new HttpEntity<>(orderWithPayment),
                new ParameterizedTypeReference<InventoryOrderDTO>(){}
        );

        return simpleOrderTransformer.convert(response.getBody());
    }

    public SimplifiedOrderDTO changeOrderStatus(Long id, String status) {
        SimplifiedOrderDTO orderWithStatus = new SimplifiedOrderDTO();
        orderWithStatus.setOrderStatus(status);

        ResponseEntity<InventoryOrderDTO> response = restTemplate.exchange(
                UriComponentsBuilder
                        .fromHttpUrl(inventoryUrl)
                        .path("/orders")
                        .path("/" + id)
                        .toUriString(),
                HttpMethod.PUT,
                new HttpEntity<>(orderWithStatus),
                new ParameterizedTypeReference<InventoryOrderDTO>(){}
        );

        return simpleOrderTransformer.convert(response.getBody());
    }

    public SimplifiedOrderDTO addItemsToOrder(Long orderId, List<Long> offerIds) {
        List<OrderItemDTO> items = offerIds.stream()
                .map(this::findOfferById)
                .map(offerToOrderItemTransformer::convert)
                .collect(Collectors.toList());
        ResponseEntity<InventoryOrderDTO> response = restTemplate.exchange(
                UriComponentsBuilder.
                        fromHttpUrl(inventoryUrl)
                        .path("/orders")
                        .path("/" + orderId)
                        .path("/items")
                        .toUriString(),
                HttpMethod.POST,
                new HttpEntity<>(items),
                new ParameterizedTypeReference<InventoryOrderDTO>() {}
        );
        return simpleOrderTransformer.convert(response.getBody());
    }

    public SimplifiedOrderDTO removeItemsFromOrder(Long orderId, List<Long> itemIds) {
        VerboseOrderDTO orderDTO = findOrder(orderId);
        if (orderDTO.isPaid()) {
            // TODO use grown up validation
            throw new RuntimeException("don't delete items form paid order");
        }
        ResponseEntity<InventoryOrderDTO> response = restTemplate.exchange(
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
        );
        return simpleOrderTransformer.convert(response.getBody());
    }
}