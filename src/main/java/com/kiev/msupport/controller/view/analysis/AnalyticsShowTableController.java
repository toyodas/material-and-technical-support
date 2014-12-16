package com.kiev.msupport.controller.view.analysis;

import com.kiev.msupport.Main;
import com.kiev.msupport.controller.db.MaterialsMngrBean;
import com.kiev.msupport.controller.utils.ComboItem;
import com.kiev.msupport.controller.utils.Tables;
import com.kiev.msupport.domain.AnalysisEntity;
import com.kiev.msupport.domain.CategoryEntity;
import com.kiev.msupport.domain.Manager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.print.*;
import java.io.ByteArrayInputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class AnalyticsShowTableController implements Initializable {
    @FXML
    private TableView<AnalyticsTable> showTable;
    @FXML
    private TableColumn<AnalyticsTable, Long> id;
    @FXML
    private TableColumn<AnalyticsTable, String> date;
    @FXML
    private TableColumn<AnalyticsTable, String> category;
    @FXML
    private TableColumn<AnalyticsTable, String> price;
    @FXML
    private TableColumn<AnalyticsTable, String> manager;
    @FXML
    private TableColumn<AnalyticsTable, ImageView> imageView;
    @FXML
    private Button showBigImage;
    @FXML
    private Button printButton;
    @FXML
    private Button updateData;
    @FXML
    private ComboBox<ComboItem> categoryFilter;
    @FXML
    private ComboBox<ComboItem> managerFilter;
    @FXML
    private Button filterB;
    @FXML
    private Button unfilterB;




    private MaterialsMngrBean db = Main.db;
    private ObservableList<AnalyticsTable> data;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        List<CategoryEntity> categoryEntities = db.findAll(CategoryEntity.class);
        List<ComboItem> comboItems = new ArrayList<ComboItem>();
        for(CategoryEntity e:categoryEntities){
            comboItems.add(new ComboItem(e.getId(), e.getName()));
        }
        categoryFilter.setItems(FXCollections.observableArrayList(comboItems));

        final List<Manager> managers = db.findAll(Manager.class);
        List<ComboItem> managersItems = new ArrayList<ComboItem>();
        for(Manager e:managers){
            managersItems.add(new ComboItem(e.getId(), e.getFullName()));
        }
        managerFilter.setItems(FXCollections.observableArrayList(managersItems));

        filterB.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                List<AnalysisEntity> tableData;
                if (managerFilter.getValue() == null && categoryFilter.getValue() != null) {
                    tableData = db.getAnalyticsByCategory(categoryFilter.getValue().getId());
                    data = FXCollections.observableArrayList(getData(tableData));
                    showTable.setItems(data);
                }

                if (managerFilter.getValue() != null && categoryFilter.getValue() == null) {
                    tableData = db.getAnalyticsByManager(managerFilter.getValue().getId());
                    data = FXCollections.observableArrayList(getData(tableData));
                    showTable.setItems(data);
                }

                if (managerFilter.getValue() != null && categoryFilter.getValue() != null) {
                    tableData = db.getAnalyticsByManagerAndCategory(
                            managerFilter.getValue().getId(),
                            categoryFilter.getValue().getId()
                    );
                    data = FXCollections.observableArrayList(getData(tableData));
                    showTable.setItems(data);
                }
            }
        });

        unfilterB.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                data = FXCollections.observableArrayList(getData(db.<AnalysisEntity>findAll(AnalysisEntity.class)));
                showTable.setItems(data);
            }
        });

        id.setCellValueFactory(new PropertyValueFactory<AnalyticsTable, Long>("id"));
        date.setCellValueFactory(new PropertyValueFactory<AnalyticsTable, String>("date"));
        category.setCellValueFactory(new PropertyValueFactory<AnalyticsTable, String>("category"));
        price.setCellValueFactory(new PropertyValueFactory<AnalyticsTable, String>("date"));
        manager.setCellValueFactory(new PropertyValueFactory<AnalyticsTable, String>("manager"));
        imageView.setCellValueFactory(new PropertyValueFactory<AnalyticsTable, ImageView>("imageView"));

        for (TableColumn col : showTable.getColumns()) {
            Tables.makeHeaderWrappable(col);
        }

        printButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                //getting image of selected
                AnalyticsTable at = showTable.getSelectionModel().getSelectedItem();
                if(at.getImageView() == null) return;
                final ImageView image = new ImageView(at.getImageView().getImage());

                print(image);
            }
        });

        showBigImage.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                //getting image of selected
                AnalyticsTable at = showTable.getSelectionModel().getSelectedItem();
                if(at.getImageView() == null) return;
                final ImageView image = new ImageView(at.getImageView().getImage());


                //creating dialog
                Stage dialog = new Stage();
                dialog.initStyle(StageStyle.UTILITY);

                //dialog insides

                StackPane stack = new StackPane();
                stack.getChildren().add(image);

                //showing
                Scene scene = new Scene(stack);
                dialog.setScene(scene);
                dialog.show();
            }
        });

        updateData.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                data = FXCollections.observableArrayList(getData(db.<AnalysisEntity>findAll(AnalysisEntity.class)));
                showTable.setItems(data);
            }
        });

        showTable.getColumns().get(showTable.getColumns().size() - 1).setPrefWidth(100);
        data = FXCollections.observableArrayList(getData(db.<AnalysisEntity>findAll(AnalysisEntity.class)));
        showTable.setItems(data);
    }

    private List<AnalyticsTable> getData(List<AnalysisEntity> dataA){
        List<AnalyticsTable> list = new ArrayList<AnalyticsTable>();
        for (AnalysisEntity e : dataA) {

            final ImageView imageview = new ImageView();
            imageview.setFitHeight(100);
            imageview.setFitWidth(100);
            imageview.setImage(new Image(new ByteArrayInputStream(e.getScreenShot())));

            CategoryEntity cat = e.getCategory();
            Manager m = e.getManager();
            Date d = e.getDate();
            list.add(new AnalyticsTable(e.getId(), d == null ? "" : d.toString(), cat == null ? "" : cat.getName(),
                    e.getFullPrice(), m == null ? "" : m.getFullName(), imageview));

        }

        return list;
    }

    private void print(final ImageView imageView) {
        PrinterJob printJob = PrinterJob.getPrinterJob();
        PageFormat pf = printJob.defaultPage();

        Paper paper = pf.getPaper();

        pf.setOrientation(PageFormat.LANDSCAPE);
        //todo find durable solution for a4
        paper.setImageableArea(0, 0, 1200, 700);
        pf.setPaper(paper);
        PageFormat validatePage = printJob.validatePage(pf);


        printJob.setPrintable(new Printable() {
            public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
                if (pageIndex != 0) {
                    return NO_SUCH_PAGE;
                }

                java.awt.Image image = SwingFXUtils.fromFXImage(imageView.getImage(), new BufferedImage(10, 10, BufferedImage.TYPE_BYTE_GRAY));

                Graphics2D g2d = (Graphics2D) graphics;
                double width = pageFormat.getImageableWidth();
                double height = pageFormat.getImageableHeight();

                g2d.translate((int) pageFormat.getImageableX(),
                        (int) pageFormat.getImageableY());
                java.awt.Image scaled = null;
                if (width > height) {
                    scaled = image.getScaledInstance((int)Math.round(width), -1, java.awt.Image.SCALE_SMOOTH);
                } else {
                    scaled = image.getScaledInstance(-1, (int)Math.round(height), java.awt.Image.SCALE_SMOOTH);
                }

                graphics.drawImage(scaled, 0, 0, null);
                return PAGE_EXISTS;
            }
        }, validatePage);
        if (printJob.printDialog()) {
            try {
                printJob.print();
            } catch (PrinterException exc) {
                System.out.println(exc);
            }
        }
    }

    protected static double fromCMToPPI(double cm) {
        return toPPI(cm * 0.393700787);
    }

    protected static double toPPI(double inch) {
        return inch * 72d;
    }

}
