package com.jetbrains.eshopping.service;

import com.jetbrains.eshopping.dto.CartItemDTO;
import com.jetbrains.eshopping.entity.CartItem;
import com.jetbrains.eshopping.entity.Product;
import com.jetbrains.eshopping.repository.CartRepository;
import com.jetbrains.eshopping.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ProductRepository productRepository;

    // Ürünü sepete ekleme işlemi
    public void addToCart(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Ürün bulunamadı: " + productId));

        // Sepette bu ürün varsa, mevcut miktarı artır
        CartItem existingCartItem = cartRepository.findByProduct(product);
        if (existingCartItem != null) {
            existingCartItem.setQuantity(existingCartItem.getQuantity() + 1);
            cartRepository.save(existingCartItem);
        } else {
            // Ürün sepette yoksa, yeni bir sepet ürünü oluştur
            CartItem newCartItem = new CartItem();
            newCartItem.setProduct(product);
            newCartItem.setQuantity(1);
            cartRepository.save(newCartItem);
        }
    }

    // Ürüne göre sepet elemanını getir
    public CartItem getCartItemByProduct(Product product) {
        return cartRepository.findByProduct(product);
    }

    // Ürünü sepetten çıkarma işlemi
    public void removeFromCart(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Ürün bulunamadı: " + productId));

        CartItem cartItem = cartRepository.findByProduct(product);
        if (cartItem != null) {
            if (cartItem.getQuantity() > 1) {
                cartItem.setQuantity(cartItem.getQuantity() - 1);
                cartRepository.save(cartItem);
            } else {
                cartRepository.delete(cartItem);
            }
        }
    }

    // Sepetteki tüm elemanları getir
    public List<CartItemDTO> getCartItems() {
        List<CartItem> cartItems = cartRepository.findAll();
        return cartItems.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    // CartItem entity'sini DTO'ya dönüştürme
    private CartItemDTO convertToDTO(CartItem cartItem) {
        CartItemDTO cartItemDTO = new CartItemDTO();
        cartItemDTO.setId(cartItem.getId());
        cartItemDTO.setProduct(cartItem.getProduct());
        cartItemDTO.setQuantity(cartItem.getQuantity());
        return cartItemDTO;
    }
}
