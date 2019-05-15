package com.netcracker.edu.varabey.controller.dto.transformer;

import com.netcracker.edu.varabey.controller.dto.CustomerDTO;
import com.netcracker.edu.varabey.entity.Customer;
import org.springframework.stereotype.Component;

@Component
public class CustomerTransformer implements Transformer<Customer, CustomerDTO> {
    @Override
    public Customer toEntity(CustomerDTO dto) {
        Customer customer = new Customer(dto.getFio(), dto.getAge(), dto.getEmail().trim().toLowerCase());
        customer.setId(dto.getId());
        return customer;
    }

    @Override
    public CustomerDTO toDto(Customer customer) {
        return new CustomerDTO(customer.getId(), customer.getFio(), customer.getAge(), customer.getEmail());
    }
}
