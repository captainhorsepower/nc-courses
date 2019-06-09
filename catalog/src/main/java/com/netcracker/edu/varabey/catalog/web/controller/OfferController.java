package com.netcracker.edu.varabey.catalog.web.controller;

import com.netcracker.edu.varabey.catalog.data.entity.Category;
import com.netcracker.edu.varabey.catalog.data.entity.Offer;
import com.netcracker.edu.varabey.catalog.data.service.CategoryService;
import com.netcracker.edu.varabey.catalog.data.service.OfferService;
import com.netcracker.edu.varabey.catalog.data.service.TagService;
import com.netcracker.edu.varabey.catalog.data.validation.OfferValidator;
import com.netcracker.edu.varabey.catalog.data.validation.exceptions.OfferException;
import com.netcracker.edu.varabey.catalog.springutils.beanannotation.Logged;
import com.netcracker.edu.varabey.catalog.web.controller.dto.CategoryDTO;
import com.netcracker.edu.varabey.catalog.web.controller.dto.OfferDTO;
import com.netcracker.edu.varabey.catalog.web.controller.dto.transformer.Transformer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/offers")
public class OfferController {
    private final Transformer<Offer, OfferDTO> offerTransformer;
    private final Transformer<Category, CategoryDTO> categoryTransformer;
    private final OfferService offerService;
    private final CategoryService categoryService;
    private final TagService tagService;
    private final OfferValidator offerValidator;

    protected Logger logger = LoggerFactory.getLogger(OfferController.class);

    public OfferController(Transformer<Offer, OfferDTO> offerTransformer, Transformer<Category, CategoryDTO> categoryTransformer, OfferService offerService, CategoryService categoryService, TagService tagService, OfferValidator offerValidator) {
        this.offerTransformer = offerTransformer;
        this.categoryTransformer = categoryTransformer;
        this.offerService = offerService;
        this.categoryService = categoryService;
        this.tagService = tagService;
        this.offerValidator = offerValidator;
    }

    protected String decode(String value) {
        try {
            return URLDecoder.decode(value, StandardCharsets.UTF_8.name());
        } catch (UnsupportedEncodingException e) {
//            logger.error("decoding problem: ", e);
            throw new OfferException(e);
        }
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Logged(messageBefore = "Received request to find Offer by id...", messageAfter = "Offer retrieved.", startFromNewLine = true)
    public OfferDTO findOffer(@PathVariable("id") Long id) {
        Offer offer = offerValidator.checkFoundById(offerService.findById(id), id);
        return offerTransformer.toDto(offer);
    }

    protected List<OfferDTO> findAllOffers() {
        return offerService.findAll().stream()
                .map(offerTransformer::toDto)
                .collect(Collectors.toList());
    }

    protected List<OfferDTO> findAllOffersByCategory(String category) {
        category = decode(category);
        return offerService.findAllOffersByCategory(category).stream()
                .map(offerTransformer::toDto)
                .collect(Collectors.toList());
    }

    protected List<OfferDTO> findAllOffersByTags(List<String> tagNames) {
        tagNames = tagNames.stream()
                .map(this::decode)
                .collect(Collectors.toList());
        return offerService.findAllOffersWithTags(tagNames).stream()
                .map(offerTransformer::toDto)
                .collect(Collectors.toList());
    }

    protected List<OfferDTO> findAllByPriceRange(Double minPrice, Double maxPrice) {
        return offerService.findAllWithPriceInRange(minPrice, maxPrice).stream()
                .map(offerTransformer::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Logged(messageBefore = "Received request to retrieve multiple offers...", messageAfter = "Offers retrieved.", startFromNewLine = true)
    public List<OfferDTO> findOffers(
            @RequestParam(name = "category", required = false) String category,
            @RequestParam(name = "tags", required = false) List<String> tags,
            @RequestParam(name = "minPrice", required = false) Double minPrice,
            @RequestParam(name = "maxPrice", required = false) Double maxPrice
            ) {
        logger.info("Filter: category={}, tags={}, priceRange=[{}, {}]", category, tags, minPrice, maxPrice);

        if (category != null && !category.isEmpty()) {
            return findAllOffersByCategory(category);
        } else if (tags != null && !tags.isEmpty()) {
            return findAllOffersByTags(tags);
        } else if (minPrice != null || maxPrice != null) {
                return findAllByPriceRange(minPrice, maxPrice);
        } else {
            return findAllOffers();
        }
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Logged(messageBefore = "Received request to create a new Offer...", messageAfter = "Offer created.", startFromNewLine = true)
    public OfferDTO saveOffer(@RequestBody OfferDTO dto) {
        Offer offer = offerTransformer.toEntity(dto);
        offer = offerService.create(offer);
        return offerTransformer.toDto(offer);
    }

    @PostMapping("/{id}/tags")
    @ResponseStatus(HttpStatus.OK)
    @Logged(messageBefore = "Received request to add tags to offer...", messageAfter = "Offer updated.", startFromNewLine = true)
    public OfferDTO addTags(@PathVariable("id") Long id, @RequestBody List<String> tagNames) {
        Offer offer = offerService.addTags(id, tagNames);
        return offerTransformer.toDto(offer);
    }

    @DeleteMapping("/{id}/tags")
    @ResponseStatus(HttpStatus.OK)
    @Logged(messageBefore = "Received request to remove tags from offer...", messageAfter = "Offer updated.", startFromNewLine = true)
    public OfferDTO removeTags(@PathVariable("id") Long id, @RequestBody List<String> tagNames) {
        Offer offer = offerService.removeTags(id, tagNames);
        return offerTransformer.toDto(offer);
    }

    @PutMapping("/{id}/category")
    @ResponseStatus(HttpStatus.OK)
    @Logged(messageBefore = "Received request to change offer's category...", messageAfter = "Offer was updated.", startFromNewLine = true)
    public OfferDTO changeCategory(@PathVariable("id") Long id, @RequestBody CategoryDTO categoryDTO) {
        Category category = categoryTransformer.toEntity(categoryDTO);
        Offer offer = offerService.changeCategory(id, category);
        return offerTransformer.toDto(offer);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Logged(messageBefore = "Received request to update offer's name and price...", messageAfter = "Offer was updated.", startFromNewLine = true)
    public OfferDTO updateOfferNameAndPrice(@PathVariable("id") Long id, @RequestBody OfferDTO dto) {
        Offer offer = offerTransformer.toEntity(dto);
        offer.setId(id);
        offer = offerService.updateNameAndPrice(offer);
        return offerTransformer.toDto(offer);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Logged(messageBefore = "Received request to delete offer...", messageAfter = "Offer was deleted.", startFromNewLine = true)
    public void deleteOffer(@PathVariable("id") Long id) {
        offerService.delete(id);
    }
}
