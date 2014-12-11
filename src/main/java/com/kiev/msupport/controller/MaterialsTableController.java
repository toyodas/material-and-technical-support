package com.kiev.msupport.controller;

import com.kiev.msupport.domain.MaterialEntity;
import com.kiev.msupport.domain.MaterialsMngrBean;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

import java.net.URL;
import java.util.*;

public class MaterialsTableController implements Initializable {
    @FXML
    private TableView<MaterialsTable> mtable;
    @FXML
    private TableColumn<MaterialsTable, Long> id;

    private TableColumn<MaterialsTable, String> name = createNameColumn();

    @FXML
    private TableColumn<MaterialsTable, Long> availableAmount;
    @FXML
    private TableColumn<MaterialsTable, Long> neededAmount;
    @FXML
    private TableColumn<MaterialsTable, String> lastUpdated;

    private Map<Long, MaterialEntity> entities;
    private Map<Long, MaterialDetail> details;
    MaterialsMngrBean db = new MaterialsMngrBean();

    ObservableList<MaterialsTable> data = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        db.bootstrap();

        List<MaterialsTable> tableData = new ArrayList<MaterialsTable>();
        List<MaterialEntity> list = db.findOffset(0, 10);

        details = new HashMap<Long, MaterialDetail>();
        entities = new HashMap<Long, MaterialEntity>();

        for(MaterialEntity m:list){
            MaterialsTable t = new MaterialsTable(m.getId(), m.getName(), m.getAvailable(), m.getNeeded(), m.getLastUpdated());
            tableData.add(t);
            entities.put(t.getId(), m);
            details.put(m.getId(), new MaterialDetail(m.getComment(), m.getDescription()));
        }

        data = FXCollections.observableArrayList(tableData);

        id.setCellValueFactory(new PropertyValueFactory<MaterialsTable, Long>("id"));
        name.setCellValueFactory(new PropertyValueFactory<MaterialsTable, String>("name"));
        availableAmount.setCellValueFactory(new PropertyValueFactory<MaterialsTable, Long>("availableAmount"));
        neededAmount.setCellValueFactory(new PropertyValueFactory<MaterialsTable, Long>("neededAmount"));
        lastUpdated.setCellValueFactory(new PropertyValueFactory<MaterialsTable, String>("lastUpdated"));

        mtable.getColumns().add(name);

        mtable.setItems(data);
    }

    private TableColumn<MaterialsTable, String> createNameColumn() {
        Callback<TableColumn<MaterialsTable, String>, TableCell<MaterialsTable, String>> editableFactory = new Callback<TableColumn<MaterialsTable, String>, TableCell<MaterialsTable, String>>() {
            @Override
            public TableCell<MaterialsTable, String> call(TableColumn p) {
                return new EditingCell();
            }
        };

        TableColumn<MaterialsTable, String> nameCol = new TableColumn<MaterialsTable, String>("Наименование");

        nameCol.setCellFactory(editableFactory);
        nameCol.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<MaterialsTable, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<MaterialsTable, String> t) {
                MaterialEntity e = entities.get(t.getRowValue().getId());
                e.setName(t.getNewValue());
                db.updateEntity(e);
                t.getRowValue().setName(t.getNewValue());
            }
        });

        return nameCol;
    }
}
