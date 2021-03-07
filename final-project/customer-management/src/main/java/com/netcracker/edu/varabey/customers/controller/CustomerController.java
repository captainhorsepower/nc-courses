package com.netcracker.edu.varabey.customers.controller;

import com.netcracker.edu.varabey.customers.controller.dto.CustomerDTO;
import com.netcracker.edu.varabey.customers.controller.dto.transformer.Transformer;
import com.netcracker.edu.varabey.customers.entity.Customer;
import com.netcracker.edu.varabey.customers.service.CustomerService;
import com.netcracker.edu.varabey.customers.springutils.beanannotation.Logged;
import com.netcracker.edu.varabey.customers.validation.util.CustomerValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    protected Logger logger = LoggerFactory.getLogger(CustomerController.class);

    @Autowired
    public CustomerController(CustomerService customerService, Transformer<Customer, CustomerDTO> customerTransformer, CustomerValidator customerValidator) {
        this.customerService = customerService;
        this.customerTransformer = customerTransformer;
        this.customerValidator = customerValidator;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Logged(messageBefore = "Received request to sign-up new Customer...", messageAfter = "Customer account was created.", startFromNewLine = true)
    public CustomerDTO saveCustomer(@RequestBody CustomerDTO dto) {
        Customer newCustomer = customerTransformer.toEntity(dto);
        newCustomer = customerService.save(newCustomer);
        return customerTransformer.toDto(newCustomer);
    }

    @GetMapping("/{idOrEmail}")
    @ResponseStatus(HttpStatus.OK)
    @Logged(messageBefore = "Received request to find Customer...", messageAfter = "Customer retrieved.", startFromNewLine = true)
    public CustomerDTO findCustomer(@PathVariable("idOrEmail") String input) {
        if (input.matches("\\d+")) {
            logger.info("searching by \'{}\'", "id");
            return findCustomerById(Long.parseLong(input));
        } else {
            logger.info("searching by \'{}\'", "email");
            return findCustomerByEmail(input);
        }
    }

    protected CustomerDTO findCustomerById(Long id) {
        Customer customer = customerValidator.checkFoundById(customerService.find(id), id);
        return customerTransformer.toDto(customer);
    }

    protected CustomerDTO findCustomerByEmail(String email) {
        Customer customer = customerValidator.checkFoundByEmail(customerService.findByEmail(email.toLowerCase()), email);
        return customerTransformer.toDto(customer);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Logged(messageBefore = "Received request to find all Customers...", messageAfter = "Customers retrieved.", startFromNewLine = true)
    public List<CustomerDTO> findAllCustomers() {
        return customerService.findAll().stream()
                .map(customerTransformer::toDto)
                .collect(Collectors.toList());
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Logged(messageBefore = "Received request to update Customer's personal data...", messageAfter = "Customer was updated.", startFromNewLine = true)
    public CustomerDTO updateCustomer(@PathVariable("id") Long id, @RequestBody CustomerDTO dto) {
        Customer customer = customerTransformer.toEntity(dto);
        customer.setId(id);
        customer = customerService.update(customer);
        return customerTransformer.toDto(customer);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Logged(messageBefore = "Received request to delete Customer...", messageAfter = "Customer account deleted.", startFromNewLine = true)
    public void deleteCustomer(@PathVariable("id") Long id) {
        customerService.delete(id);
    }
}