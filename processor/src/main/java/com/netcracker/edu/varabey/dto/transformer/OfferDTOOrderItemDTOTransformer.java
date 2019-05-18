package com.netcracker.edu.varabey.dto.transformer;

import com.netcracker.edu.varabey.dto.OfferDTO;
import com.netcracker.edu.varabey.dto.OrderItemDTO;
import org.springframework.stereotype.Component;

@Component
public class OfferDTOOrderItemDTOTransformer implements Transformer<OfferDTO, OrderItemDTO> {
    public OrderItemDTO convert(OfferDTO offerDTO) {
        OrderItemDTO orderItemDTO = new OrderItemDTO();
        orderItemDTO.setPrice(offerDTO.getPrice());
        orderItemDTO.setName(offerDTO.getName());
        orderItemDTO.setCategory(offerDTO.getCategory());
        orderItemDTO.setTags(offerDTO.getTags());
        return orderItemDTO;
    }


}
