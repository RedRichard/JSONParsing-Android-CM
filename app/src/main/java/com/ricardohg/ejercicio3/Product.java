package com.ricardohg.ejercicio3;

import java.io.Serializable;

public class Product implements Serializable {
    String id;
    String name;
    String price;
    String provider;
    String delivery;
    String imageURL;

    public Product(String id, String name, String price, String provider, String delivery, String imageURL) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.provider = provider;
        this.delivery = delivery;
        this.imageURL = imageURL;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public String getDelivery() {
        return delivery;
    }

    public void setDelivery(String delivery) {
        this.delivery = delivery;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }
}
