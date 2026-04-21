package com.example.demo.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import jakarta.persistence.Lob;

import jakarta.persistence.GeneratedValue;

@Entity
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    private byte[] data;

    public Image() {}

    public Image(byte[] data) {
        this.data = data;
    }

    public Long getId() { return id; }
    public byte[] getData() { return data; }
    public void setData(byte[] data) { this.data = data; }
    
}
