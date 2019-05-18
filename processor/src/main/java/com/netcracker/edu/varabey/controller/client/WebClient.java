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

    public List<OfferDTO> findAllOffers() {
        ResponseEntity<List<OfferDTO>> response = restTemplate.exchange(
                catalogUrl + "/offers",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<OfferDTO>>(){});
        return response.getBody();
    }

    public OfferDTO findOfferById(Long id) {
        return restTemplate.exchange(
                catalogUrl + "/offers/" + id,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<OfferDTO>(){})
                .getBody();
    }

    public List<CustomerDTO> findAllCustomers() {
        ResponseEntity<List<CustomerDTO>> response = restTemplate.exchange(
                customerManagementUrl + "/customers",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<CustomerDTO>>(){});
        return response.getBody();
    }

    public CustomerDTO findCustomer(String email) {
        ResponseEntity<CustomerDTO> response = restTemplate.exchange(
                customerManagementUrl + "/customers/" + email,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<CustomerDTO>(){});
        return response.getBody();
    }

    public CustomerDTO signUpWithEmail(CustomerDTO customer) {
        return restTemplate.exchange(
                customerManagementUrl + "/customers",
                HttpMethod.POST,
                new HttpEntity<>(customer),
                new ParameterizedTypeReference<CustomerDTO>(){}
                ).getBody();
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