package com.kiev.msupport.controller.view.mtr;

import com.kiev.msupport.Main;
import com.kiev.msupport.controller.db.MaterialsMngrBean;
import com.kiev.msupport.domain.MTREntity;
import javafx.collections.FXCollections;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;


public class MTRTableController implements Initializable {
    @FXML
    private Button update;
    @FXML
    private TableView<MTRTable> mtrTable;
    @FXML
    private TableColumn<MTRTable, String> id;
    @FXML
    private TableColumn<MTRTable, String> category;
    @FXML
    private TableColumn<MTRTable, String> name;
    @FXML
    private TableColumn<MTRTable, String> units;

    MaterialsMngrBean db = Main.db;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        id.setCellValueFactory(new PropertyValueFactory<MTRTable, String>("id"));
        category.setCellValueFactory(new PropertyValueFactory<MTRTable, String>("category"));
        name.setCellValueFactory(new PropertyValueFactory<MTRTable, String>("name"));
        units.setCellValueFactory(new PropertyValueFactory<MTRTable, String>("units"));

        update.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                mtrTable.setItems(FXCollections.observableArrayList(mtrTablesData()));
            }
        });

        mtrTable.setItems(FXCollections.observableArrayList(mtrTablesData()));
    }

    private List<MTRTable> mtrTablesData(){
        List<MTREntity> mtrs = db.findAll(MTREntity.class);
        List<MTRTable> mtrTable = new ArrayList<MTRTable>();
        for(MTREntity e:mtrs){
            mtrTable.add(new MTRTable(e));
        }
        return mtrTable;
    }
}
