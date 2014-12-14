package com.kiev.msupport.controller.view.analysis;

import com.kiev.msupport.Main;
import com.kiev.msupport.controller.db.MaterialsMngrBean;
import com.kiev.msupport.controller.utils.Images;
import com.kiev.msupport.controller.view.income.ComboItem;
import com.kiev.msupport.domain.*;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

public class AnalysisTableController implements Initializable {
    @FXML
    private Button makeScreenshot;
    @FXML
    private GridPane grid;
    @FXML
    private SplitPane snapshotArea;
    @FXML
    private TextField categoryName;
    @FXML
    private Label fullPrice;


    private MaterialsMngrBean db = Main.db;
    private ObservableList<AnalysisTable> data;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        makeScreenshot.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                try {
                    File tempFile = File.createTempFile("analysis", ".png");
                    WritableImage image = snapshotArea.snapshot(null, null);
                    ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", tempFile);
                    CategoryEntity category = db.categoryIfNotExist(categoryName.getText());
                    AnalysisEntity entity = new AnalysisEntity(new Date(), db.manager,
                            Images.ImageToByte(tempFile), category, fullPrice.getText());
                    db.updateEntity(entity);
                    tempFile.delete();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
