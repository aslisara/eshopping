package com.jetbrains.eshopping.repository;

import com.jetbrains.eshopping.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
    // Özel sorgular için gerekirse metotlar eklenebilir

    // Örnek: Ürün ismine göre bir ürün bul
    Optional<Product> findByName(String name);
}
