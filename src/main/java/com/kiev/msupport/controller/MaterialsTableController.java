package com.kiev.msupport.controller;

import com.kiev.msupport.domain.MaterialEntity;
import com.kiev.msupport.domain.MaterialsMngrBean;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.*;

public class MaterialsTableController implements Initializable {
    @FXML
    private TableView<MaterialsTable> tableId;
    @FXML
    private TableColumn<MaterialsTable, Long> id;
    @FXML
    private TableColumn<MaterialsTable, String> name;
    @FXML
    private TableColumn<MaterialsTable, Long> availableAmount;
    @FXML
    private TableColumn<MaterialsTable, Long> neededAmount;
    @FXML
    private TableColumn<MaterialsTable, String> lastUpdated;

    private List<MaterialEntity> entities;
    private Map<Long, MaterialDetail> details;

    ObservableList<MaterialsTable> data = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        MaterialsMngrBean db = new MaterialsMngrBean();
        db.bootstrap();

        List<MaterialsTable> tableData = new ArrayList<MaterialsTable>();
        entities = db.findOffset(0, 10);
        details = new HashMap<Long, MaterialDetail>();

        for(MaterialEntity m:entities){
            tableData.add(new MaterialsTable(m.getId(), m.getName(), m.getAvailable(), m.getNeeded(), m.getLastUpdated()));
            details.put(m.getId(), new MaterialDetail(m.getComment(), m.getDescription()));
        }

        data = FXCollections.observableArrayList(tableData);

        id.setCellValueFactory(new PropertyValueFactory<MaterialsTable, Long>("id"));
        name.setCellValueFactory(new PropertyValueFactory<MaterialsTable, String>("name"));
        availableAmount.setCellValueFactory(new PropertyValueFactory<MaterialsTable, Long>("availableAmount"));
        neededAmount.setCellValueFactory(new PropertyValueFactory<MaterialsTable, Long>("neededAmount"));
        lastUpdated.setCellValueFactory(new PropertyValueFactory<MaterialsTable, String>("lastUpdated"));

        tableId.setItems(data);
    }



}
