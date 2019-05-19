package com.netcracker.edu.varabey.controller.client;

import com.netcracker.edu.varabey.dto.*;
import com.netcracker.edu.varabey.dto.input.OrderInputDTO;
import com.netcracker.edu.varabey.dto.transformer.OfferDTOOrderItemDTOTransformer;
import com.netcracker.edu.varabey.dto.transformer.Transformer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class WebClient {
    private final RestTemplate restTemplate;
    private final String inventoryUrl;
    private final String catalogUrl;
    private final String customerManagementUrl;
    private final Transformer<OfferDTO, OrderItemDTO> offerToOrderItemTransformer;

    public WebClient(@Value("${inventory.url}") String inventoryUrl, @Value("${catalog.url}") String catalogUrl,
                     @Value("${customer-management.url}") String customerManagementUrl, RestTemplate restTemplate, ResponseErrorHandler restTemplateResponseErrorHandler, OfferDTOOrderItemDTOTransformer offerToOrderItemTransformer) {
        this.inventoryUrl = inventoryUrl;
        this.catalogUrl = catalogUrl;
        this.customerManagementUrl = customerManagementUrl;
        this.restTemplate = restTemplate;
        this.offerToOrderItemTransformer = offerToOrderItemTransformer;
        this.restTemplate.setErrorHandler(restTemplateResponseErrorHandler);
    }

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

    public com.netcracker.edu.varabey.dto.OrderDTO createOrder(OrderInputDTO inputOrderInputDTO) {
        CustomerDTO customer = findCustomer(inputOrderInputDTO.getEmail());

        Set<OrderItemDTO> items = new HashSet<>();
        inputOrderInputDTO.getOfferIds().stream()
                .map(this::findOfferById)
                .map(offerToOrderItemTransformer::convert)
                .forEach(items::add);


        com.netcracker.edu.varabey.dto.OrderDTO orderDTO = new com.netcracker.edu.varabey.dto.OrderDTO();
        orderDTO.setPaid(inputOrderInputDTO.getPaid());
        orderDTO.setOrderStatus(inputOrderInputDTO.getOrderStatus());
        orderDTO.setCreatedOnDate(inputOrderInputDTO.getCreatedOnDate());
        orderDTO.setCustomer(customer);
        orderDTO.setItems(items);

        orderDTO = restTemplate.exchange(
                inventoryUrl + "/orders",
                HttpMethod.POST,
                new HttpEntity<>(orderDTO),
                new ParameterizedTypeReference<com.netcracker.edu.varabey.dto.OrderDTO>(){})
                .getBody();
        return orderDTO;
    }
//
//    private Set<OfferDTO> getOffersByIds(Set<Long> itemIds) {
//    }
//
//    public OrderInputDTO getOrderById(Long id) {
//        return restTemplate.exchange(inventoryUrl + "/orders/" + id,
//                HttpMethod.GET,
//                null,
//                new ParameterizedTypeReference<>(){}).getBody();
//    }
//
//    public List<OrderInputDTO> getAllOrders() {
//        return restTemplate.exchange(inventoryUrl + "/orders",
//                HttpMethod.GET,
//                null,
//                new ParameterizedTypeReference<>(){})
//                .getBody();
//    }
//
//    public OrderInputDTO addItemToOrder(Long orderId, Long itemId) {
//        return restTemplate.exchange(inventoryUrl + "/orders/" + orderId + "/items",
//                HttpMethod.PUT,
//                new HttpEntity<>(offerToOrderItemTransformer.convert(findOfferById(itemId))),
//                new ParameterizedTypeReference<>(){}).getBody();
//    }
//
//    public void removeItemFromOrder(Long orderId, Long itemId) {
//        restTemplate.exchange(inventoryUrl + "/orders/" + orderId + "/items/" + itemId,
//                HttpMethod.DELETE,
//                null,
//                new ParameterizedTypeReference<>(){}
//                ).getBody();
//    }
//
//    public OrderInputDTO payForOrder(Long orderId) {
//        return restTemplate.exchange(inventoryUrl + "/orders/" + orderId + "/pay",
//                HttpMethod.PUT,
//                null,
//                new ParameterizedTypeReference<>(){})
//                .getBody();
//    }
//
//    public List<OrderInputDTO> getAllOrdersByPaymentStatus(String status) {
//        return restTemplate.exchange(inventoryUrl + "/statuses/" + status,
//                HttpMethod.GET,
//                null,
//                new ParameterizedTypeReference<List<OrderInputDTO>>(){})
//                .getBody();
//    }
//
//    public List<OrderInputDTO> getAllOrdersByEmail(String email) {
//        return restTemplate.exchange(inventoryUrl + "/emails/" + email,
//                HttpMethod.GET,
//                null,
//                new ParameterizedTypeReference<List<OrderInputDTO>>(){})
//                .getBody();
//    }

//    public Integer getAmountOfItemsBoughtByCustomerWithEmail(String email) {
//        return restTemplate.exchange(inventoryUrl + "/emails/" + email + "/amount",
//                HttpMethod.GET,
//                null,
//                new ParameterizedTypeReference<Integer>(){}() {
//                }).getBody();
//    }
//
//    public Double GetFullPriceOfItemsBoughtByCustomerWithEmail(String email) {
//        return restTemplate.exchange(inventoryUrl + "/emails/" + email + "/full-price",
//                HttpMethod.GET,
//                null,
//                new ParameterizedTypeReference<>(){}() {
//                }).getBody();
//    }
//
//    public OrderInputDTO setNextOrderStatus(Long orderId) {
//        return restTemplate.exchange(inventoryUrl + "/orders/" + orderId + "/status/next",
//                HttpMethod.PUT,
//                null,
//                new ParameterizedTypeReference<>(){}())
//                .getBody();
//    }
//
//    private OrderInputDTO saveOrder(OrderInputDTO orderDTO) {
//        orderDTO = restTemplate.exchange(
//                inventoryUrl + "/orders",
//                HttpMethod.POST,
//                new HttpEntity<>(orderDTO),
//                new ParameterizedTypeReference<>(){}() {
//                }
//        ).getBody();
//        return orderDTO;
//    }
//
//    private Set<OfferDTO> getOffersByIds(Set<Long> ids) {
//        return ids.stream()
//                .map(this::findOfferById)
//                .collect(Collectors.toSet());
//    }
}