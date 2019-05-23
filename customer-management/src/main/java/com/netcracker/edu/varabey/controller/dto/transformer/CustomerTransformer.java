package com.netcracker.edu.varabey.controller.dto.transformer;

import com.netcracker.edu.varabey.controller.dto.CustomerDTO;
import com.netcracker.edu.varabey.entity.Customer;
import org.springframework.stereotype.Component;

@Component
public class CustomerTransformer implements Transformer<Customer, CustomerDTO> {
    @Override
    public Customer toEntity(CustomerDTO dto) {
        Customer customer = new Customer();
        customer.setFio( (dto.getFio() == null) ? dto.getFio() : dto.getFio().trim());
        customer.setAge(dto.getAge());
        customer.setEmail( (dto.getEmail() == null) ? dto.getEmail() : dto.getEmail().trim().toLowerCase());
        customer.setId(dto.getId());
        return customer;
    }

    @Override
    public CustomerDTO toDto(Customer customer) {
        return new CustomerDTO(customer.getId(), customer.getFio(), customer.getAge(), customer.getEmail());
    }
}
