package com.kiev.msupport.controller.view.request;

import com.kiev.msupport.domain.CategoryEntity;
import com.kiev.msupport.domain.DepartmentEntity;
import com.kiev.msupport.domain.UnitEntity;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;

public class RequestTable {
    public SimpleLongProperty id = new SimpleLongProperty();
    public SimpleStringProperty category = new SimpleStringProperty();
    public SimpleStringProperty name = new SimpleStringProperty();
    public SimpleStringProperty units = new SimpleStringProperty();
    public SimpleStringProperty dep = new SimpleStringProperty();
    public SimpleStringProperty amount = new SimpleStringProperty();

    private Long categoryId;
    private Long unitsId;
    private Long depId;

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public Long getUnitsId() {
        return unitsId;
    }

    public void setUnitsId(Long unitsId) {
        this.unitsId = unitsId;
    }

    public Long getDepId() {
        return depId;
    }

    public void setDepId(Long depId) {
        this.depId = depId;
    }

    public RequestTable(String category, String name, String units, String dep, String amount) {
        this.category = new SimpleStringProperty(category);
        this.name = new SimpleStringProperty(name);
        this.units = new SimpleStringProperty(units);
        this.dep = new SimpleStringProperty(dep);
        this.amount = new SimpleStringProperty(amount);
    }

    public RequestTable(CategoryEntity category, String name, UnitEntity units, DepartmentEntity dep, String amount, String price) {
        this.categoryId = category.getId();
        this.category = new SimpleStringProperty(category.getName());
        this.name = new SimpleStringProperty(name);
        this.units = new SimpleStringProperty(units.getName());
        this.unitsId = units.getId();
        this.dep = new SimpleStringProperty(dep.getName());
        this.depId = dep.getId();
        this.amount = new SimpleStringProperty(amount);
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

    public String getDep() {
        return dep.get();
    }

    public SimpleStringProperty depProperty() {
        return dep;
    }

    public void setDep(String dep) {
        this.dep.set(dep);
    }

    public String getAmount() {
        return amount.get();
    }

    public SimpleStringProperty amountProperty() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount.set(amount);
    }
}
