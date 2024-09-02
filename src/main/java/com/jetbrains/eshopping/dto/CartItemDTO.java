package com.jetbrains.eshopping.dto;



import com.jetbrains.eshopping.entity.Product;

    public class CartItemDTO {

        private Product product;
        private int quantity;
        private Double price;
        private String name;
        private String formattedPrice;
        private String formattedTotalPrice;

        public Product getProduct() {
            return product;
        }

        public void setProduct(Product product) {
            this.product = product;
        }

        public int getQuantity() {
            return quantity;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }

        public void setId(Long id) {

        }

        public Double getPrice() {
            return price;
        }

        public void setPrice(Double price) {
            this.price = price;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getFormattedPrice() {
            return formattedPrice;
        }

        public void setFormattedPrice(String formattedPrice) {
            this.formattedPrice = formattedPrice;
        }

        public String getFormattedTotalPrice() {
            return formattedTotalPrice;
        }

        public void setFormattedTotalPrice(String formattedTotalPrice) {
            this.formattedTotalPrice = formattedTotalPrice;
        }
    }

