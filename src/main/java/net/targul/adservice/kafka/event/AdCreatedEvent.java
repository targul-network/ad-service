package net.targul.adservice.kafka.event;

import java.util.List;

public class AdCreatedEvent {
    private String id;
    private String title;
    private String description;
    private double price;
    private List<String> categories;

    public AdCreatedEvent() {};

    public AdCreatedEvent(String id, String title, String description, double price, List<String> categories) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.price = price;
        this.categories = categories;
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

    public List<String> getCategories() {
        return categories;
    }

    public void setCategories(List<String> categories) {
        this.categories = categories;
    }
}
