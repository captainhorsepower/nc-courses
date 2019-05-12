package com.netcracker.edu.varabey.controller;

import com.netcracker.edu.varabey.controller.dto.CustomerDTO;
import com.netcracker.edu.varabey.controller.dto.transformator.Transformer;
import com.netcracker.edu.varabey.entity.Customer;
import com.netcracker.edu.varabey.service.CustomerManagementService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/customers")
public class CustomerManagementController {
    private CustomerManagementService service;
    private Transformer<Customer, CustomerDTO> transformer;

    public CustomerManagementController(CustomerManagementService service, Transformer<Customer, CustomerDTO> transformer) {
        this.service = service;
        this.transformer = transformer;
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CustomerDTO findCustomer(@PathVariable(value = "id") Long id) {
        Customer customer = service.find(id);
        if (customer == null) {
            throw new RuntimeException("customer not found");
        }
        return transformer.toDto(customer);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<CustomerDTO> findAllCustomers() {
        return service.findAll().stream()
                .map(transformer::toDto)
                .collect(Collectors.toList());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CustomerDTO saveCustomer(@RequestBody CustomerDTO dto) {
        Customer newCustomer = transformer.toEntity(dto);
        newCustomer = service.save(newCustomer);
        return transformer.toDto(newCustomer);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CustomerDTO updateCustomer(@PathVariable("id") Long id, @RequestBody CustomerDTO dto) {
        Customer customer = transformer.toEntity(dto);
        customer.setId(id);
        customer = service.update(customer);
        return transformer.toDto(customer);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCustomer(@PathVariable("id") Long id) {
        service.delete(id);
    }


}
