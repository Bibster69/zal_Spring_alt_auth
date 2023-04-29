package com.example.demo.DTO;

import lombok.Data;

@Data
public class CartDTO {
    private int id;
    private Double checkout_sum;
    private Boolean paidFor;
}
