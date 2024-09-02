package com.jetbrains.eshopping.repository;

import com.jetbrains.eshopping.entity.CartItem;
import com.jetbrains.eshopping.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;


    public interface CartRepository extends JpaRepository<CartItem, Long> {
        CartItem findByProduct(Product product);
    }


