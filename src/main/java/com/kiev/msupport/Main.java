package com.kiev.msupport;

import com.kiev.msupport.controller.db.MaterialsMngrBean;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    public static MaterialsMngrBean db = new MaterialsMngrBean();
    public static Stage mainStage;
    @Override
    public void start(Stage stage) throws Exception {
        db.bootstrap();

        AnchorPane page = (AnchorPane)FXMLLoader.load(this.getClass().getClassLoader().getResource("tabs.fxml"));
        Scene scene = new Scene(page);
        //maximized window
        Screen screen = Screen.getPrimary();
        Rectangle2D bounds = screen.getVisualBounds();

        stage.setX(bounds.getMinX());
        stage.setY(bounds.getMinY());
        stage.setWidth(bounds.getWidth());
        stage.setHeight(bounds.getHeight());
        //----

        stage.setTitle("Material and Technical Support");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
        mainStage = stage;
    }




}