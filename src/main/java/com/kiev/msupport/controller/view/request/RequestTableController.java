package com.kiev.msupport.controller.view.request;

import com.kiev.msupport.Main;
import com.kiev.msupport.controller.db.MaterialsMngrBean;
import com.kiev.msupport.controller.utils.EditingCell;
import com.kiev.msupport.controller.utils.Tables;
import com.kiev.msupport.domain.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;

import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class RequestTableController implements Initializable {
    @FXML
    private TableView<RequestTable> requestTable;
    @FXML
    private TableColumn<RequestTable, String> category;
    @FXML
    private TableColumn<RequestTable, String> name;
    @FXML
    private TableColumn<RequestTable, String> units;
    @FXML
    private TableColumn<RequestTable, String> amount;
    @FXML
    private ComboBox<ComboItem> categoryIt;
    @FXML
    private TextField nameIt;
    @FXML
    private ComboBox<ComboItem> unitIt;
    @FXML
    private ComboBox<ComboItem> depIt;
    @FXML
    private TextField amountIt;
    @FXML
    private Button addToTableB;
    @FXML
    private Label error;
    @FXML
    private Button addToDBB;
    @FXML
    private ListView<String> presentNames;
    @FXML
    private Tooltip tooltip;
    @FXML
    private Button reimport;

    MaterialsMngrBean db = Main.db;

    ObservableList<RequestTable> data;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
//inputs config
        List<CategoryEntity> categoryEntities = db.findAll(CategoryEntity.class);
        List<ComboItem> comboItems = new ArrayList<ComboItem>();
        for(CategoryEntity e:categoryEntities){
            comboItems.add(new ComboItem(e.getId(), e.getName()));
        }
        categoryIt.setItems(FXCollections.observableArrayList(comboItems));

        List<UnitEntity> unitEntities = db.findAll(UnitEntity.class);
        List<ComboItem> unitCombos = new ArrayList<ComboItem>();
        for(UnitEntity e:unitEntities){
            unitCombos.add(new ComboItem(e.getId(), e.getName()));
        }

        unitIt.setItems(FXCollections.observableArrayList(unitCombos));


        List<DepartmentEntity> departmentEntities  = db.findAll(DepartmentEntity.class);
        List<ComboItem> depCombos = new ArrayList<ComboItem>();
        for(DepartmentEntity e:departmentEntities){
            depCombos.add(new ComboItem(e.getId(), e.getName()));
        }

        depIt.setItems(FXCollections.observableArrayList(depCombos));

//table config
        category.setCellValueFactory(new PropertyValueFactory<RequestTable, String>("category"));
        name.setCellValueFactory(new PropertyValueFactory<RequestTable, String>("name"));
        units.setCellValueFactory(new PropertyValueFactory<RequestTable, String>("units"));
        amount.setCellValueFactory(new PropertyValueFactory<RequestTable, String>("amount"));


        requestTable.setEditable(true);

        Callback<TableColumn<RequestTable, String>, TableCell<RequestTable, String>> editableFactory = new Callback<TableColumn<RequestTable, String>, TableCell<RequestTable, String>>() {
            @Override
            public TableCell<RequestTable, String> call(TableColumn p) {
                return new EditingCell<RequestTable>();
            }
        };


        for (TableColumn col : requestTable.getColumns()) {
            Tables.makeHeaderWrappable(col);
            col.setCellFactory(editableFactory);
        }


//events handling
        addToTableB.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                error.setVisible(false);
                if (categoryIt.getValue() == null || nameIt.getText() == null
                        || unitIt.getValue() == null || depIt.getValue() == null
                        || amountIt.getText() == null) {
                    error.setVisible(true);
                    return;
                }

                RequestTable val = new RequestTable(categoryIt.getValue().getName(),
                        nameIt.getText(), unitIt.getValue().getName(), depIt.getValue().getName(),
                        amountIt.getText()
                );

                val.setCategoryId(categoryIt.getValue().getId());
                val.setUnitsId(unitIt.getValue().getId());
                val.setDepId(depIt.getValue().getId());

                if (requestTable.getItems() == null) {
                    requestTable.setItems(FXCollections.<RequestTable>observableArrayList());
                }

                requestTable.getItems().addAll(val);
            }
        });

        addToDBB.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                List<RequestTable> tableData = requestTable.getItems();
                for(RequestTable t:tableData){

                    CategoryEntity category = db.getEntity(CategoryEntity.class, t.getCategoryId());
                    UnitEntity units = db.getEntity(UnitEntity.class, t.getUnitsId());
                    DepartmentEntity dep = db.getEntity(DepartmentEntity.class, t.getDepId());

                    MTREntity mtr = db.getMTR(t.getCategoryId(), t.getUnitsId(), t.getName());
                    if(mtr == null){
                        mtr = db.updateEntity(new MTREntity(category, t.getName(), units));
                    }

                    RequestEntity en = new RequestEntity(mtr, t.getAmount(), dep, new Date(), db.manager);
                    db.updateEntity(en);
                }

                requestTable.setItems(FXCollections.observableArrayList(new ArrayList<RequestTable>()));
            }
        });

        requestTable.setItems(data);
    }

}
