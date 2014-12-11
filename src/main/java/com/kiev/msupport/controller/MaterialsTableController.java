package com.kiev.msupport.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.awt.*;
import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

public class MaterialsTableController implements Initializable{
    @FXML private TableView<MaterialsTable> tableId;
    @FXML private TableColumn<MaterialsTable, Long> id;
    @FXML private TableColumn<MaterialsTable, String> name;
    @FXML private TableColumn<MaterialsTable, Long> availableAmount;
    @FXML private TableColumn<MaterialsTable, Long> neededAmount;
    @FXML private TableColumn<MaterialsTable, String> lastUpdated;

    final ObservableList<MaterialsTable> data = FXCollections.observableArrayList(
            new MaterialsTable(1l, "Жопа мира1", 0l, 20l, new Date().toString()),
            new MaterialsTable(2l, "Жопа мира2", 0l, 20l, new Date().toString()),
            new MaterialsTable(3l, "Жопа мира3", 0l, 20l, new Date().toString())
    );

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        id.setCellValueFactory(new PropertyValueFactory<MaterialsTable, Long>("id"));
        name.setCellValueFactory(new PropertyValueFactory<MaterialsTable, String>("name"));
        availableAmount.setCellValueFactory(new PropertyValueFactory<MaterialsTable, Long>("availableAmount"));
        neededAmount.setCellValueFactory(new PropertyValueFactory<MaterialsTable, Long>("neededAmount"));
        lastUpdated.setCellValueFactory(new PropertyValueFactory<MaterialsTable, String>("lastUpdated"));

        tableId.setItems(data);
    }
}
