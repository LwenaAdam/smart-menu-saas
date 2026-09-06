package com.smartmenu.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

/**
 * Table Entity - Represents physical tables in the restaurant
 * Each table has a unique QR code that customers scan
 */
@Entity
@jakarta.persistence.Table(name = "restaurant_tables")
@EntityListeners(AuditingEntityListener.class)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Table {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String userId;

    private String tableNumber;

    private String qrCodeId;

    private String qrCodeUrl;

    @Column(columnDefinition = "TEXT")
    private String qrCodeImage;

    private boolean room = false;

    private String location;

    private boolean active = true;

    @CreatedDate
    private LocalDateTime createdAt;
}
