package com.example.jaja.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Product implements Parcelable {
    private String name;
    private float quantity;
    private float price;
    private String imagePath;
    private String description;
    private String createdAt;
    private String productID;

    public Product(String name, float quantity, float price, String imagePath, String description, String createdAt, String productID) {
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        this.imagePath = imagePath;
        this.description = description;
        this.createdAt = createdAt;
        this.productID = productID;
    }

// Getter Methods

    public String getName() {
        return name;
    }

    public float getQuantity() {
        return quantity;
    }

    public float getPrice() {
        return price;
    }

    public String getImagePath() {
        return imagePath;
    }

    public String getDescription() {
        return description;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getProductID() {
        return productID;
    }

    // Setter Methods

    public void setName(String name) {
        this.name = name;
    }

    public void setQuantity(float quantity) {
        this.quantity = quantity;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeFloat(quantity);
        dest.writeFloat(price);
        dest.writeString(imagePath);
        dest.writeString(description);
        dest.writeString(createdAt);
        dest.writeString(productID);
    }

    protected Product(Parcel in){
        name = in.readString();
        quantity = in.readFloat();
        price = in.readFloat();
        imagePath = in.readString();
        description = in.readString();
        createdAt = in.readString();
        productID = in.readString();
    }

    public static final Creator<Product> CREATOR = new Creator<Product>() {
        @Override
        public Product createFromParcel(Parcel in) {
            return new Product(in);
        }

        @Override
        public Product[] newArray(int size) {
            return new Product[size];
        }
    };

}
