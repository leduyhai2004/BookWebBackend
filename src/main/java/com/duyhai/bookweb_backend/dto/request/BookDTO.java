package com.duyhai.bookweb_backend.dto.request;

import lombok.Data;

import java.util.List;
@Data
public class BookDTO {
    private String name;
    private String author;
    private String ISBN;
    private String description;
    private double priceOrigin;
    private double priceSell;
    private int quantity;
    private double rating;
    private List<ImageDTO> images;
}
