package com.netcracker.edu.varabey.customers.controller;

import com.netcracker.edu.varabey.customers.controller.dto.CustomerDTO;
import com.netcracker.edu.varabey.customers.controller.dto.transformer.Transformer;
import com.netcracker.edu.varabey.customers.entity.Customer;
import com.netcracker.edu.varabey.customers.service.CustomerService;
import com.netcracker.edu.varabey.customers.springutils.beanannotation.Logged;
import com.netcracker.edu.varabey.customers.validation.CustomerValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/customers")
public class CustomerController {
    private CustomerService customerService;
    private Transformer<Customer, CustomerDTO> customerTransformer;
    private final CustomerValidator customerValidator;

    @Autowired
    public CustomerController(CustomerService customerService, Transformer<Customer, CustomerDTO> customerTransformer, CustomerValidator customerValidator) {
        this.customerService = customerService;
        this.customerTransformer = customerTransformer;
        this.customerValidator = customerValidator;
    }

    @Logged(messageBefore = "Creating customer...", messageAfter = "Customer created.", startFromNewLine = true)
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CustomerDTO saveCustomer(@RequestBody CustomerDTO dto) {
        Customer newCustomer = customerTransformer.toEntity(dto);
        newCustomer = customerService.save(newCustomer);
        return customerTransformer.toDto(newCustomer);
    }

    @Logged(messageBefore = "Searching customer...",
            messageAfter = "Customer found.",
            startFromNewLine = true)
    @GetMapping("/{idOrEmail}")
    @ResponseStatus(HttpStatus.OK)
    public CustomerDTO findCustomer(@PathVariable("idOrEmail") String input) {
        if (input.matches("\\d+")) {
            return findCustomerById(Long.parseLong(input));
        } else {
            return findCustomerByEmail(input);
        }
    }

    private CustomerDTO findCustomerById(Long id) {
        Customer customer = customerValidator.checkFoundById(customerService.find(id), id);
        return customerTransformer.toDto(customer);
    }

    private CustomerDTO findCustomerByEmail(String email) {
        Customer customer = customerValidator.checkFoundByEmail(customerService.findByEmail(email), email);
        return customerTransformer.toDto(customer);
    }

    @Logged(messageBefore = "Getting all customers...",
            messageAfter = "All customers acquired.",
            startFromNewLine = true)
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<CustomerDTO> findAllCustomers() {
        return customerService.findAll().stream()
                .map(customerTransformer::toDto)
                .collect(Collectors.toList());
    }

    @Logged(messageBefore = "Editing customer...",
            messageAfter = "Changes to customer saved.",
            startFromNewLine = true)
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CustomerDTO updateCustomer(@PathVariable("id") Long id, @RequestBody CustomerDTO dto) {
        Customer customer = customerTransformer.toEntity(dto);
        customer.setId(id);
        customer = customerService.update(customer);
        return customerTransformer.toDto(customer);
    }

    @Logged(messageBefore = "Deleting customer...",
            messageAfter = "Customer deleted.",
            startFromNewLine = true)
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCustomer(@PathVariable("id") Long id) {
        customerService.delete(id);
    }
}