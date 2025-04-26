package com.duyhai.bookweb_backend.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookDTO {
    private int bookId;
    private String name;
    private String author;
    private String ISBN;
    private String description;
    private double priceOrigin;
    private double priceSell;
    private int quantity;
    private double rating;
    private String imageURL;
}
