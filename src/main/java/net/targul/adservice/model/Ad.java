package net.targul.adservice.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "ads")
public class Ad {
    @Id
    private String id;
    private String title;
    private String description;
    private double price;
    private String userId;

    public Ad(String id, String title, String description, double price, String userId) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.price = price;
        this.userId = userId;
    }

    public Ad() {}

    public Ad(String id) {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
