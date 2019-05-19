package com.netcracker.edu.varabey.controller.dto.transformer;

import com.netcracker.edu.varabey.controller.dto.CustomerDTO;
import com.netcracker.edu.varabey.controller.dto.domainspecific.InventoryOrderDTO;
import com.netcracker.edu.varabey.controller.dto.domainspecific.VerboseOrderDTO;
import org.springframework.stereotype.Component;

@Component
public class InventoryToProcessorOrderTransformer implements Transformer<InventoryOrderDTO, VerboseOrderDTO> {
    @Override
    public VerboseOrderDTO convert(InventoryOrderDTO input) {
        VerboseOrderDTO orderDTO = new VerboseOrderDTO();

        CustomerDTO customer = new CustomerDTO();
        customer.setEmail(input.getEmail());

        orderDTO.setCustomer(customer);
        orderDTO.setId(input.getId());
        orderDTO.setIsPaid(input.isPaid());
        orderDTO.setOrderStatus(input.getOrderStatus());
        orderDTO.setCreatedOnDate(input.getCreatedOnDate());
        orderDTO.setItemCount(input.getItemCount());
        orderDTO.setTotalPrice(input.getTotalPrice());
        orderDTO.setItems(input.getItems());
        return orderDTO;
    }
}