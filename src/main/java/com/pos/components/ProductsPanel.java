package com.pos.components;

import com.pos.Main;
import com.pos.controllers.Productos;
import com.pos.models.ProductoEntity;
import com.pos.store.State;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.util.List;

public class ProductsPanel extends Application {

    private List<ProductoEntity> allProducts = null;
    private Productos controller = new Productos();
    private ObservableList<ProductoEntity> products = null;

    private FormData idTxt = new FormData("ID", false);
    private FormData nameTxt = new FormData("Nombre");
    private FormData precioTxt = new FormData("Precio");
    private FormData stockTxt = new FormData("Stock");
    private FormData typeTxt = new FormData("type");

    private int selectedIndex = -1;
    private Stage stage = null;

    public void start(Stage st){

        this.stage = st;

        allProducts = controller.getAll();
        products = FXCollections.observableArrayList(allProducts);

        stage.setTitle("JavaFX ListView CRUD - ADD UPDATE DELETE CLEAR");

        // Title
        Label nameLabel = new Label("Products");
        nameLabel.setFont(new Font("Arial", 20));

        VBox titleContainer = new VBox();
        titleContainer.getChildren().add(nameLabel);
        titleContainer.setAlignment(Pos.CENTER);

        // Fields container
        HBox fieldsContainer = new HBox();
        fieldsContainer.setSpacing(15);
        fieldsContainer.getChildren().addAll(idTxt, nameTxt, precioTxt, stockTxt, typeTxt);
        fieldsContainer.setAlignment(Pos.CENTER);

        // Something list
        ListView myListView = new ListView();

        myListView.setCellFactory(param -> new ListCell<ProductoEntity>() {
            @Override
            protected void updateItem(ProductoEntity item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || item == null || item.getNombre() == null) {
                    setText(null);
                } else {
                    setText(item.getNombre());
                }
            }
        });

        myListView.setItems(products);

        myListView.setOnMouseClicked(event -> {
            ProductoEntity selectedItem = (ProductoEntity) myListView.getSelectionModel().getSelectedItem();
            selectedIndex = myListView.getSelectionModel().getSelectedIndex();

            // Setting data on fields
            idTxt.setText(Integer.toString(selectedItem.getId()));
            nameTxt.setText(selectedItem.getNombre());
            precioTxt.setText(Integer.toString(selectedItem.getPrecio()));
            stockTxt.setText(Integer.toString(selectedItem.getStock()));
            typeTxt.setText(selectedItem.getType());
        });

        // ------ Crud buttons -----
        // Create something
        Button addButton = new Button("Add");
        addButton.setOnAction((ActionEvent e) -> {

            ProductoEntity productoEntity = new ProductoEntity();
            productoEntity.setNombre(nameTxt.getText());
            productoEntity.setPrecio(Integer.parseInt(precioTxt.getText()));
            productoEntity.setStock(Integer.parseInt(stockTxt.getText()));
            productoEntity.setImagen("undefined");
            productoEntity.setType(typeTxt.getText());

            controller.insertProduct(productoEntity);
            products.add(productoEntity);
            allProducts.add(productoEntity);

            clearAll();
        });

        // Update Something
        Button updateBtn = new Button("Update");
        updateBtn.setOnAction((ActionEvent e) -> {
            Dialog d=new Alert(Alert.AlertType.INFORMATION,String.valueOf(selectedIndex));
            d.show();

            allProducts.get(selectedIndex).setNombre(nameTxt.getText());
            allProducts.get(selectedIndex).setPrecio(Integer.parseInt(precioTxt.getText()));
            allProducts.get(selectedIndex).setStock(Integer.parseInt(stockTxt.getText()));
            allProducts.get(selectedIndex).setType(typeTxt.getText());

            System.out.println(allProducts.get(selectedIndex).toString());

            controller.updateProduct(allProducts.get(selectedIndex));

            System.out.println(allProducts.get(selectedIndex).toString());

            products.remove(selectedIndex);
            products.add(selectedIndex, allProducts.get(selectedIndex));
            clearAll();
        });

        // Delete something
        Button deleteBtn = new Button("Delete");
        deleteBtn.setOnAction((ActionEvent e) -> {
            controller.deleteProduct(allProducts.get(selectedIndex));
            products.remove(selectedIndex);
            clearAll();
        });

        // Butons Container
        HBox btnsContainer = new HBox();
        btnsContainer.getChildren().addAll(addButton, updateBtn, deleteBtn);
        // ----------------------

        // Grid Container
        GridPane panelGrid = new GridPane();

        ColumnConstraints fullCol = new ColumnConstraints();
        fullCol.setPercentWidth(100);

        RowConstraints tenRow = new RowConstraints();
        tenRow.setPercentHeight(10);

        RowConstraints seventRow = new RowConstraints();
        seventRow.setPercentHeight(70);

        // Setting col and rows to container
        panelGrid.getColumnConstraints().addAll(fullCol);
        panelGrid.getRowConstraints().addAll(tenRow, seventRow, tenRow, tenRow);
        btnsContainer.setAlignment(Pos.CENTER);
        btnsContainer.setSpacing(10);

        // Setting components to container
        panelGrid.add(titleContainer, 0, 0);
        panelGrid.add(myListView, 0, 1);
        panelGrid.add(fieldsContainer, 0, 2);
        panelGrid.add(btnsContainer, 0, 3);

        // Adding principal panel
        Scene scene = new Scene(panelGrid, 1080, 680);
        stage.setScene(scene);
        stage.getScene().getWindow().addEventFilter(WindowEvent.WINDOW_CLOSE_REQUEST, this::closeWindowEvent);
        stage.show();
    }

    private void closeWindowEvent(WindowEvent event) {
        stage.close();
        Platform.runLater( () -> new Main().start( new Stage() ) );
    }

    private void clearAll() {
        idTxt.clear();
        nameTxt.clear();
        precioTxt.clear();
        stockTxt.clear();
        typeTxt.clear();
    }

}

class FormData extends VBox {

    TextField input = new TextField();

    public FormData(String label) {
        setAlignment(Pos.CENTER);
        getChildren().add(new Label(label));
        getChildren().add(input);
    }

    public FormData(String label, boolean disabled) {
        input.setEditable(disabled);
        setAlignment(Pos.CENTER);
        getChildren().add(new Label(label));
        getChildren().add(input);
    }

    public void setText(String text) {
        input.setText(text);
    }

    public String getText() {
        return input.getText();
    }

    public void clear() {
        this.input.setText("");
    }
}
