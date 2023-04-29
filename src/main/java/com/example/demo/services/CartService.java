package com.example.demo.services;

import com.example.demo.DTO.CartDTO;
import com.example.demo.models.Book;
import com.example.demo.models.Cart;
import com.example.demo.repos.BookRepository;
import com.example.demo.repos.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CartService implements ICartService{

    private CartRepository cartRepository;
    private BookRepository bookRepository;

    @Autowired
    public CartService(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    @Override
    public CartDTO createCart(CartDTO cartDTO){
        Cart cart = new Cart();
        cart.setCheckout_sum(0.0);
        cart.setPaidFor(false);

        Cart newCart = cartRepository.save(cart);

        CartDTO cartResponse = new CartDTO();
        cartResponse.setCheckout_sum(newCart.getCheckout_sum());
        cartResponse.setPaidFor(false);

        return cartResponse;
    }

    @Override
    public List<CartDTO> getAllCarts() {
        List<Cart> carts = cartRepository.findAll();
        return carts.stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    @Override
    public CartDTO getCartByID(int id) {
        Cart cart = cartRepository.findById(id).orElseThrow(RuntimeException::new);
        return mapToDTO(cart);
    }

    @Override
    public CartDTO addItem(int id, int book_id) {
        Cart cart = cartRepository.findById(id).orElseThrow(RuntimeException::new);
        Book book = bookRepository.findById(book_id).orElseThrow(RuntimeException::new);
        cart.getBooks().add(book);
        Double sum = cart.getCheckout_sum();
        cart.setCheckout_sum(sum + book.getPrice());
        cartRepository.save(cart);

        return mapToDTO(cart);
    }

    @Override
    public CartDTO removeItem(int id, int book_id) {
        Cart cart = cartRepository.findById(id).orElseThrow(RuntimeException::new);
        Book book = bookRepository.findById(book_id).orElseThrow(RuntimeException::new);
        cart.getBooks().remove(book);
        Double sum = cart.getCheckout_sum();
        cart.setCheckout_sum(sum - book.getPrice());
        cartRepository.save(cart);

        return mapToDTO(cart);
    }

    @Override
    public CartDTO checkout(int id) {
        Cart cart = cartRepository.findById(id).orElseThrow(RuntimeException::new);
        cart.setPaidFor(true);
        return mapToDTO(cart);
    }

    @Override
    public void deleteCart(int id) {
        Cart cart = cartRepository.findById(id).orElseThrow(RuntimeException::new);
        cartRepository.delete(cart);
    }

    private CartDTO mapToDTO(Cart cart){
        CartDTO cartDTO = new CartDTO();
        cartDTO.setId(cart.getId());
        cartDTO.setCheckout_sum(cart.getCheckout_sum());
        cartDTO.setPaidFor(cart.getPaidFor());

        return cartDTO;
    }


}
