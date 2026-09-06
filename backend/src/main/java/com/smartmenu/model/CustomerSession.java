package com.smartmenu.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Customer Session Model - Tracks anonymous customers by device ID
 */
@Entity
@jakarta.persistence.Table(name = "customer_sessions")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerSession {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String deviceId;
    private Long tableId;
    private String userId;
    private String customerName;
    private String customerPhone;
    private Integer visitCount;
    private LocalDateTime firstVisit;
    private LocalDateTime lastVisit;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public CustomerSession(String deviceId, Long tableId, String userId) {
        this.deviceId = deviceId;
        this.tableId = tableId;
        this.userId = userId;
        this.visitCount = 1;
        this.firstVisit = LocalDateTime.now();
        this.lastVisit = LocalDateTime.now();
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }
}
