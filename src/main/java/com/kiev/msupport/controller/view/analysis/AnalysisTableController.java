package com.kiev.msupport.controller.view.analysis;

import com.kiev.msupport.Main;
import com.kiev.msupport.controller.db.MaterialsMngrBean;
import com.kiev.msupport.controller.utils.ComboItem;
import com.kiev.msupport.controller.utils.EditingCell;
import com.kiev.msupport.controller.utils.Images;
import com.kiev.msupport.controller.view.analysis.contragent.ContragentController;
import com.kiev.msupport.controller.view.analysis.contragent.Prices;
import com.kiev.msupport.domain.*;
import javafx.collections.FXCollections;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.util.Callback;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

import javax.imageio.ImageIO;
import java.io.*;
import java.math.BigDecimal;
import java.net.URL;
import java.util.*;

public class AnalysisTableController implements Initializable {
    @FXML
    private Button makeScreenshot;
    @FXML
    private Button addContragent;
    @FXML
    private ComboBox<ComboItem> managerCombo;

    @FXML
    private GridPane grid;
    @FXML
    private SplitPane snapshotArea;
    @FXML
    private TextField categoryName;
    @FXML
    private Label fullPrice;
    @FXML
    private Label id;
    @FXML
    private Label date;
    @FXML
    private TableView<ProductTable> products;
    @FXML
    private TableColumn<ProductTable, String> name;
    @FXML
    private TableColumn<ProductTable, String> unit;
    @FXML
    private TableColumn<ProductTable, String> amount;
    @FXML
    private TableColumn<ProductTable, String> mprice;

    //single product
    @FXML
    private Button addProduct;
    @FXML
    private TextField prodName;
    @FXML
    private ComboBox<ComboItem> prodUnit;
    @FXML
    private TextField prodAmount;
    @FXML
    private TextField prodPrice;
    @FXML
    private Button openMultipleButton;

    private AnalysisEntity entity;
    private MaterialsMngrBean db = Main.db;
    private ArrayList<ProductTable> productData;
    private Map<Integer, ContragentController> contragentMap = new HashMap<Integer, ContragentController>();
    private final FileChooser fileChooser = new FileChooser();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        List<Manager> managers = db.findAll(Manager.class);
        List<ComboItem> comboItems = new ArrayList<ComboItem>();
        for(Manager e:managers){
            comboItems.add(new ComboItem(e.getId(), e.getFullName()));
        }
        managerCombo.setItems(FXCollections.observableArrayList(comboItems));


        //importing the file

        openMultipleButton.setOnAction(
                new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(final ActionEvent e) {
                        List<File> list =
                                fileChooser.showOpenMultipleDialog(Main.mainStage);
                        if (list != null) {
                            for (File file : list) {
                                importFile(file);
                            }
                        }
                    }
                });



        List<UnitEntity> units = db.findAll(UnitEntity.class);
        for(UnitEntity u:units){
            prodUnit.getItems().add(new ComboItem(u.getId(), u.getName()));
        }

        //main menu
        makeScreenshot.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                try {
                    //todo show error message
//                    if ("".equals(categoryName.getText())) return;
                    if ("".equals(fullPrice.getText())) fullPrice.setText("0");

                    Manager manager = null;
                    if (managerCombo.getValue()!=null) {
                        manager = db.getEntity(Manager.class, managerCombo.getValue().getId());
                    }

                    CategoryEntity category = db.categoryIfNotExist(categoryName.getText());
                    entity = new AnalysisEntity(new Date(), manager,
                            null, category, fullPrice.getText());
                    entity = db.updateEntity(entity);
                    id.setText(Long.toString(entity.getId()));
                    date.setText(entity.getDate().toString());
                    File tempFile = File.createTempFile("analysis", ".png");
                    WritableImage image = snapshotArea.snapshot(null, null);
                    ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", tempFile);
                    entity.setScreenShot(Images.ImageToByte(tempFile));
                    db.updateEntity(entity);
                    tempFile.delete();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        //product data table
        name.setCellValueFactory(new PropertyValueFactory<ProductTable, String>("name"));
        unit.setCellValueFactory(new PropertyValueFactory<ProductTable, String>("units"));
        amount.setCellValueFactory(new PropertyValueFactory<ProductTable, String>("amount"));
        mprice.setCellValueFactory(new PropertyValueFactory<ProductTable, String>("minPrice"));

        Callback<TableColumn<ProductTable, String>, TableCell<ProductTable, String>> editableFactory =
                new Callback<TableColumn<ProductTable, String>, TableCell<ProductTable, String>>() {
            @Override
            public TableCell<ProductTable, String> call(TableColumn p) {
                return new EditingCell<ProductTable>();
            }
        };

        products.setEditable(true);
        int count = 0;
        for (TableColumn col : products.getColumns()) {
            if(count !=products.getColumns().size() - 1)
            col.setCellFactory(editableFactory);
            count++;
        }

        productData = new ArrayList<ProductTable>();
        products.setItems(FXCollections.observableArrayList(productData));

        //contragents
        addContragent.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                int index = contragentMap.entrySet().size()+1;
                buildNewContagentColumn(index);
            }
        });

        //adding products
        addProduct.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                ProductTable newProduct = new ProductTable(prodName.getText(), prodUnit.getValue().getName(), prodAmount.getText());
                products.getItems().add(newProduct);
                int row = products.getItems().size()-1;
                List<ContragentController> controllers = new ArrayList<ContragentController>();
                for( Map.Entry<Integer, ContragentController> cont : contragentMap.entrySet()){
                    ContragentController c = cont.getValue();
                    c.price.getItems().add(new Prices("0", "0", products, row));
                    controllers.add(c);
                }
                products.getItems().get(row).setClist(controllers);
            }
        });

        amount.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<ProductTable, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<ProductTable, String> t) {
                BigDecimal v = new BigDecimal(t.getNewValue());
                int row = t.getRowValue().getRow();
                Prices pr;
                for(ContragentController c:t.getRowValue().getClist()){
                    pr = c.price.getItems().get(row);
                    BigDecimal pforOne = new BigDecimal(pr.getPriceForOne());
                    pr.setPriceForAll(v.multiply(pforOne).toString());

                    BigDecimal sum = new BigDecimal(0);
                    for(Prices p : c.price.getItems()){
                        sum = sum.add(new BigDecimal(p.getPriceForAll()));
                    }

                    c.fullPriceWithTax.setText(sum.toString());
                }
            }
        });
    }


    private void buildNewContagentColumn(int index){
        //update contragent view
        ContragentController controller = new ContragentController(index, this);
        controller.newGridColumn(index, grid);

        //update conragentModel
        contragentMap.put(index, controller);
    }

    public void updateFullPrice(){
        BigDecimal sum = new BigDecimal(0);
        for( Map.Entry<Integer, ContragentController> table : contragentMap.entrySet()){
            ContragentController c = table.getValue();
            sum = sum.add(new BigDecimal(c.selectedSumPrice.getText()));
        }
        fullPrice.setText(sum.toString());
    }

    private void importFile(File file){
        try {
            POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream(file));
            HSSFWorkbook wb = new HSSFWorkbook(fs);
            HSSFSheet sheet = wb.getSheetAt(0);
            HSSFRow row;
            HSSFCell cell;

            cell = sheet.getRow(0).getCell(4);
            System.out.println(cell.getStringCellValue());

        } catch(Exception ioe) {
            ioe.printStackTrace();
        }
    }
}


