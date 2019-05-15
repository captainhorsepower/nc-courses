package com.netcracker.edu.varabey.controller;

import com.netcracker.edu.varabey.controller.dto.CustomerDTO;
import com.netcracker.edu.varabey.controller.dto.transformer.Transformer;
import com.netcracker.edu.varabey.entity.Customer;
import com.netcracker.edu.varabey.service.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static com.netcracker.edu.varabey.controller.util.RestPreconditions.checkFound;

@RestController
@RequestMapping("/customers")
public class CustomerController {
    private CustomerService customerService;
    private Transformer<Customer, CustomerDTO> customerTransformer;

    public CustomerController(CustomerService customerService, Transformer<Customer, CustomerDTO> customerTransformer) {
        this.customerService = customerService;
        this.customerTransformer = customerTransformer;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CustomerDTO saveCustomer(@RequestBody CustomerDTO dto) {
        Customer newCustomer = customerTransformer.toEntity(dto);
        newCustomer = customerService.save(newCustomer);
        return customerTransformer.toDto(newCustomer);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CustomerDTO findCustomer(@PathVariable(value = "id") Long id) {
        Customer customer = checkFound(customerService.find(id));
        return customerTransformer.toDto(customer);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<CustomerDTO> findAllCustomers() {
        return customerService.findAll().stream()
                .map(customerTransformer::toDto)
                .collect(Collectors.toList());
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CustomerDTO updateCustomer(@PathVariable("id") Long id, @RequestBody CustomerDTO dto) {
        Customer customer = customerTransformer.toEntity(dto);
        customer.setId(id);
        customer = customerService.update(customer);
        return customerTransformer.toDto(customer);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCustomer(@PathVariable("id") Long id) {
        customerService.delete(id);
    }
}
