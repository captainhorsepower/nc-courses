package com.netcracker.edu.varabey.processor.controller.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;
import java.util.Set;

@Data
@NoArgsConstructor
public class TagDTO {
    private Long id;
    private String name;
    private Set<Long> offers;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TagDTO tagDTO = (TagDTO) o;
        return name.equals(tagDTO.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
