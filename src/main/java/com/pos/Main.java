package com.pos;

import com.pos.components.ProductsPanel;
import com.pos.store.State;

import com.pos.components.PanelGrid;
import com.pos.components.SalePanel;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.control.TabPane.TabClosingPolicy;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class Main extends Application {

    State store = new State();
    List<KeyCode> keyList = new ArrayList<KeyCode>();

    @Override
    public void start(Stage stage) {

        // Contenedor de productos
        SalePanel salePanel = new SalePanel(store);

        //Panel Abarrotes
        store.setType("abarrotes");
        PanelGrid panelAbarrotes = new PanelGrid(store);

        // Panel Bebidas
        store.setType("bebidas");
        PanelGrid panelBebidas = new PanelGrid(store);

        // Panel Botanas
        store.setType("botanas");
        PanelGrid panelBotanas = new PanelGrid(store);

        store.setType("default");

        // +++++++++++++++++++++++++++++ TABS +++++++++++++++++++++++++++++
        TabPane tabPane = new TabPane();
        tabPane.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);

        Tab tab1 = new Tab("Abarrotes", panelAbarrotes);
        Tab tab2 = new Tab("Bebidas", panelBebidas);
        Tab tab3 = new Tab("Botanas", panelBotanas);

        tabPane.getTabs().add(tab1);
        tabPane.getTabs().add(tab2);
        tabPane.getTabs().add(tab3);

        VBox tabsContainer = new VBox(tabPane);
        tabsContainer.setVgrow(tabPane, Priority.ALWAYS);
        tabsContainer.getStyleClass().add("tabs-container");
        // ---------------------------- TABS ----------------------------

        // +++++++++++++++++++++++++++++ SETTING MAIN PANE +++++++++++++++++++++++++++++
            GridPane mainContainer = new GridPane();
            mainContainer.getStyleClass().add("main");
            mainContainer.setGridLinesVisible(true);

            ColumnConstraints tabsColumn = new ColumnConstraints();
            tabsColumn.setPercentWidth(70);

            RowConstraints mainRow = new RowConstraints();
            mainRow.setPercentHeight(100);

            ColumnConstraints payColumn = new ColumnConstraints();
            payColumn.setPercentWidth(30);

            mainContainer.getColumnConstraints().addAll(tabsColumn, payColumn);
            mainContainer.getRowConstraints().addAll(mainRow);

            mainContainer.add(tabsContainer, 0, 0);
            mainContainer.add(salePanel, 1, 0);
        // ---------------------------- SETTING MAIN PANE ----------------------------

        // +++++++++++++++++++++++++++++ LAST SETTINGS +++++++++++++++++++++++++++++
            Scene scene = new Scene(mainContainer, 1080, 680);
            scene.getStylesheets().add(getClass().getResource("/css/styles.css").toExternalForm());

            scene.setOnKeyPressed((event) -> {

                keyList.add(event.getCode());

                if(keyList.size() < 2) {
                    return;
                }
                // Si se preciona control + c se abre la ventana de productos
                if((keyList.get(0) == KeyCode.CONTROL) && (keyList.get(1) == KeyCode.C)) {
                    keyList.clear();
                    stage.close();
                    Platform.runLater( () -> new ProductsPanel().start(new Stage()) );
                }
            });

            scene.setOnKeyReleased((event) -> {
                keyList.remove(event.getCode());
            });

            stage.setScene(scene);
            stage.setResizable(false);

            stage.show();
        // ---------------------------- LAST SETTINGS ----------------------------
    }

    public static void main(String[] args) {
        launch(args);
    }
}