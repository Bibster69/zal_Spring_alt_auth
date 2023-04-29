package com.example.demo.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "carts")
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private Double checkout_sum;

    private Boolean paidFor;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "cart_books",
            joinColumns = {@JoinColumn(name = "cart_id",
                    referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "book_id",
                    referencedColumnName = "id")}
    )
    private List<Book> books = new ArrayList<>();
}
