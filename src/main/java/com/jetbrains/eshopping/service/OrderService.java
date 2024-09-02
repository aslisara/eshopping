package com.jetbrains.eshopping.service;

import com.jetbrains.eshopping.dto.OrderDTO;
import com.jetbrains.eshopping.dto.OrderDetailDTO;
import com.jetbrains.eshopping.dto.ProductDTO;
import com.jetbrains.eshopping.entity.Order;
import com.jetbrains.eshopping.entity.OrderDetail;
import com.jetbrains.eshopping.entity.Product;
import com.jetbrains.eshopping.entity.User;
import com.jetbrains.eshopping.exception.ResourceNotFoundException;
import com.jetbrains.eshopping.repository.OrderDetailRepository;
import com.jetbrains.eshopping.repository.OrderRepository;
import com.jetbrains.eshopping.repository.ProductRepository;
import com.jetbrains.eshopping.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    // Sipariş oluşturma
    public OrderDTO createOrder(Long userId, List<OrderDetailDTO> orderDetails) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Order order = new Order();
        order.setUser(user);
        order.setOrderDate(new Date());

        List<OrderDetail> details = orderDetails.stream().map(this::mapToEntity).collect(Collectors.toList());
        order.setOrderDetails(details);

        Order savedOrder = orderRepository.save(order);

        // OrderDetail'ları kaydet ve order ile ilişkilendir
        for (OrderDetail detail : details) {
            detail.setOrder(savedOrder);
            orderDetailRepository.save(detail);
        }

        return mapToDTO(savedOrder);
    }

    // Belirli bir kullanıcının tüm siparişlerini getir
    public List<OrderDTO> getOrdersByUserId(Long userId) {
        List<Order> orders = orderRepository.findByUserId(userId);
        return orders.stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    // Entity'den DTO'ya dönüştürme
    private OrderDTO mapToDTO(Order order) {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setId(order.getId());
        orderDTO.setOrderDate(order.getOrderDate());
        List<OrderDetailDTO> detailDTOs = order.getOrderDetails().stream().map(this::mapToDTO).collect(Collectors.toList());
        orderDTO.setOrderDetails(detailDTOs);
        return orderDTO;
    }

    // DTO'dan Entity'ye dönüştürme
    private OrderDetail mapToEntity(OrderDetailDTO orderDetailDTO) {
        ProductDTO productDTO = orderDetailDTO.getProduct();
        Product product = productRepository.findById(productDTO.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setProduct(product);
        orderDetail.setQuantity(orderDetailDTO.getQuantity());
        orderDetail.setPrice(orderDetailDTO.getPrice());
        return orderDetail;
    }

    private OrderDetailDTO mapToDTO(OrderDetail orderDetail) {
        OrderDetailDTO detailDTO = new OrderDetailDTO();
        detailDTO.setId(orderDetail.getId());
        detailDTO.setProduct(productRepository.findById(orderDetail.getProduct().getId())
                .map(this::mapToDTO).orElse(null));
        detailDTO.setQuantity(orderDetail.getQuantity());
        detailDTO.setPrice(orderDetail.getPrice());
        return detailDTO;
    }

    private ProductDTO mapToDTO(Product product) {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(product.getId());
        productDTO.setName(product.getName());
        productDTO.setDescription(product.getDescription());
        productDTO.setPrice(product.getPrice());
        productDTO.setStock_quantity(product.getStock_quantity());
        return productDTO;
    }
}
