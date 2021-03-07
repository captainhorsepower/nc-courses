package com.netcracker.edu.varabey.inventory.web.controller;

import com.netcracker.edu.varabey.inventory.data.entity.Order;
import com.netcracker.edu.varabey.inventory.data.entity.OrderItem;
import com.netcracker.edu.varabey.inventory.springutils.beanannotation.Logged;
import com.netcracker.edu.varabey.inventory.web.controller.dto.OrderDTO;
import com.netcracker.edu.varabey.inventory.web.controller.dto.OrderItemDTO;
import com.netcracker.edu.varabey.inventory.web.controller.dto.transformer.Transformer;
import com.netcracker.edu.varabey.inventory.web.service.OrderService;
import com.netcracker.edu.varabey.inventory.web.validation.OrderValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping
public class OrderController {
    private final OrderService orderService;
    private final Transformer<Order, OrderDTO> orderTransformer;
    private final Transformer<OrderItem, OrderItemDTO> itemTransformer;
    private final OrderValidator orderValidator;

    protected Logger logger = LoggerFactory.getLogger(OrderController.class);

    public OrderController(OrderService orderService, Transformer<Order, OrderDTO> orderTransformer, Transformer<OrderItem, OrderItemDTO> itemTransformer, OrderValidator orderValidator) {
        this.orderService = orderService;
        this.orderTransformer = orderTransformer;
        this.itemTransformer = itemTransformer;
        this.orderValidator = orderValidator;
    }

    @PostMapping("/orders")
    @ResponseStatus(HttpStatus.CREATED)
    @Logged(messageBefore = "Received request to create new Order...", messageAfter = "Order posted.", startFromNewLine = true)
    public OrderDTO saveOrder(@RequestBody OrderDTO dto) {
        Order order = orderTransformer.toEntity(dto);
        order = orderService.save(order);
        return orderTransformer.toDto(order);
    }

    @GetMapping("/orders/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Logged(messageBefore = "Received request to retrieve order by id...", messageAfter = "Order retrieved.", startFromNewLine = true)
    public OrderDTO findOrder(@PathVariable("id") Long id) {
        Order order = orderValidator.checkFoundById(orderService.findById(id), id);
        return orderTransformer.toDto(order);
    }

    @GetMapping("/orders")
    @ResponseStatus(HttpStatus.OK)
    @Logged(messageBefore = "Received request to retrieve multiple orders...", messageAfter = "Orders retrieved.", startFromNewLine = true)
    public List<OrderDTO> findAllOrders(@RequestParam(name = "email", required = false) String coupledEmail, @RequestParam(name = "paid", required = false) Boolean isPaid) {
        logger.info("Filter: email=\'{}\', isPaid={}", coupledEmail, isPaid);

        List<Order> orders;

        if (coupledEmail != null) {
            orders = orderService.findAllOrdersByEmail(coupledEmail);
        } else if (isPaid != null) {
            orders = orderService.findAllOrdersByPaymentStatus(isPaid);
        } else {
            orders = orderService.findAll();
        }

        return orders.stream()
                .map(orderTransformer::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/orders/items")
    @ResponseStatus(HttpStatus.OK)
    @Logged(messageBefore = "Received request to retrieve OrderItems coupled with email...", messageAfter = "OrderItems retrieved.", startFromNewLine = true)
    public List<OrderItemDTO> findItemsByCustomerAndTag(@RequestParam(name = "email") String email, @RequestParam(name = "tags", required = false) List<String> tagNames, @RequestParam(name = "category", required = false) String categoryName) {

        logger.info("Filter: email=\'{}\', tags=\'{}\', category=\'{}\'", email, tagNames, categoryName);

        List<OrderItem> filteredItems;

        email = email.toLowerCase();

        if (categoryName != null && !categoryName.isEmpty()) {
            filteredItems = orderService.findAllOrderItemsByEmailAndCategory(email, categoryName);
        } else if (tagNames != null && !tagNames.isEmpty()) {
            filteredItems = orderService.findAllOrderItemsByEmailAndTags(email, tagNames);
        } else {
            filteredItems = orderService.findAllOrderItemsByEmail(email);
        }

        return filteredItems.stream()
                .map(itemTransformer::toDto)
                .collect(Collectors.toList());
    }

    @PostMapping("/orders/{id}/items")
    @ResponseStatus(HttpStatus.OK)
    @Logged(messageBefore = "Received request to add Items to the Order...", messageAfter = "Order updated.", startFromNewLine = true)
    public OrderDTO addItemsToOrder(@PathVariable("id") Long id, @RequestBody List<OrderItemDTO> itemDTOs) {
        List<OrderItem> items = itemDTOs.stream().map(itemTransformer::toEntity).collect(Collectors.toList());
        Order order = orderService.addItems(id, items);
        return orderTransformer.toDto(order);
    }

    @DeleteMapping("/orders/{id}/items")
    @ResponseStatus(HttpStatus.OK)
    @Logged(messageBefore = "Received request to remove Items the Order...", messageAfter = "Order updated.", startFromNewLine = true)
    public OrderDTO removeItemsFromOrder(
            @PathVariable("id") Long id,
            @RequestParam("id") Set<Long> itemIds) {
        Order order = orderService.removeItems(id, itemIds);
        return orderTransformer.toDto(order);
    }

    @PutMapping("/orders/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Logged(messageBefore = "Received request to update order statuses...", messageAfter = "Order updated.", startFromNewLine = true)
    public OrderDTO updateOrderStatusAndPaymentStatus(@PathVariable("id") Long id, @RequestBody OrderDTO dto) {
        Order order = orderTransformer.toEntity(dto);
        order = orderService.updatePaymentAndStatus(id, order);
        return orderTransformer.toDto(order);
    }

    @PutMapping("/orders/{id}/nextStatus")
    @ResponseStatus(HttpStatus.OK)
    @Logged(messageBefore = "Received request to update order statuses...", messageAfter = "Order updated.", startFromNewLine = true)
    public OrderDTO setNextOrderStatus(@PathVariable("id") Long id) {
        return orderTransformer.toDto(orderService.setNextOrderStatus(id));
    }

    @DeleteMapping("/orders/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Logged(messageBefore = "Received request to delete Order by id...", messageAfter = "Order was deleted.", startFromNewLine = true)
    public void deleteOrder(@PathVariable("id") Long id) {
        orderService.deleteOrder(id);
    }

    @GetMapping("/orders/metadata/totalPrice")
    @ResponseStatus(HttpStatus.OK)
    public Double getTotalMoneySpendByCustomer(@RequestParam(name = "email") String email) {
        return orderService.getTotalMoneySpendByEmail(email);
    }

    @GetMapping("/orders/metadata/itemCount")
    @ResponseStatus(HttpStatus.OK)
    public Long getItemCountBoughtByCustomer(@RequestParam(name = "email") String email) {
        return orderService.getItemCountBoughtByEmail(email);
    }
}
