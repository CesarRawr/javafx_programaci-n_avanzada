package com.pos.components;

import com.pos.store.State;

import com.pos.models.InvoiceEntity;
import com.pos.models.ProductoEntity;
import com.pos.utils.Misc;

import javafx.geometry.Insets;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

import java.util.List;

public class PanelGrid extends ScrollPane {

    GridPane panelGrid = new GridPane();

    public PanelGrid(State store) {

        panelGrid.getStyleClass().add("grid");
        panelGrid.setVgap(10);
        panelGrid.setHgap(10);
        panelGrid.setPadding(new Insets(10, 10, 10, 10));

        ColumnConstraints quarterCol = new ColumnConstraints();
        quarterCol.setPercentWidth(25);

        RowConstraints row = new RowConstraints();

        panelGrid.getColumnConstraints().addAll(quarterCol, quarterCol, quarterCol, quarterCol);
        panelGrid.getRowConstraints().addAll(row);

        int column = 0;
        int actualRow = 0;
        int lastRow = 0;

        for(ProductoEntity obj: store.getProductList()) {

            ProductCard simpleCard = new ProductCard(store, obj);
            panelGrid.add(simpleCard, column, actualRow);
            store.pushAbarroteCard(simpleCard);
            column = Misc.getColumn(column);
            actualRow = Misc.getRow(column, actualRow);
            if (actualRow > lastRow) {
                panelGrid.getRowConstraints().add(row);
                lastRow = actualRow;
            }
        }

        this.getStyleClass().add("scroll");
        this.setFitToHeight(true);
        this.setFitToWidth(true);

        this.setContent(panelGrid);
    }
}
