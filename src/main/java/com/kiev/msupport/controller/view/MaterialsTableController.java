package com.kiev.msupport.controller.view;

import com.kiev.msupport.controller.db.MaterialsMngrBean;
import com.kiev.msupport.domain.MaterialEntity;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.StackPane;
import javafx.scene.text.TextAlignment;
import javafx.util.Callback;

import java.net.URL;
import java.util.*;
import java.util.List;

public class MaterialsTableController implements Initializable {
    @FXML
    private TableView<MaterialsTable> mtable;
    @FXML
    private TableColumn<MaterialsTable, Long> id;
    @FXML
    private TableColumn<MaterialsTable, String> category;
    @FXML
    private TableColumn<MaterialsTable, String> name;
    @FXML
    private TableColumn<MaterialsTable, String> units;
    @FXML
    private TableColumn<MaterialsTable, String> dep;
    @FXML
    private TableColumn<MaterialsTable, String> tResidue;
    @FXML
    private TableColumn<MaterialsTable, String> pResidue;
    @FXML
    private TableColumn<MaterialsTable, String> fPrice;
    @FXML
    private TableColumn<MaterialsTable, String> demand;
    @FXML
    private TableColumn<MaterialsTable, String> monthIncome;


    private Map<Long, MaterialEntity> entities;
    private Map<Long, MaterialDetail> details;
    MaterialsMngrBean db = new MaterialsMngrBean();

    ObservableList<MaterialsTable> data = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        db.bootstrap();

        List<MaterialsTable> tableData = new ArrayList<MaterialsTable>();
        List<MaterialEntity> list = db.findOffset(0, 20);

        entities = new HashMap<Long, MaterialEntity>();
//
//        for(MaterialEntity m:list){
//            MaterialsTable t = new MaterialsTable(
//                    m.getId(), m.getMtr().getCategory().getName(), m.getMtr().getName(), m..getMtr().getUnits().getName(),
//                    m.getDepartment().getName(), m.getResidueForToday(), m.getResidueForPeriod(),
//                    m.getFullPrice(), m.getDemand(), m.getMonthIncome());
//            tableData.add(t);
//            entities.put(t.getId(), m);
//        }

        data = FXCollections.observableArrayList(tableData);

        id.setCellValueFactory(new PropertyValueFactory<MaterialsTable, Long>("id"));
        category.setCellValueFactory(new PropertyValueFactory<MaterialsTable, String>("category"));
        name.setCellValueFactory(new PropertyValueFactory<MaterialsTable, String>("name"));
        units.setCellValueFactory(new PropertyValueFactory<MaterialsTable, String>("units"));
        dep.setCellValueFactory(new PropertyValueFactory<MaterialsTable, String>("dep"));
        tResidue.setCellValueFactory(new PropertyValueFactory<MaterialsTable, String>("tResidue"));
        fPrice.setCellValueFactory(new PropertyValueFactory<MaterialsTable, String>("fPrice"));
        pResidue.setCellValueFactory(new PropertyValueFactory<MaterialsTable, String>("pResidue"));
        demand.setCellValueFactory(new PropertyValueFactory<MaterialsTable, String>("demand"));
        monthIncome.setCellValueFactory(new PropertyValueFactory<MaterialsTable, String>("monthIncome"));

        for(TableColumn col :mtable.getColumns()){
            makeHeaderWrappable(col);
        }

        mtable.setItems(data);
    }

    private void makeHeaderWrappable(TableColumn col) {
        Label label = new Label(col.getText());
        label.setStyle("-fx-padding: 8px;");
        label.setWrapText(true);
        label.setAlignment(Pos.CENTER);
        label.setTextAlignment(TextAlignment.CENTER);

        col.setPrefWidth(100);
        StackPane stack = new StackPane();
        stack.getChildren().add(label);
        stack.prefWidthProperty().bind(col.widthProperty().subtract(5));
        label.prefWidthProperty().bind(stack.prefWidthProperty());
        col.setGraphic(stack);
    }

//    private TableColumn<MaterialsTable, String> createNameColumn() {
//        Callback<TableColumn<MaterialsTable, String>, TableCell<MaterialsTable, String>> editableFactory = new Callback<TableColumn<MaterialsTable, String>, TableCell<MaterialsTable, String>>() {
//            @Override
//            public TableCell<MaterialsTable, String> call(TableColumn p) {
//                return new EditingCell();
//            }
//        };
//
//        TableColumn<MaterialsTable, String> nameCol = new TableColumn<MaterialsTable, String>("Наименование");
//
//        nameCol.setCellFactory(editableFactory);
//        nameCol.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<MaterialsTable, String>>() {
//            @Override
//            public void handle(TableColumn.CellEditEvent<MaterialsTable, String> t) {
//                MaterialEntity e = entities.get(t.getRowValue().getId());
//                e.setName(t.getNewValue());
//                db.updateEntity(e);
//                t.getRowValue().setName(t.getNewValue());
//            }
//        });
//
//        return nameCol;
//    }
}
