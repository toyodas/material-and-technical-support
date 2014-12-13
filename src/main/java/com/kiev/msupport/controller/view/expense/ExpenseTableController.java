package com.kiev.msupport.controller.view.expense;

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

public class ExpenseTableController implements Initializable {
    @FXML
    private TableView<ExpenseTable> expenseTable;
    @FXML
    private TableColumn<ExpenseTable, String> category;
    @FXML
    private TableColumn<ExpenseTable, String> name;
    @FXML
    private TableColumn<ExpenseTable, String> units;
    @FXML
    private TableColumn<ExpenseTable, String> amount;

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

    MaterialsMngrBean db = Main.db;

    ObservableList<ExpenseTable> data;

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
        category.setCellValueFactory(new PropertyValueFactory<ExpenseTable, String>("category"));
        name.setCellValueFactory(new PropertyValueFactory<ExpenseTable, String>("name"));
        units.setCellValueFactory(new PropertyValueFactory<ExpenseTable, String>("units"));
        amount.setCellValueFactory(new PropertyValueFactory<ExpenseTable, String>("amount"));


        expenseTable.setEditable(true);

        Callback<TableColumn<ExpenseTable, String>, TableCell<ExpenseTable, String>> editableFactory = new Callback<TableColumn<ExpenseTable, String>, TableCell<ExpenseTable, String>>() {
            @Override
            public TableCell<ExpenseTable, String> call(TableColumn p) {
                return new EditingCell<ExpenseTable>();
            }
        };


        for (TableColumn col : expenseTable.getColumns()) {
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

                ExpenseTable val = new ExpenseTable(categoryIt.getValue().getName(),
                        nameIt.getText(), unitIt.getValue().getName(), depIt.getValue().getName(),
                        amountIt.getText()
                );

                val.setCategoryId(categoryIt.getValue().getId());
                val.setUnitsId(unitIt.getValue().getId());
                val.setDepId(depIt.getValue().getId());

                if (expenseTable.getItems() == null) {
                    expenseTable.setItems(FXCollections.<ExpenseTable>observableArrayList());
                }

                expenseTable.getItems().addAll(val);
            }
        });

        addToDBB.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                List<ExpenseTable> tableData = expenseTable.getItems();
                for(ExpenseTable t:tableData){

                    CategoryEntity category = db.getEntity(CategoryEntity.class, t.getCategoryId());
                    UnitEntity units = db.getEntity(UnitEntity.class, t.getUnitsId());
                    DepartmentEntity dep = db.getEntity(DepartmentEntity.class, t.getDepId());

                    MTREntity mtr = db.getMTR(t.getCategoryId(), t.getUnitsId(), t.getName());
                    if(mtr == null){
                        mtr = db.updateEntity(new MTREntity(category, t.getName(), units));
                    }

                    ExpenseEntity en = new ExpenseEntity(mtr, t.getAmount(), dep, new Date().toString(), db.manager);
                    db.updateEntity(en);
                }

                expenseTable.setItems(FXCollections.observableArrayList(new ArrayList<ExpenseTable>()));
            }
        });

        expenseTable.setItems(data);
    }

}
