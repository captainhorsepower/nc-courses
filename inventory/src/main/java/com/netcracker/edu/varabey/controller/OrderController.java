package com.netcracker.edu.varabey.controller;

import com.netcracker.edu.varabey.controller.dto.OrderDTO;
import com.netcracker.edu.varabey.controller.dto.OrderItemDTO;
import com.netcracker.edu.varabey.controller.dto.transformer.Transformer;
import com.netcracker.edu.varabey.controller.util.FilterDTO;
import com.netcracker.edu.varabey.entity.*;
import com.netcracker.edu.varabey.service.CategoryService;
import com.netcracker.edu.varabey.service.CustomerService;
import com.netcracker.edu.varabey.service.OrderService;
import com.netcracker.edu.varabey.service.TagService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.netcracker.edu.varabey.controller.util.RestPreconditions.checkFound;

@RestController
@RequestMapping("/orders")
public class OrderController {
    private final OrderService orderService;
    private final CategoryService categoryService;
    private final TagService tagService;
    private final CustomerService customerService;
    private final Transformer<Order, OrderDTO> orderTransformer;
    private final Transformer<OrderItem, OrderItemDTO> itemTransformer;

    public OrderController(OrderService orderService, CategoryService categoryService, TagService tagService, CustomerService customerService, Transformer<Order, OrderDTO> orderTransformer, Transformer<OrderItem, OrderItemDTO> itemTransformer) {
        this.orderService = orderService;
        this.categoryService = categoryService;
        this.tagService = tagService;
        this.customerService = customerService;
        this.orderTransformer = orderTransformer;
        this.itemTransformer = itemTransformer;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OrderDTO saveOrder(@RequestBody OrderDTO dto) {
        Order order = orderTransformer.toEntity(dto);
        order = orderService.save(order);
        return orderTransformer.toDto(order);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public OrderDTO findOrder(@PathVariable("id") Long id) {
        Order order = checkFound(orderService.findById(id));
        return orderTransformer.toDto(order);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<OrderDTO> findAllOrders() {
        return orderService.findAll().stream()
                .map(orderTransformer::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/filtered")
    @ResponseStatus(HttpStatus.OK)
    public List<OrderItemDTO> findItemsByCustomerAndTag(@RequestBody FilterDTO filter) {
        if ((filter.getCustomerId() == null && filter.getEmail() == null) || (filter.getTag() == null && filter.getCategory() == null)) {
            throw new IllegalArgumentException("bad filter. Impossible to find anything");
        }

        Customer customer = null;
        if (filter.getEmail() != null) {
            customer = customerService.findByEmail(filter.getEmail());
        } else if (filter.getCustomerId() != null) {
            customer = customerService.findCustomer(filter.getCustomerId());
        }
        checkFound(customer);

        List<OrderItem> filteredItems;
        if (filter.getCategory() != null) {
            Category category = checkFound(categoryService.findCategory(filter.getCategory()));
            filteredItems = orderService.findAllOrderItemsByCustomerAndCategory(customer, category);
        } else {
            Tag tag = checkFound(tagService.findByName(filter.getTag()));
            filteredItems = orderService.findAllOrderItemsByCustomerAndTag(customer, tag);
        }

        return filteredItems.stream()
                .map(itemTransformer::toDto)
                .collect(Collectors.toList());
    }

    @PutMapping("/{id}/addItems")
    @ResponseStatus(HttpStatus.OK)
    public OrderDTO addItemsToOrder(@PathVariable("id") Long id, @RequestBody List<OrderItemDTO> itemDTOs) {
        List<OrderItem> items = itemDTOs.stream().map(itemTransformer::toEntity).collect(Collectors.toList());
        Order order = orderService.addItems(id, items);
        return orderTransformer.toDto(order);
    }

    @PutMapping("/{id}/removeItems")
    @ResponseStatus(HttpStatus.OK)
    public OrderDTO removeItemsFromOrder(@PathVariable("id") Long id, @RequestBody List<OrderItemDTO> itemsDTOs) {
        List<OrderItem> items = itemsDTOs.stream().map(itemTransformer::toEntity).collect(Collectors.toList());
        Order order = orderService.removeItems(id, items);
        return orderTransformer.toDto(order);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public OrderDTO updateOrderStatusAndPaymentStatus(@PathVariable("id") Long id, @RequestBody OrderDTO dto) {
        Order order = orderTransformer.toEntity(dto);
        order = orderService.updatePaymentAndStatus(order);
        return orderTransformer.toDto(order);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteOrder(@PathVariable("id") Long id) {
        orderService.deleteOrder(id);
    }
}
