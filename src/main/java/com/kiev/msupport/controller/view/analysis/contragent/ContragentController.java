package com.kiev.msupport.controller.view.analysis.contragent;

import com.kiev.msupport.controller.utils.EditingCell;
import com.kiev.msupport.controller.view.analysis.ProductTable;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.util.Callback;

import java.math.BigDecimal;

public class ContragentController {
    public final long index;
    public TextField name;
    public TextField paymentConditions;
    public TextField deliveryConditions;
    public TextField deliveryDate;
    public TextField logistics;
    public TableView<Prices> price;
    public TableColumn<Prices, String> priceForOne;
    public TableColumn<Prices, String> priceForAll;
    public Label fullPriceWithTax;
    public Label selectedSumPrice;


    public ContragentController(final long index) {
        this.index = index;
        name = new TextField("");
        name.setPromptText("Поставщик");

        paymentConditions = new TextField("");
        paymentConditions.setPromptText("Предоплата, отсрочка оплаты");

        deliveryConditions = new TextField("");
        deliveryConditions.setPromptText("CPT, FCA .. etc");

        deliveryDate = new TextField("");
        deliveryDate.setPromptText("к. дней");

        logistics = new TextField("");

        price = new TableView<Prices>();
        price.setEditable(true);

        Callback<TableColumn<Prices, String>, TableCell<Prices, String>> editableFactory = new Callback<TableColumn<Prices, String>, TableCell<Prices, String>>() {
            @Override
            public TableCell<Prices, String> call(TableColumn p) {
                return new EditingCell<Prices>();
            }
        };

        priceForOne = new TableColumn<Prices, String>("Цена без НДС");

        priceForOne.setEditable(true);
        priceForOne.setCellValueFactory(new PropertyValueFactory<Prices, String>("priceForOne"));

        priceForOne.setCellFactory(editableFactory);
        priceForOne.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Prices, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Prices, String> t) {

                Prices prices = t.getRowValue();
                BigDecimal v = new BigDecimal(t.getNewValue());
                int row = prices.getRow();
                ProductTable pt = prices.getProduct().getItems().get(row);
                String amountS = pt.getAmount();
                BigDecimal amount = new BigDecimal(amountS);
                t.getRowValue().setPriceForAll(v.multiply(amount).toString());

                BigDecimal min = v;
                for(ContragentController c: pt.getClist()){
                    Prices p = c.price.getItems().get(row);
                    BigDecimal val = new BigDecimal(p.getPriceForOne());
                    if(v.compareTo(val) == 1){
                        min = val;
                    }
                }

                BigDecimal sum = new BigDecimal(0);
                for(Prices p : price.getItems()){
                    sum = sum.add(new BigDecimal(p.getPriceForAll()));
                }

                fullPriceWithTax.setText(sum.toString());

                prices.getProduct().getItems().get(row).setMinPrice(min.toString());

                t.getRowValue().setPriceForOne(t.getNewValue());
            }
        });

        priceForAll = new TableColumn<Prices, String>("Сумма с НДС");
        priceForAll.setCellValueFactory(new PropertyValueFactory<Prices, String>("priceForAll"));

        price.getColumns().addAll(priceForOne, priceForAll);

        fullPriceWithTax = new Label("");
        selectedSumPrice = new Label("");
    }

    public void newGridColumn(int colIndex, GridPane gridPane){
        gridPane.addColumn(colIndex);
        int row = 0;
        gridPane.getColumnConstraints().addAll(new ColumnConstraints());
        gridPane.add(anchored(name), colIndex, row++);
        gridPane.add(anchored(paymentConditions), colIndex, row++);
        gridPane.add(anchored(deliveryConditions), colIndex, row++);
        gridPane.add(anchored(deliveryDate), colIndex, row++);
        gridPane.add(anchored(logistics), colIndex, row++);
        gridPane.add(anchored(price), colIndex, row++);
        gridPane.add(anchored(fullPriceWithTax), colIndex, row++);
        gridPane.add(anchored(selectedSumPrice), colIndex, row++);
    }

    private AnchorPane anchored(Node node){
        AnchorPane anchorPane = new AnchorPane();
        AnchorPane.setTopAnchor(node, 0d);
        AnchorPane.setBottomAnchor(node, 0d);
        AnchorPane.setRightAnchor(node, 0d);
        AnchorPane.setLeftAnchor(node, 0d);

        anchorPane.getChildren().add(node);
        return anchorPane;
    }
}
