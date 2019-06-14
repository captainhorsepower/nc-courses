package com.netcracker.edu.varabey.catalog.web.controller;

import com.netcracker.edu.varabey.catalog.web.controller.dto.TagDTO;
import com.netcracker.edu.varabey.catalog.web.controller.dto.transformer.Transformer;
import com.netcracker.edu.varabey.catalog.data.entity.Tag;
import com.netcracker.edu.varabey.catalog.data.service.TagService;
import com.netcracker.edu.varabey.catalog.data.validation.TagValidator;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/tags")
@Api
public class TagController {
    @Qualifier("tagTransformer")
    private final Transformer<Tag, TagDTO> tagTransformer;
    private final TagService tagService;
    private final TagValidator tagValidator;

    public TagController(Transformer<Tag, TagDTO> tagTransformer, TagService tagService, TagValidator tagValidator) {
        this.tagTransformer = tagTransformer;
        this.tagService = tagService;
        this.tagValidator = tagValidator;
    }

    @GetMapping("/{input}")
    @ResponseStatus(HttpStatus.OK)
    public TagDTO findTag(@PathVariable("input") String input) {
        Tag tag = tagValidator.checkFoundByName(tagService.find(input), "Tag with identifier \'" + input + "\' was not found.");
        return tagTransformer.toDto(tag);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TagDTO saveTag(@RequestBody TagDTO dto) {
        Tag tag = tagTransformer.toEntity(dto);
        tag = tagService.create(tag);
        return tagTransformer.toDto(tag);
    }

    @PostMapping("saveAll")
    @ResponseStatus(HttpStatus.CREATED)
    public List<TagDTO> saveAllTags(@RequestBody List<TagDTO> dtoList) {
        return dtoList.stream()
                .map(this::saveTag)
                .collect(Collectors.toList());
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public TagDTO updateTagName(@PathVariable("id") Long id, @RequestBody TagDTO dto) {
        Tag tag = tagTransformer.toEntity(dto);
        tag.setId(id);
        tag = tagService.updateName(tag);
        return tagTransformer.toDto(tag);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTag(@PathVariable("id") Long id) {
        tagService.deleteTag(id);
    }
}
