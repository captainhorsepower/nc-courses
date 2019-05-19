package com.netcracker.edu.varabey.controller.dto.transformer;

import com.netcracker.edu.varabey.controller.dto.domainspecific.InventoryOrderDTO;
import com.netcracker.edu.varabey.controller.dto.domainspecific.SimplifiedOrderDTO;
import org.springframework.stereotype.Component;

@Component
public class InventoryToSimplifiedOrderTransformer implements Transformer<InventoryOrderDTO, SimplifiedOrderDTO> {
    @Override
    public SimplifiedOrderDTO convert(InventoryOrderDTO input) {
        SimplifiedOrderDTO orderDTO = new SimplifiedOrderDTO();
        orderDTO.setId(input.getId());
        orderDTO.setEmail(input.getEmail());
        orderDTO.setPaid(input.isPaid());
        orderDTO.setOrderStatus(input.getOrderStatus());
        orderDTO.setCreatedOnDate(input.getCreatedOnDate());
        orderDTO.setItemCount(input.getItemCount());
        orderDTO.setTotalPrice(input.getTotalPrice());
        return orderDTO;
    }
}
