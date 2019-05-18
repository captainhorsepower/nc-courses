package com.netcracker.edu.varabey.controller;

import com.netcracker.edu.varabey.controller.dto.CategoryDTO;
import com.netcracker.edu.varabey.controller.dto.OfferDTO;
import com.netcracker.edu.varabey.controller.dto.transformer.Transformer;
import com.netcracker.edu.varabey.controller.dto.util.OfferFilterDTO;
import com.netcracker.edu.varabey.entity.Category;
import com.netcracker.edu.varabey.entity.Offer;
import com.netcracker.edu.varabey.entity.Tag;
import com.netcracker.edu.varabey.service.CategoryService;
import com.netcracker.edu.varabey.service.OfferService;
import com.netcracker.edu.varabey.service.TagService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

import static com.netcracker.edu.varabey.controller.util.RestPreconditions.checkFound;

@RestController
@RequestMapping("/offers")
public class OfferController {
    private final Transformer<Offer, OfferDTO> offerTransformer;
    private final Transformer<Category, CategoryDTO> categoryTransformer;
    private final OfferService offerService;
    private final CategoryService categoryService;
    private final TagService tagService;
    private final String notSetFlag = "-1.00";

    public OfferController(Transformer<Offer, OfferDTO> offerTransformer, Transformer<Category, CategoryDTO> categoryTransformer, OfferService offerService, CategoryService categoryService, TagService tagService) {
        this.offerTransformer = offerTransformer;
        this.categoryTransformer = categoryTransformer;
        this.offerService = offerService;
        this.categoryService = categoryService;
        this.tagService = tagService;
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public OfferDTO findOffer(@PathVariable("id") Long id) {
        Offer offer = checkFound(offerService.findById(id));
        return offerTransformer.toDto(offer);
    }

    private List<OfferDTO> findAllOffers() {
        return offerService.findAll().stream()
                .map(offerTransformer::toDto)
                .collect(Collectors.toList());
    }

    private List<OfferDTO> findAllOffersByCategory(String category) {
        category = category.replaceAll("%20", " ");
        return offerService.findAllOffersByCategory(
                categoryService.getByName(category)
        ).stream()
                .map(offerTransformer::toDto)
                .collect(Collectors.toList());
    }

    private List<OfferDTO> findAllOffersByTags(List<String> tagNames) {
        List<Tag> tags = tagNames.stream()
                .map(tagService::getByName)
                .collect(Collectors.toList());
        return offerService.findAllOffersWithTags(tags).stream()
                .map(offerTransformer::toDto)
                .collect(Collectors.toList());
    }

    private List<OfferDTO> findAllByPriceRange(Double minPrice, Double maxPrice) {
        return offerService.findAllWithPriceInRange(minPrice, maxPrice).stream()
                .map(offerTransformer::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<OfferDTO> findOffers(
            @RequestParam(name = "category", required = false) String category,
            @RequestParam(name = "tags", required = false) List<String> tags,
            @RequestParam(name = "minPrice", required = false) Double minPrice,
            @RequestParam(name = "maxPrice", required = false) Double maxPrice
            ) {
        if (category != null) {
            return findAllOffersByCategory(category);
        } else if (tags != null) {
            return findAllOffersByTags(tags);
        } else if (minPrice != null || maxPrice != null) {
                return findAllByPriceRange(minPrice, maxPrice);
        } else {
            return findAllOffers();
        }
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OfferDTO saveOffer(@RequestBody OfferDTO dto) {
        Offer offer = offerTransformer.toEntity(dto);
        offer = offerService.create(offer);
        return offerTransformer.toDto(offer);
    }

    @PutMapping("/{id}/addTags")
    @ResponseStatus(HttpStatus.OK)
    public OfferDTO addTags(@PathVariable("id") Long id, @RequestBody List<String> tagNames) {
        Offer offer = offerService.addTags(id, tagNames);
        return offerTransformer.toDto(offer);
    }

    @PutMapping("/{id}/removeTags")
    @ResponseStatus(HttpStatus.OK)
    public OfferDTO removeTags(@PathVariable("id") Long id, @RequestBody List<String> tagNames) {
        Offer offer = offerService.removeTags(id, tagNames);
        return offerTransformer.toDto(offer);
    }

    @PutMapping("/{id}/changeCategory")
    @ResponseStatus(HttpStatus.OK)
    public OfferDTO changeCategory(@PathVariable("id") Long id, @RequestBody CategoryDTO categoryDTO) {
        Category category = categoryTransformer.toEntity(categoryDTO);
        Offer offer = offerService.changeCategory(id, category);
        return offerTransformer.toDto(offer);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public OfferDTO updateOfferNameAndPrice(@PathVariable("id") Long id, @RequestBody OfferDTO dto) {
        Offer offer = offerTransformer.toEntity(dto);
        offer.setId(id);
        offer = offerService.updateNameAndPrice(offer);
        return offerTransformer.toDto(offer);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteOffer(@PathVariable("id") Long id) {
        offerService.delete(id);
    }
}
