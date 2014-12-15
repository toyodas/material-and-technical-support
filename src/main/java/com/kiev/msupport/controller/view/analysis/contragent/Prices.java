package com.kiev.msupport.controller.view.analysis.contragent;

import com.kiev.msupport.controller.view.analysis.ProductTable;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.TableView;

public class Prices {
    private SimpleStringProperty priceForOne;
    private SimpleStringProperty priceForAll;

    private TableView<ProductTable> product;
    private int row;

    public Prices(String priceForOne, String priceForAll, TableView<ProductTable> product, int row) {
        this.priceForOne = new SimpleStringProperty(priceForOne);
        this.priceForAll = new SimpleStringProperty(priceForAll);
        this.product = product;
        this.row = row;
    }

    public String getPriceForOne() {
        return priceForOne.get();
    }

    public SimpleStringProperty priceForOneProperty() {
        return priceForOne;
    }

    public void setPriceForOne(String priceForOne) {
        this.priceForOne.set(priceForOne);
    }

    public String getPriceForAll() {
        return priceForAll.get();
    }

    public SimpleStringProperty priceForAllProperty() {
        return priceForAll;
    }

    public void setPriceForAll(String priceForAll) {
        this.priceForAll.set(priceForAll);
    }

    public TableView<ProductTable> getProduct() {
        return product;
    }

    public void setProduct(TableView<ProductTable> product) {
        this.product = product;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }
}