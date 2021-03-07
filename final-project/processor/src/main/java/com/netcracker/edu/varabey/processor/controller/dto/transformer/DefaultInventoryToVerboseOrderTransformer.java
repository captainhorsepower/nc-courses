package com.netcracker.edu.varabey.processor.controller.dto.transformer;

import com.netcracker.edu.varabey.processor.controller.dto.CustomerDTO;
import com.netcracker.edu.varabey.processor.controller.dto.domainspecific.InventoryOrderDTO;
import com.netcracker.edu.varabey.processor.controller.dto.domainspecific.VerboseOrderDTO;
import org.springframework.stereotype.Component;

@Component
public class DefaultInventoryToVerboseOrderTransformer implements Transformer<InventoryOrderDTO, VerboseOrderDTO> {
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
        orderDTO.setTotalPrice(Double.valueOf(String.format("%.2f", input.getTotalPrice())));
        orderDTO.setItems(input.getItems());
        return orderDTO;
    }
}