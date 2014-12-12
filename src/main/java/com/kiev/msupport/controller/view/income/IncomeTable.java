package com.kiev.msupport.controller.view.income;

import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;

public class IncomeTable {
    public SimpleLongProperty id = new SimpleLongProperty();
    public SimpleStringProperty category = new SimpleStringProperty();
    public SimpleStringProperty name = new SimpleStringProperty();
    public SimpleStringProperty units = new SimpleStringProperty();
    public SimpleStringProperty dep = new SimpleStringProperty();
    public SimpleStringProperty amount = new SimpleStringProperty();
    public SimpleStringProperty price = new SimpleStringProperty();
    public SimpleStringProperty noTax = new SimpleStringProperty();
    public SimpleStringProperty tax = new SimpleStringProperty();

    public IncomeTable( String category, String name, String units, String dep, String amount, String price) {
        this.category = new SimpleStringProperty(category);
        this.name = new SimpleStringProperty(name);
        this.units = new SimpleStringProperty(units);
        this.dep = new SimpleStringProperty(dep);
        this.amount = new SimpleStringProperty(amount);
        this.price = new SimpleStringProperty(price);
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

    public String getPrice() {
        return price.get();
    }

    public SimpleStringProperty priceProperty() {
        return price;
    }

    public void setPrice(String price) {
        this.price.set(price);
    }

    public String getNoTax() {
        return noTax.get();
    }

    public SimpleStringProperty noTaxProperty() {
        return noTax;
    }

    public void setNoTax(String noTax) {
        this.noTax.set(noTax);
    }

    public String getTax() {
        return tax.get();
    }

    public SimpleStringProperty taxProperty() {
        return tax;
    }

    public void setTax(String tax) {
        this.tax.set(tax);
    }
}
