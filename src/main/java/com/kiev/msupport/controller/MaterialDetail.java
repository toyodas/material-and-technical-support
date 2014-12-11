package com.kiev.msupport.controller;

import javafx.beans.property.SimpleStringProperty;

public class MaterialDetail {
    private SimpleStringProperty comment = new SimpleStringProperty();
    private SimpleStringProperty description = new SimpleStringProperty();

    public MaterialDetail(String comment, String description) {
        this.comment = new SimpleStringProperty(comment);
        this.description = new SimpleStringProperty(description);
    }

    public String getComment() {
        return comment.get();
    }

    public SimpleStringProperty commentProperty() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment.set(comment);
    }

    public String getDescription() {
        return description.get();
    }

    public SimpleStringProperty descriptionProperty() {
        return description;
    }

    public void setDescription(String description) {
        this.description.set(description);
    }
}
