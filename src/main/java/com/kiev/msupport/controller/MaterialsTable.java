package com.kiev.msupport.controller;

import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;

public class MaterialsTable {
    public SimpleLongProperty id = new SimpleLongProperty();
    public SimpleStringProperty name = new SimpleStringProperty();
    public SimpleLongProperty availableAmount = new SimpleLongProperty();
    public SimpleLongProperty neededAmount = new SimpleLongProperty();
    public SimpleStringProperty lastUpdated = new SimpleStringProperty();


    public MaterialsTable(Long id, String name, Long availableAmount, Long neededAmount, String lastUpdated) {
        this.id = new SimpleLongProperty(id);
        this.name = new SimpleStringProperty(name);
        this.availableAmount = new SimpleLongProperty(availableAmount);
        this.neededAmount = new SimpleLongProperty(neededAmount);
        this.lastUpdated = new SimpleStringProperty(lastUpdated);
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

    public String getName() {
        return name.get();
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public long getAvailableAmount() {
        return availableAmount.get();
    }

    public SimpleLongProperty availableAmountProperty() {
        return availableAmount;
    }

    public void setAvailableAmount(long availableAmount) {
        this.availableAmount.set(availableAmount);
    }

    public long getNeededAmount() {
        return neededAmount.get();
    }

    public SimpleLongProperty neededAmountProperty() {
        return neededAmount;
    }

    public void setNeededAmount(long neededAmount) {
        this.neededAmount.set(neededAmount);
    }

    public String getLastUpdated() {
        return lastUpdated.get();
    }

    public SimpleStringProperty lastUpdatedProperty() {
        return lastUpdated;
    }

    public void setLastUpdated(String lastUpdated) {
        this.lastUpdated.set(lastUpdated);
    }
}
