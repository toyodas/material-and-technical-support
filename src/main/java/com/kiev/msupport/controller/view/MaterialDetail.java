package com.kiev.msupport.controller.view;

import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;

public class MaterialDetail {
    private SimpleStringProperty comment = new SimpleStringProperty();
    private SimpleStringProperty description = new SimpleStringProperty();
    private SimpleStringProperty lastUpdated = new SimpleStringProperty();

    public MaterialDetail(String comment, String description, String lastUpdated) {
        this.comment = new SimpleStringProperty(comment);
        this.description = new SimpleStringProperty(description);
        this.lastUpdated = new SimpleStringProperty();
    }



}
