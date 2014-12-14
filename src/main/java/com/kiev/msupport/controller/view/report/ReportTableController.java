package com.kiev.msupport.controller.view.report;

import com.kiev.msupport.Main;
import com.kiev.msupport.controller.db.MaterialsMngrBean;
import com.kiev.msupport.controller.utils.Tables;
import com.kiev.msupport.domain.ReportEntity;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.*;
import java.util.List;

public class ReportTableController implements Initializable {
    @FXML
    private TableView<ReportTable> reportTable;
    @FXML
    private TableColumn<ReportTable, String> category;
    @FXML
    private TableColumn<ReportTable, String> name;
    @FXML
    private TableColumn<ReportTable, String> units;
    @FXML
    private TableColumn<ReportTable, String> dep;
    @FXML
    private TableColumn<ReportTable, String> tResidue;
    @FXML
    private TableColumn<ReportTable, String> pResidue;
    @FXML
    private TableColumn<ReportTable, String> fPrice;
    @FXML
    private TableColumn<ReportTable, String> demand;
    @FXML
    private TableColumn<ReportTable, String> monthIncome;
    @FXML
    private Button reimport;


    private Map<Long, ReportEntity> entities;
    MaterialsMngrBean db = Main.db;

    ObservableList<ReportTable> data = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        List<ReportTable> tableData = db.getReports(0, 10);
        data = FXCollections.observableArrayList(tableData);

        category.setCellValueFactory(new PropertyValueFactory<ReportTable, String>("category"));
        name.setCellValueFactory(new PropertyValueFactory<ReportTable, String>("name"));
        units.setCellValueFactory(new PropertyValueFactory<ReportTable, String>("units"));
        dep.setCellValueFactory(new PropertyValueFactory<ReportTable, String>("dep"));
        tResidue.setCellValueFactory(new PropertyValueFactory<ReportTable, String>("residueForToday"));
        fPrice.setCellValueFactory(new PropertyValueFactory<ReportTable, String>("fullPrice"));
        pResidue.setCellValueFactory(new PropertyValueFactory<ReportTable, String>("residueForPeriod"));
        demand.setCellValueFactory(new PropertyValueFactory<ReportTable, String>("demand"));
        monthIncome.setCellValueFactory(new PropertyValueFactory<ReportTable, String>("monthIncome"));

        for(TableColumn col : reportTable.getColumns()){
            Tables.makeHeaderWrappable(col);
        }

        reportTable.setItems(data);

        reimport.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                data = FXCollections.observableArrayList(db.getReports(0, 10));
                reportTable.setItems(data);
            }
        });
    }
}
