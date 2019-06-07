package com.netcracker.edu.varabey.inventory.controller;

import com.netcracker.edu.varabey.inventory.controller.dto.OrderDTO;
import com.netcracker.edu.varabey.inventory.controller.dto.OrderItemDTO;
import com.netcracker.edu.varabey.inventory.controller.dto.transformer.Transformer;
import com.netcracker.edu.varabey.inventory.data.entity.*;
import com.netcracker.edu.varabey.inventory.data.service.CategoryService;
import com.netcracker.edu.varabey.inventory.data.service.CustomerService;
import com.netcracker.edu.varabey.inventory.data.service.OrderService;
import com.netcracker.edu.varabey.inventory.data.service.TagService;
import com.netcracker.edu.varabey.inventory.util.aop.beanannotation.Logged;
import com.netcracker.edu.varabey.inventory.validation.CategoryValidator;
import com.netcracker.edu.varabey.inventory.validation.CustomerValidator;
import com.netcracker.edu.varabey.inventory.validation.OrderValidator;
import com.netcracker.edu.varabey.inventory.validation.TagValidator;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping
public class OrderController {
    private final OrderService orderService;
    private final CategoryService categoryService;
    private final TagService tagService;
    private final CustomerService customerService;
    private final Transformer<Order, OrderDTO> orderTransformer;
    private final Transformer<OrderItem, OrderItemDTO> itemTransformer;
    private final OrderValidator orderValidator;
    private final CustomerValidator customerValidator;
    private final TagValidator tagValidator;
    private final CategoryValidator categoryValidator;

    public OrderController(OrderService orderService, CategoryService categoryService, TagService tagService, CustomerService customerService, Transformer<Order, OrderDTO> orderTransformer, Transformer<OrderItem, OrderItemDTO> itemTransformer, OrderValidator orderValidator, CustomerValidator customerValidator, TagValidator tagValidator, CategoryValidator categoryValidator) {
        this.orderService = orderService;
        this.categoryService = categoryService;
        this.tagService = tagService;
        this.customerService = customerService;
        this.orderTransformer = orderTransformer;
        this.itemTransformer = itemTransformer;
        this.orderValidator = orderValidator;
        this.customerValidator = customerValidator;
        this.tagValidator = tagValidator;
        this.categoryValidator = categoryValidator;
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
    public List<OrderDTO> findAllOrders() {
        return orderService.findAll().stream()
                .map(orderTransformer::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/customers/{email}/orders/items")
    @ResponseStatus(HttpStatus.OK)
    public List<OrderItemDTO> findItemsByCustomerAndTag(
            @PathVariable(name = "email") String email,
            @RequestParam(name = "tag", required = false) String tagName,
            @RequestParam(name = "category", required = false) String categoryName
            ) {
        Customer customer = customerValidator.checkFoundByEmail(customerService.findByEmail(email), email);

        List<OrderItem> filteredItems;
        if (categoryName != null && !categoryName.isEmpty()) {
            Category category = categoryValidator.checkFoundByName(categoryService.findCategory(categoryName), categoryName);
            filteredItems = orderService.findAllOrderItemsByCustomerAndCategory(customer, category);
        } else if (tagName != null && !tagName.isEmpty()) {
            Tag tag = tagValidator.checkFoundByName(tagService.findByName(tagName), tagName);
            filteredItems = orderService.findAllOrderItemsByCustomerAndTag(customer, tag);
        } else {
            filteredItems = orderService.findAllOrderItemsByCustomer(customer);
        }

        return filteredItems.stream()
                .map(itemTransformer::toDto)
                .collect(Collectors.toList());
    }

    @PostMapping("/orders/{id}/items")
    @ResponseStatus(HttpStatus.OK)
    public OrderDTO addItemsToOrder(@PathVariable("id") Long id, @RequestBody List<OrderItemDTO> itemDTOs) {
        List<OrderItem> items = itemDTOs.stream().map(itemTransformer::toEntity).collect(Collectors.toList());
        Order order = orderService.addItems(id, items);
        return orderTransformer.toDto(order);
    }

    @DeleteMapping("/orders/{id}/items")
    @ResponseStatus(HttpStatus.OK)
    public OrderDTO removeItemsFromOrder(
            @PathVariable("id") Long id,
            @RequestParam("id") Set<Long> itemIds) {
        Order order = orderService.removeItems(id, itemIds);
        return orderTransformer.toDto(order);
    }

    @PutMapping("/orders/{id}")
    @ResponseStatus(HttpStatus.OK)
    public OrderDTO updateOrderStatusAndPaymentStatus(@PathVariable("id") Long id, @RequestBody OrderDTO dto) {
        Order order = orderTransformer.toEntity(dto);
        order = orderService.updatePaymentAndStatus(id, order);
        return orderTransformer.toDto(order);
    }

    @DeleteMapping("/orders/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteOrder(@PathVariable("id") Long id) {
        orderService.deleteOrder(id);
    }
}
