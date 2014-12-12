package com.kiev.msupport.controller.view;

import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;

public class MaterialsTable {
    public SimpleLongProperty id = new SimpleLongProperty();
    public SimpleStringProperty category = new SimpleStringProperty();
    public SimpleStringProperty name = new SimpleStringProperty();
    public SimpleStringProperty units = new SimpleStringProperty();
    public SimpleStringProperty dep = new SimpleStringProperty();
    public SimpleStringProperty residueForToday = new SimpleStringProperty();
    public SimpleStringProperty residueForPeriod = new SimpleStringProperty();
    public SimpleStringProperty fullPrice = new SimpleStringProperty();
    public SimpleStringProperty demand = new SimpleStringProperty();
    public SimpleStringProperty monthIncome = new SimpleStringProperty();

    public MaterialsTable(Long id, String category, String name, String units, String dep, String residueForToday, String residueForPeriod, String fullPrice, String demand, String monthIncome) {
        this.id = new SimpleLongProperty(id);
        this.category = new SimpleStringProperty(category);
        this.name = new SimpleStringProperty(name);
        this.units = new SimpleStringProperty(units);
        this.dep = new SimpleStringProperty(dep);
        this.residueForToday = new SimpleStringProperty(residueForToday);
        this.residueForPeriod = new SimpleStringProperty(residueForPeriod);
        this.fullPrice = new SimpleStringProperty(fullPrice);
        this.demand = new SimpleStringProperty(demand);
        this.monthIncome = new SimpleStringProperty(monthIncome);
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

    public String getResidueForToday() {
        return residueForToday.get();
    }

    public SimpleStringProperty residueForTodayProperty() {
        return residueForToday;
    }

    public void setResidueForToday(String residueForToday) {
        this.residueForToday.set(residueForToday);
    }

    public String getResidueForPeriod() {
        return residueForPeriod.get();
    }

    public SimpleStringProperty residueForPeriodProperty() {
        return residueForPeriod;
    }

    public void setResidueForPeriod(String residueForPeriod) {
        this.residueForPeriod.set(residueForPeriod);
    }

    public String getFullPrice() {
        return fullPrice.get();
    }

    public SimpleStringProperty fullPriceProperty() {
        return fullPrice;
    }

    public void setFullPrice(String fullPrice) {
        this.fullPrice.set(fullPrice);
    }

    public String getDemand() {
        return demand.get();
    }

    public SimpleStringProperty demandProperty() {
        return demand;
    }

    public void setDemand(String demand) {
        this.demand.set(demand);
    }

    public String getMonthIncome() {
        return monthIncome.get();
    }

    public SimpleStringProperty monthIncomeProperty() {
        return monthIncome;
    }

    public void setMonthIncome(String monthIncome) {
        this.monthIncome.set(monthIncome);
    }
}
