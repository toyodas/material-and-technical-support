package com.kiev.msupport.controller.view.mtr;

import com.kiev.msupport.domain.MTREntity;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;

public class MTRTable {
    private SimpleLongProperty id = new SimpleLongProperty();
    private SimpleStringProperty category = new SimpleStringProperty();
    private SimpleStringProperty name = new SimpleStringProperty();
    private SimpleStringProperty units = new SimpleStringProperty();

    public MTRTable(MTREntity e){
        this.id = new SimpleLongProperty(e.getId());
        this.name = new SimpleStringProperty(e.getName());
        this.category = new SimpleStringProperty(e.getCategory().getName());
        this.units = new SimpleStringProperty(e.getUnits().getName());
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

    public String getCategory() {
        return category.get();
    }

    public SimpleStringProperty categoryProperty() {
        return category;
    }

    public void setCategory(String category) {
        this.category.set(category);
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

    public String getUnits() {
        return units.get();
    }

    public SimpleStringProperty unitsProperty() {
        return units;
    }

    public void setUnits(String units) {
        this.units.set(units);
    }
}
