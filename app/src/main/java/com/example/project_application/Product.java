package com.example.project_application;

import org.parceler.Parcel;

@Parcel

public class Product {
    private int id;
    private String code;
    private String name;
    private float price;
    private int qte_produit_client;
    private byte[] image;

    public Product(int id, String code, String name, float price, byte[] image) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.price = price;
        this.image = image;
    }
    public Product(String code, String name, float price,int qte, byte[] image) {
        this.code = code;
        this.name = name;
        this.price = price;
        this.image = image;
        this.qte_produit_client = qte;
    }

    public Product(String code, String name, float price, byte[] image) {
        this.code = code;
        this.name = name;
        this.price = price;
        this.image = image;

    }
    public Product() {

    }

    public Product(int id, String name, float price, int qte_produit_client, byte[] image) {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public int getQte_produit_client() {
        return qte_produit_client;
    }

    public void setQte_produit_client(int qte_produit_client) {
        this.qte_produit_client = qte_produit_client;
    }
}
