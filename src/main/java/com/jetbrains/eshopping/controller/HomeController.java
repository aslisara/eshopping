package com.jetbrains.eshopping.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/homepage")
    public String home(Model model) {

        model.addAttribute("message", "eShopping Platform'a Hoş Geldiniz!");
        return "index";
    }

//    @GetMapping("/cart")
//    public String viewCart(Model model) {
//        model.addAttribute("message", "Sepetinizdeki Ürünler");
//        return "cart";
//    }

    @GetMapping("/order")
    public String viewOrder(Model model) {
        model.addAttribute("message", "Sipariş Bilgisi");
        return "order";
    }

    @GetMapping("/products")
    public String viewProducts(Model model) {
        model.addAttribute("message", "Ürün Bilgileri");
        return "products"; // products.html dosyasını döndürür
    }
}
