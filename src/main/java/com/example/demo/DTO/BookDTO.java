package com.example.demo.DTO;

import lombok.Data;

@Data
public class BookDTO {
    private int id;
    private String title;
    private String description;
    private Double price;
}
