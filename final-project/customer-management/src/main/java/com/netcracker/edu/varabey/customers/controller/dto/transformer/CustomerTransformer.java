package com.netcracker.edu.varabey.customers.controller.dto.transformer;

import com.netcracker.edu.varabey.customers.controller.dto.CustomerDTO;
import com.netcracker.edu.varabey.customers.entity.Customer;
import com.netcracker.edu.varabey.customers.validation.exceptions.CustomerException;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

@Component
public class CustomerTransformer implements Transformer<Customer, CustomerDTO> {
    @Override
    public Customer toEntity(CustomerDTO dto) {
        Customer customer = new Customer();
        customer.setFio( (dto.getFio() == null) ? null : decode(dto.getFio().trim()));
        customer.setAge(dto.getAge());
        customer.setEmail( (dto.getEmail() == null) ? null : decode(dto.getEmail().trim().toLowerCase()));
        customer.setId(dto.getId());
        return customer;
    }

    @Override
    public CustomerDTO toDto(Customer customer) {
        return new CustomerDTO(customer.getId(), customer.getFio(), customer.getAge(), customer.getEmail());
    }

    protected String decode(String value) {
        try {
            return URLDecoder.decode(value, StandardCharsets.UTF_8.name());
        } catch (UnsupportedEncodingException e) {
            throw new CustomerException(e);
        }
    }
}
