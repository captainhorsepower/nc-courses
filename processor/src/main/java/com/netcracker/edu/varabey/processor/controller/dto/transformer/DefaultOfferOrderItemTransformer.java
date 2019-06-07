package com.netcracker.edu.varabey.processor.controller.dto.transformer;

import com.netcracker.edu.varabey.processor.controller.dto.OfferDTO;
import com.netcracker.edu.varabey.processor.controller.dto.OrderItemDTO;
import org.springframework.stereotype.Component;

@Component
public class DefaultOfferOrderItemTransformer implements Transformer<OfferDTO, OrderItemDTO> {
    public OrderItemDTO convert(OfferDTO offerDTO) {
        OrderItemDTO orderItemDTO = new OrderItemDTO();

        orderItemDTO.setPrice(offerDTO.getPrice());
        orderItemDTO.setName(offerDTO.getName());
        orderItemDTO.setCategory(offerDTO.getCategory());
        orderItemDTO.setTags(offerDTO.getTags());

        return orderItemDTO;
    }


}
