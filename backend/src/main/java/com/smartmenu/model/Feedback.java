package com.smartmenu.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * Feedback Model - Stores customer feedback for orders
 */
@Entity
@jakarta.persistence.Table(name = "feedback")
public class Feedback {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userId;
    private Long orderId;
    private String orderNumber;
    private String tableNumber;
    private Double totalAmount;
    private Integer rating;

    @Column(columnDefinition = "TEXT")
    private String comments;

    private LocalDateTime createdAt;

    public Feedback() {
        this.createdAt = LocalDateTime.now();
    }

    public Feedback(String userId, Long orderId, String orderNumber, String tableNumber,
                   Double totalAmount, Integer rating, String comments) {
        this.userId = userId;
        this.orderId = orderId;
        this.orderNumber = orderNumber;
        this.tableNumber = tableNumber;
        this.totalAmount = totalAmount;
        this.rating = rating;
        this.comments = comments;
        this.createdAt = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getTableNumber() {
        return tableNumber;
    }

    public void setTableNumber(String tableNumber) {
        this.tableNumber = tableNumber;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
