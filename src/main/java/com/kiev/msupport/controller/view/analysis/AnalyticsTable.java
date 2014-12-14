package com.kiev.msupport.controller.view.analysis;

import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.image.ImageView;

public class AnalyticsTable {

    private SimpleLongProperty id;
    private SimpleStringProperty date;
    private SimpleStringProperty category;
    private SimpleStringProperty price;
    private SimpleStringProperty manager;
    private ImageView imageView;

    public AnalyticsTable(Long id, String date, String category, String price, String manager, ImageView imageView) {
        this.id = new SimpleLongProperty(id);
        this.date = new SimpleStringProperty(date);
        this.category = new SimpleStringProperty(category);
        this.price = new SimpleStringProperty(price);
        this.manager = new SimpleStringProperty(manager);
        this.imageView = imageView;
    }

    public long getId() {
        return id.get();
    }

    public SimpleLongProperty idProperty() {
        return id;
    }

    public void setId(long id) {
        this.id.set(id);
    }

    public String getDate() {
        return date.get();
    }

    public SimpleStringProperty dateProperty() {
        return date;
    }

    public void setDate(String date) {
        this.date.set(date);
    }

    public String getCategory() {
        return category.get();
    }

    public SimpleStringProperty categoryProperty() {
        return category;
    }

    public void setCategory(String category) {
        this.category.set(category);
    }

    public String getPrice() {
        return price.get();
    }

    public SimpleStringProperty priceProperty() {
        return price;
    }

    public void setPrice(String price) {
        this.price.set(price);
    }

    public String getManager() {
        return manager.get();
    }

    public SimpleStringProperty managerProperty() {
        return manager;
    }

    public void setManager(String manager) {
        this.manager.set(manager);
    }

    public ImageView getImageView() {
        return imageView;
    }

    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }
}
