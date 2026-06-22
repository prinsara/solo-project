package com.example.soloproject.product.entity;

import com.example.soloproject.admin.entity.Admin;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private int price;

    @ManyToOne
    @JoinColumn(name = "admin_id")
    private Admin admin;

    protected Product() {
    }

    public Product(String name, int price) {
        this.name = name;
        this.price = price;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public Admin getAdmin() {
        return admin;
    }

    public void update(String name, int price) {
        this.name = name;
        this.price = price;
    }

    public void assignAdmin(Admin admin) {
        this.admin = admin;
    }
}
