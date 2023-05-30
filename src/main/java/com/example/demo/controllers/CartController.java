package com.example.demo.controllers;

import com.example.demo.DTO.CartDTO;

import com.example.demo.services.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/carts/")
public class CartController {
    @Autowired
    private CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping("getAllCarts")
    public ResponseEntity<List<CartDTO>> getAllCarts(){
        return new ResponseEntity<>(cartService.getAllCarts(), HttpStatus.OK);
    }

    @GetMapping("cart/{id}")
    public ResponseEntity<CartDTO> getCartByID(@PathVariable int id){
        return ResponseEntity.ok(cartService.getCartByID(id));
    }

    @PostMapping("cart/create")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<CartDTO> createCart(@RequestBody CartDTO cartDTO){
        return new ResponseEntity<>(cartService.createCart(cartDTO), HttpStatus.CREATED);
    }

    @PutMapping("cart/addItem/{cart_id}/{book_id}")
    public ResponseEntity<CartDTO> addItem(@PathVariable("cart_id") int cart_id,
                                            @PathVariable("book_id") int book_id){
        return new ResponseEntity<>(cartService.addItem(cart_id, book_id), HttpStatus.OK);
    }

    @PutMapping("cart/removeItem/{cart_id}/{book_id}")
    public ResponseEntity<CartDTO> removeItem(@PathVariable("cart_id") int cart_id,
                                           @PathVariable("book_id") int book_id){
        return new ResponseEntity<>(cartService.removeItem(cart_id, book_id), HttpStatus.OK);
    }

    @PutMapping("cart/checkout/{id}")
    public ResponseEntity<CartDTO> checkout(@PathVariable("id") int id){
        return new ResponseEntity<>(cartService.checkout(id), HttpStatus.OK);
    }

    @DeleteMapping("cart/delete/{id}")
    public ResponseEntity<String> deleteCart(@PathVariable("id") int id){
        cartService.deleteCart(id);
        return ResponseEntity.ok("Cart deleted");
    }
}
