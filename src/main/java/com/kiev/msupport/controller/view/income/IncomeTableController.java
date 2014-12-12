package com.kiev.msupport.controller.view.income;

import com.kiev.msupport.Main;
import com.kiev.msupport.controller.db.MaterialsMngrBean;
import com.kiev.msupport.controller.utils.EditingCell;
import com.kiev.msupport.controller.utils.Tables;
import com.kiev.msupport.domain.CategoryEntity;
import com.kiev.msupport.domain.MaterialEntity;
import com.kiev.msupport.domain.UnitEntity;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.event.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
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
    private ComboBox<CategoryCombo> categoryIt;
    @FXML
    private TextField nameIt;
    @FXML
    private ComboBox<UnitCombo> unitIt;
    @FXML
    private TextField amountIt;
    @FXML
    private TextField priceIt;
    @FXML
    private Button addToTableB;
    @FXML
    private Button importFromFileB;
    @FXML
    private Button addToDBB;


    MaterialsMngrBean db = Main.db;

    ObservableList<IncomeTable> data;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
//
        List<CategoryEntity> categoryEntities = db.findAll(CategoryEntity.class);
        List<CategoryCombo> categoryCombos = new ArrayList<CategoryCombo>();
        for(CategoryEntity e:categoryEntities){
            categoryCombos.add(new CategoryCombo(e.getId(), e.getName()));
        }
        categoryIt.setItems(FXCollections.observableArrayList(categoryCombos));

        List<UnitEntity> unitEntities = db.findAll(UnitEntity.class);
        List<UnitCombo> unitCombos = new ArrayList<UnitCombo>();
        for(UnitEntity e:unitEntities){
            unitCombos.add(new UnitCombo(e.getId(), e.getName()));
        }

        unitIt.setItems(FXCollections.observableArrayList(unitCombos));


//table
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


        price.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<IncomeTable, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<IncomeTable, String> t) {
                BigDecimal amount = new BigDecimal(t.getRowValue().getPrice());
                BigDecimal price = new BigDecimal(t.getNewValue());
                BigDecimal withTax = amount.multiply(price);
                BigDecimal withNoTax = withTax.multiply(new BigDecimal("0.2"));

                t.getRowValue().setNoTax(withTax.toString());
                t.getRowValue().setTax(withNoTax.toString());
            }
        });

        amount.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<IncomeTable, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<IncomeTable, String> t) {
                BigDecimal amount = new BigDecimal(t.getNewValue());
                BigDecimal price = new BigDecimal(t.getRowValue().getPrice());
                BigDecimal withTax = amount.multiply(price);
                BigDecimal withNoTax = withTax.multiply(new BigDecimal("0.2"));

                t.getRowValue().setNoTax(withTax.toString());
                t.getRowValue().setTax(withNoTax.toString());
            }
        });

        incomeTable.setItems(data);
    }


}
