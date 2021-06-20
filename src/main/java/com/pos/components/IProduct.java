package com.pos.components;

import com.pos.models.LineEntity;
import com.pos.store.State;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Spinner;
import javafx.scene.layout.HBox;

// Esta representa a cada producto que se va a llevar
public class IProduct extends HBox {

	Label cant, nombre, total;
	Button deleteBtn;

	ProductCard productCard;

	public IProduct(LineEntity line, State store, ListView<IProduct> container) {

		// Enlazando IProduct con la tarjeta del grid
	    for(ProductCard itemCard: store.getAbarrotesCards()) {
	    	if(itemCard.getProduct().equals(line.getProduct())) {
	    		this.productCard = itemCard;
	    	}
        }

		// Setting basic data
		cant = new Label(Integer.toString(line.getCant()));
		nombre = new Label(line.getProduct().getNombre());
		total = new Label("$" + Integer.toString(line.getTotal()));

		// Button delete
		deleteBtn = new Button("D");
		IProduct thisProduct = this;

		// Button event
		EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e)
			{
				if(!productCard.getStatus()) {

					productCard.getProduct().setStock(line.getCant());

					// Setting new stock and total to labels
					productCard.getStock().setText(Integer.toString(line.getCant()-1));
					productCard.getTotal().setText("$" + Integer.toString(productCard.getProduct().getPrecio()));

					// Re setting components
					productCard.setAllComponents();

					// Removing line
					store.getILines().remove(line);
					container.getItems().remove(thisProduct);
					productCard.setStatus(true);
					return;
				}

				// Setting new stock to product in productCard
				productCard.getProduct().setStock(Integer.parseInt(productCard.getStock().getText()) + line.getCant() + 1);

				// Setting new stock and total to labels
				productCard.getStock().setText(Integer.toString(Integer.parseInt(productCard.getStock().getText()) + line.getCant()));

				store.getILines().remove(line);
				container.getItems().remove(thisProduct);
			}
		};
		// Setting event
		deleteBtn.setOnAction(event);

		this.getChildren().addAll(cant, nombre, total, deleteBtn);
	}
}