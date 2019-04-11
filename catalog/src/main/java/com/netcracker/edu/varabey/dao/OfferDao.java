package com.netcracker.edu.varabey.dao;

import com.netcracker.edu.varabey.entity.Offer;

import java.util.List;

public interface OfferDao {
    Offer create(Offer offer);
    Offer read(Long id);
    Offer update(Offer offer);
    void delete(Long id);

    List<Offer> readAll();
}
