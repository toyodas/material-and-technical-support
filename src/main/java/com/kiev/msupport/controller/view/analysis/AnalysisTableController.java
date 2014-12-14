package com.kiev.msupport.controller.view.analysis;

import com.kiev.msupport.Main;
import com.kiev.msupport.controller.db.MaterialsMngrBean;
import com.kiev.msupport.controller.utils.EditingCell;
import com.kiev.msupport.controller.utils.Images;
import com.kiev.msupport.controller.utils.Tables;
import com.kiev.msupport.controller.view.expense.ComboItem;
import com.kiev.msupport.controller.view.expense.ExpenseTable;
import com.kiev.msupport.domain.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.SnapshotParameters;
import javafx.scene.SnapshotResult;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.util.Callback;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class AnalysisTableController implements Initializable {
    @FXML
    private Button makeScreenshot;
    @FXML
    private GridPane grid;
    @FXML
    private SplitPane snapshotArea;



    private MaterialsMngrBean db = Main.db;
    private ObservableList<AnalysisTable> data;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        makeScreenshot.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                try {
                    File tempFile = File.createTempFile("analysis", ".png");
                    WritableImage image = grid.snapshot(null, null);
                    ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", tempFile);

                    //todo: fully fill
                    AnalysisEntity entity = new AnalysisEntity(new Date(), db.manager, Images.ImageToByte(tempFile));
                    db.updateEntity(entity);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
