package com.kiev.msupport.controller.view.analysis;

import com.kiev.msupport.controller.view.analysis.contragent.ContragentController;
import javafx.beans.property.SimpleStringProperty;

import java.util.List;

public class ProductTable {
    private SimpleStringProperty name;
    private SimpleStringProperty units;
    private SimpleStringProperty amount;
    private SimpleStringProperty minPrice;

    private List<ContragentController> clist;
    private int row;

    public ProductTable(String name, String units, String amount) {
        this.name = new SimpleStringProperty(name);
        this.units = new SimpleStringProperty(units);
        this.amount = new SimpleStringProperty(amount);
        this.minPrice= new SimpleStringProperty("0");
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

    public String getAmount() {
        return amount.get();
    }

    public SimpleStringProperty amountProperty() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount.set(amount);
    }

    public String getMinPrice() {
        return minPrice.get();
    }

    public SimpleStringProperty minPriceProperty() {
        return minPrice;
    }

    public void setMinPrice(String minPrice) {
        this.minPrice.set(minPrice);
    }

    public List<ContragentController> getClist() {
        return clist;
    }

    public void setClist(List<ContragentController> clist) {
        this.clist = clist;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }
}
