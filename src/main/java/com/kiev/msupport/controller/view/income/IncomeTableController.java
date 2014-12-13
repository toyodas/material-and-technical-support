package com.kiev.msupport.controller.view.income;

import com.kiev.msupport.Main;
import com.kiev.msupport.controller.db.MaterialsMngrBean;
import com.kiev.msupport.controller.utils.EditingCell;
import com.kiev.msupport.controller.utils.Tables;
import com.kiev.msupport.domain.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.event.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;

import java.math.BigDecimal;
import java.net.URL;
import java.util.*;

public class IncomeTableController implements Initializable {
    @FXML
    private TableView<IncomeTable> incomeTable;
    @FXML
    private TableColumn<IncomeTable, String> category;
    @FXML
    private TableColumn<IncomeTable, String> name;
    @FXML
    private TableColumn<IncomeTable, String> units;
    @FXML
    private TableColumn<IncomeTable, String> amount;
    @FXML
    private TableColumn<IncomeTable, String> price;
    @FXML
    private TableColumn<IncomeTable, String> noTaxPrice;
    @FXML
    private TableColumn<IncomeTable, String> taxPrice;

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
    private TextField priceIt;
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

    ObservableList<IncomeTable> data;

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
        category.setCellValueFactory(new PropertyValueFactory<IncomeTable, String>("category"));
        name.setCellValueFactory(new PropertyValueFactory<IncomeTable, String>("name"));
        units.setCellValueFactory(new PropertyValueFactory<IncomeTable, String>("units"));
        amount.setCellValueFactory(new PropertyValueFactory<IncomeTable, String>("amount"));
        price.setCellValueFactory(new PropertyValueFactory<IncomeTable, String>("price"));
        noTaxPrice.setCellValueFactory(new PropertyValueFactory<IncomeTable, String>("noTax"));
        taxPrice.setCellValueFactory(new PropertyValueFactory<IncomeTable, String>("tax"));

        incomeTable.setEditable(true);

        Callback<TableColumn<IncomeTable, String>, TableCell<IncomeTable, String>> editableFactory = new Callback<TableColumn<IncomeTable, String>, TableCell<IncomeTable, String>>() {
            @Override
            public TableCell<IncomeTable, String> call(TableColumn p) {
                return new EditingCell<IncomeTable>();
            }
        };


        int count = 0;
        for (TableColumn col : incomeTable.getColumns()) {
            Tables.makeHeaderWrappable(col);
            count++;
        }

        for (int i = 0; i < count - 2; i++) {
            TableColumn<IncomeTable, String> col = (TableColumn<IncomeTable, String>) incomeTable.getColumns().get(i);
            col.setCellFactory(editableFactory);
        }


//events handling
        EventHandler<TableColumn.CellEditEvent<IncomeTable, String>> eh1 =  new EventHandler<TableColumn.CellEditEvent<IncomeTable, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<IncomeTable, String> t) {
                BigDecimal amount = new BigDecimal(t.getRowValue().getPrice());
                BigDecimal price = new BigDecimal(t.getNewValue());
                BigDecimal withNoTax = amount.multiply(price);
                BigDecimal withTax = withNoTax.add(withNoTax.multiply(new BigDecimal("0.2")));

                t.getRowValue().setNoTax(withNoTax.toString());
                t.getRowValue().setTax(withTax.toString());
            }
        };

        price.setOnEditCommit(eh1);

        EventHandler<TableColumn.CellEditEvent<IncomeTable, String>> eh2 =
                new EventHandler<TableColumn.CellEditEvent<IncomeTable, String>>() {
                    @Override
                    public void handle(TableColumn.CellEditEvent<IncomeTable, String> t) {
                        BigDecimal amount = new BigDecimal(t.getNewValue());
                        BigDecimal price = new BigDecimal(t.getRowValue().getPrice());
                        BigDecimal withNoTax = amount.multiply(price);
                        BigDecimal withTax = withNoTax.add(withNoTax.multiply(new BigDecimal("0.2")));

                        t.getRowValue().setNoTax(withNoTax.toString());
                        t.getRowValue().setTax(withTax.toString());
                    }
                };

        amount.setOnEditCommit(eh2);

        addToTableB.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                error.setVisible(false);
                if (categoryIt.getValue() == null || nameIt.getText() == null
                        || unitIt.getValue() == null || depIt.getValue() == null
                        || amountIt.getText() == null || priceIt.getText() == null) {
                    error.setVisible(true);
                    return;
                }

                IncomeTable val = new IncomeTable(categoryIt.getValue().getName(),
                        nameIt.getText(), unitIt.getValue().getName(), depIt.getValue().getName(),
                        amountIt.getText(), priceIt.getText()
                );

                val.setCategoryId(categoryIt.getValue().getId());
                val.setUnitsId(unitIt.getValue().getId());
                val.setDepId(depIt.getValue().getId());

                if (incomeTable.getItems() == null) {
                    incomeTable.setItems(FXCollections.<IncomeTable>observableArrayList());
                }

                BigDecimal amount = new BigDecimal(val.getAmount());
                BigDecimal price = new BigDecimal(val.getPrice());
                BigDecimal withNoTax = amount.multiply(price);
                BigDecimal withTax = withNoTax.add(withNoTax.multiply(new BigDecimal("0.2")));

                val.setNoTax(withNoTax.toString());
                val.setTax(withTax.toString());

                incomeTable.getItems().addAll(val);
            }
        });

        addToDBB.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                List<IncomeTable> tableData = incomeTable.getItems();
                for(IncomeTable t:tableData){

                    CategoryEntity category = db.getEntity(CategoryEntity.class, t.getCategoryId());
                    UnitEntity units = db.getEntity(UnitEntity.class, t.getUnitsId());
                    DepartmentEntity dep = db.getEntity(DepartmentEntity.class, t.getDepId());

                    MTREntity mtr = db.getMTR(t.getCategoryId(), t.getUnitsId(), t.getName());
                    if(mtr == null){
                        mtr = db.updateEntity(new MTREntity(category, t.getName(), units));
                    }

                    IncomeEntity en = new IncomeEntity(mtr, t.getAmount(), t.getPrice(), dep, new Date().toString(), db.manager);
                    db.updateEntity(en);
                }

                incomeTable.setItems(FXCollections.observableArrayList(new ArrayList<IncomeTable>()));
            }
        });

        incomeTable.setItems(data);
    }

}
