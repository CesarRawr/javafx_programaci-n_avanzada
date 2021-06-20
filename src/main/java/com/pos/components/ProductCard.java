package com.pos.components;

import com.pos.models.ProductoEntity;
import com.pos.store.State;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class ProductCard extends VBox {

	VBox bottomPanel = new VBox();

	Label nameLabel;
	Label total;
	Label stock;
	Pane imagen;

	Button btn;
	Spinner<Integer> spinner;

	ProductoEntity producto;

	// Si existe stock es true. By default true
	boolean status = true;

	public ProductCard(State store, ProductoEntity producto) {

		this.producto = producto;

		// Label del nombre de producto
		nameLabel = new Label(producto.getNombre());
		nameLabel.getStyleClass().add("font-16");

		// Si las existencias son 0, simplemente mandamos un mensaje de que no hay existencias y salimos
		if(producto.getStock()-1 < 0) {

			status = false;

			getStyleClass().add("item-card");
			setAlignment(Pos.CENTER);
			setPadding(new Insets(10, 10, 15, 10));
			setSpacing(15);

			getChildren().add(nameLabel);
			getChildren().add(new Label("Existencias agotadas"));
			return;
		}

		// Imagen pendiente
		imagen = new Pane();
		imagen.getStyleClass().add("imagen");

		// Label para el stock de productos
		stock = new Label(Integer.toString(producto.getStock()-1));
		stock.getStyleClass().add("font-16");

		// Label para el costo total
		total = new Label("$" + Integer.toString(producto.getPrecio()));
		total.getStyleClass().add("font-14");

		// Boton para agregar al sale panel
	    btn = new Button("Agregar");
		EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e)
			{
				int cant = spinner.getValue();
				int totalToLine = Integer.parseInt(total.getText().replace("$", ""));

				store.setLine(cant, totalToLine, producto);
				store.pushLine();

				stock.setText(Integer.toString(producto.getStock()-spinner.getValue()-1));
				producto.setStock(producto.getStock()-spinner.getValue());
				spinner.getValueFactory().setValue(1);

				if(producto.getStock() == 0) {

					status = false;
					getChildren().clear();
					getChildren().add(nameLabel);
					getChildren().add(new Label("Existencias Agotadas"));
				}
			}
		};
		// Setting event
		btn.setOnAction(event);

		int maxValue = producto.getStock();
		spinner = new Spinner<Integer>(1, 100, 1);
		// Spinner de cantidad de la tarjeta
	    spinner.getStyleClass().add(Spinner.STYLE_CLASS_SPLIT_ARROWS_HORIZONTAL);
	    spinner.valueProperty().addListener((obs, oldValue, newValue) -> {
			if (producto.getStock()-newValue >= 0) {
				stock.setText(Integer.toString(producto.getStock() - newValue));
				total.setText("$" + Integer.toString(producto.getPrecio()*newValue));
			}
			else {
				spinner.decrement(1);
			}
		});

	    bottomPanel.getChildren().add(stock);
	   	bottomPanel.getChildren().add(spinner);
	    bottomPanel.getChildren().add(total);
	    bottomPanel.getChildren().add(btn);

	    bottomPanel.setAlignment(Pos.CENTER);
	    bottomPanel.setPadding(new Insets(5, 0, 5, 0));
	    bottomPanel.setSpacing(8);

	    getStyleClass().add("item-card");
	    setAlignment(Pos.CENTER);
	    setPadding(new Insets(10, 10, 15, 10));
	    setSpacing(15);

	    getChildren().add(nameLabel);
	    getChildren().add(imagen);
	    getChildren().add(bottomPanel);
	}

	// Name
	public Label getNameLabel() {
		return nameLabel;
	}
	public void setNameLabel(Label nameLabel) {
		this.nameLabel = nameLabel;
	}

	// Total
	public Label getTotal() {
		return total;
	}
	public void setTotal(Label total) {
		this.total = total;
	}

	// Bottom panel
	public VBox getBottomPanel() {
		return bottomPanel;
	}
	public void setBottomPanel(VBox bottomPanel) {
		this.bottomPanel = bottomPanel;
	}

	// stock
	public Label getStock() {
		return stock;
	}
	public void setStock(Label stock) {
		this.stock = stock;
	}

	// Image
	public Pane getImagen() {
		return imagen;
	}
	public void setImagen(Pane imagen) {
		this.imagen = imagen;
	}

	// boton
	public Button getBtn() {
		return btn;
	}
	public void setBtn(Button btn) {
		this.btn = btn;
	}

	// Spinner
	public Spinner<Integer> getSpinner() {
		return spinner;
	}
	public void setSpinner(Spinner<Integer> spinner) {
		this.spinner = spinner;
	}

	// product
	public ProductoEntity getProduct() {
		return this.producto;
	}

	// Status
	public boolean getStatus() {
		return this.status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}

	// Setting components
	public void setAllComponents() {

		// Bottom panel setting
		bottomPanel.getChildren().clear();
		bottomPanel.getChildren().add(stock);
		bottomPanel.getChildren().add(spinner);
		bottomPanel.getChildren().add(total);
		bottomPanel.getChildren().add(btn);

		// Card setting
		getChildren().clear();
		getChildren().add(nameLabel);
		getChildren().add(imagen);
		getChildren().add(bottomPanel);
	}
}