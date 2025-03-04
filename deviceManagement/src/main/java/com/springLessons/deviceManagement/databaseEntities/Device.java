package com.springLessons.deviceManagement.databaseEntities;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Entity
@Table(name = "Devices")
public class Device {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-increment ID
    private Long id;

    private String name;
    private String brand;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private State state;

    @CreationTimestamp
    @Column(updatable = false, nullable = false)
    private LocalDateTime creationTime;

    public Device(String name, String brand, State state) {
        this.name = name;
        this.brand = brand;
        this.state = state;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public LocalDateTime getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(LocalDateTime creationTime) {
        this.creationTime = creationTime;
    }

    // Constructors
    public Device() {}

}
