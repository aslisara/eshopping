package com.jetbrains.eshopping.controller;

import com.jetbrains.eshopping.dto.CartItemDTO;
import com.jetbrains.eshopping.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.text.DecimalFormat;
import java.util.List;

@Controller
public class CartController {

    @Autowired
    private CartService cartService;

    @GetMapping("/cart")
    public String viewCart(Model model) {
        List<CartItemDTO> cartItems = cartService.getCartItems();
        model.addAttribute("cartItems", cartItems);

        // Toplam fiyatı hesaplama ve formatlama
        double totalPrice = cartItems.stream()
                .mapToDouble(cartItem -> cartItem.getQuantity() * cartItem.getProduct().getPrice())
                .sum();
        DecimalFormat df = new DecimalFormat("#.00");
        model.addAttribute("totalPrice", df.format(totalPrice));

        // Fiyatları formatlamak için yeni bir liste oluşturun
        List<CartItemDTO> formattedCartItems = cartItems.stream()
                .map(cartItem -> {
                    double totalItemPrice = cartItem.getQuantity() * cartItem.getProduct().getPrice();
                    cartItem.setFormattedPrice(df.format(cartItem.getProduct().getPrice()));
                    cartItem.setFormattedTotalPrice(df.format(totalItemPrice));
                    return cartItem;
                })
                .toList();
        model.addAttribute("cartItems", formattedCartItems);

        return "cart"; // cart.html şablonunu döndürüyoruz
    }
}
