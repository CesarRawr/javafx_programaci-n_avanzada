package com.pos.components;

import com.pos.controllers.Productos;
import com.pos.models.InvoiceEntity;
import com.pos.models.LineEntity;
import com.pos.models.ProductoEntity;
import com.pos.store.State;
import javafx.beans.binding.Bindings;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SalePanel extends GridPane {

	public SalePanel(State store) {

        this.getStyleClass().add("pay-info");

		// Creating Rows and coulumn
		RowConstraints row25 = new RowConstraints();
		row25.setPercentHeight(10);

		RowConstraints row50 = new RowConstraints();
		row50.setPercentHeight(80);

		ColumnConstraints col100 = new ColumnConstraints();
		col100.setPercentWidth(100);

		this.getRowConstraints().addAll(row25, row50, row25);
		this.getColumnConstraints().addAll(col100);

		// Title label
        Label title = new Label("Total");
        title.getStyleClass().add("font-20");

        // Header
        VBox header = new VBox();
		header.getStyleClass().add("sale-header");
		header.getChildren().add(title);
	    header.setAlignment(Pos.CENTER);

	    // Sale button
		Button saleBtn = new Button("Vender");
		saleBtn.setMaxSize(250, 300);

		saleBtn.setOnAction(e -> {

			List<ProductoEntity> lista = new ArrayList<ProductoEntity>();

			// Seteando invoice asociado a cada linea
			for(int i = 0; i < store.getILines().size(); i++) {
				store.getILines().get(i).setInvoice(store.getInvoice());
			}

			// Seteando date a el invoice
			Date date = new Date();
			Instant instant = date.toInstant();
			LocalDate localDate = instant.atZone(ZoneId.systemDefault()).toLocalDate();

			store.getInvoice().setHora(localDate.toString());

			// Insertando invoice para completar la venta
			store.getProductosController().insertInvoice(store.getInvoice());

			// Obteniendo productos actuales en venta
			for(ProductCard c: store.getAbarrotesCards()) {
				lista.add(c.getProduct());
			}

			// Refrescando el stock de los productos en la bd
			store.getProductosController().refreshProducts(lista);

			// Eliminando residuos
			store.getProductsContainer().getItems().clear();
			store.setInvoice(new InvoiceEntity());
		});

		VBox btnPanel = new VBox();
		btnPanel.getStyleClass().add("sale-header");
		btnPanel.getChildren().add(saleBtn);
		btnPanel.setAlignment(Pos.CENTER);

		this.add(header, 0, 0);
		this.add(store.getProductsContainer(), 0, 1);
		this.add(btnPanel, 0, 2);
	}
}