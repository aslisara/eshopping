package com.jetbrains.eshopping.repository;


import com.jetbrains.eshopping.entity.Order;
import com.jetbrains.eshopping.entity.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {


}