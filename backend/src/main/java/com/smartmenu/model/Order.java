package com.smartmenu.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Order Entity - Represents a customer order
 * Workflow: PENDING → CONFIRMED → PREPARING → READY → COMPLETED
 */
@Entity
@jakarta.persistence.Table(name = "orders")
@EntityListeners(AuditingEntityListener.class)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String userId;

    private Long tableId;
    private String tableNumber;

    private String deviceId;
    private String customerName;
    private String orderNumber;

    @ElementCollection
    @CollectionTable(name = "order_items", joinColumns = @JoinColumn(name = "order_id"))
    private List<OrderItem> items = new ArrayList<>();

    private BigDecimal subtotal;
    private BigDecimal tax;
    private BigDecimal total;

    @Enumerated(EnumType.STRING)
    private OrderStatus status = OrderStatus.PENDING;

    @Column(columnDefinition = "TEXT")
    private String customerNotes;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    private LocalDateTime completedAt;

    @Embeddable
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class OrderItem {
        private String menuItemId;
        private String menuItemName;
        private BigDecimal price;
        private Integer quantity;
        private String specialInstructions;
    }

    public enum OrderStatus {
        PENDING,
        CONFIRMED,
        PREPARING,
        READY,
        COMPLETED,
        CANCELLED
    }
}
