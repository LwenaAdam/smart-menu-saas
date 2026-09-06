package com.smartmenu.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@jakarta.persistence.Table(name = "users") 
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-incrementing Integer/Long
    private Long id;

    @Column(unique = true, nullable = false) // Create unique index on username
    private String username;
    
    @Column(unique = true)  // Create unique index on email
    private String email;
    
    private String password;  // Will be hashed with BCrypt
    
    private String firstName;
    
    private String lastName;
    
    private String restaurantName;
    
    private String phone;
    
    // User role (single role per user)
    private UserRole role = UserRole.RESTAURANT_OWNER;
    
    private boolean active = true;
    
    @CreatedDate
    private LocalDateTime createdAt;
    
    @LastModifiedDate
    private LocalDateTime updatedAt;
}

