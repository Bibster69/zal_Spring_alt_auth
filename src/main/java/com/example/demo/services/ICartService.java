package com.example.demo.services;

import com.example.demo.DTO.CartDTO;

import java.util.List;

public interface ICartService {
    CartDTO createCart(CartDTO cartDTO);
    List<CartDTO> getAllCarts();

    CartDTO getCartByID(int id);

    CartDTO addItem(int id, int book_id);

    CartDTO removeItem(int id, int book_id);

    CartDTO checkout(int id);

    void deleteCart(int id);
}
