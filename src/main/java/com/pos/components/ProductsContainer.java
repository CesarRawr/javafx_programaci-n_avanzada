package com.pos.components;

import com.pos.models.InvoiceEntity;
import com.pos.models.LineEntity;
import com.pos.store.State;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;

import java.util.List;

// Esta clase maneja la lista de productos que se van a vender
public class ProductsContainer extends ListView<IProduct> {

	public ProductsContainer() {
		this.getStyleClass().add("red");
	}

	public void addProduct(State store) {

		// Se crea una lista nueva
		List<LineEntity> currentList = store.getILines();

		// Obtenemos el nombre del producto en la linea a settear
		String nombre = store.getCurrentLine().getProduct().getNombre();

		LineEntity result = null;
        // Buscando si el objeto se encuentra en la lista
		for (LineEntity currentLine: currentList) {
			if(currentLine.getProduct().getNombre().equals(nombre)) {
				result = currentLine;
			}
		}

		// Si no se encuentra el  objeto
		if(result == null) {
			// Agregar nueva linea a la lista actual
			currentList.add(store.getCurrentLine());
			// Setear lista actual a invoice
			store.setILines(currentList);
			// Obtener nueva lista para el ListView<Iproduct>
			ObservableList<IProduct> ol = getIProductList(store);
			// Eliminar lista de contenedor
			this.getItems().clear();
			// Setting new items
			this.setItems(ol);
		}
		else {
			int newCant = result.getCant()+store.getCurrentLine().getCant();
			// Setting cant
			currentList.get(currentList.indexOf(result)).setCant(newCant);
			// Setting total
			currentList.get(currentList.indexOf(result)).setTotal(newCant*store.getCurrentLine().getProduct().getPrecio());
			// setting new list
			store.setILines(currentList);
			// getting new iproductlist
			ObservableList<IProduct> ol = getIProductList(store);
			// Eliminar lista de contenedor
			this.getItems().clear();
			// Setting new items
			this.setItems(ol);
		}
	}

	// Crear lista de IProduct
	public ObservableList<IProduct> getIProductList(State store) {
		ObservableList<IProduct> newList = FXCollections.observableArrayList();

		for (LineEntity obj: store.getILines()) {
			IProduct newLine = new IProduct(obj, store, this);
			newList.add(newLine);
		}

		return newList;
	}
}