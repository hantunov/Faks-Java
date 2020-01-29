package hr.java.vjezbe.javafx;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import hr.java.vjezbe.entitet.Senzor;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.util.Callback;

public class SenzoriController {
	
	private List<Senzor> listaSenzora;
	
	@FXML
	private TextField senzoriFilterTextField;
	@FXML
	private TableView<Senzor> senzoriTableView;
	@FXML
	private TableColumn<Senzor, String> mjernaJedinicaColumn;
	@FXML
	private TableColumn<Senzor, String> preciznostColumn;
	
	@FXML
	public void initialize() {
		mjernaJedinicaColumn.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Senzor, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Senzor, String> param) {
						return new ReadOnlyObjectWrapper<String>(param.getValue().getMjernaJedinica().toString());
					}
				});
		
		preciznostColumn.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Senzor, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Senzor, String> param) {
						return new ReadOnlyObjectWrapper<String>(param.getValue().getPreciznostSenzora() + "");
					}
				});
		
		listaSenzora = Main.dohvatiSenzore();
	}

	public void prikaziSenzore() {
		List<Senzor> filtriraniSenzori = new ArrayList<Senzor>();
		if (senzoriFilterTextField.getText().isEmpty() == false) {
			filtriraniSenzori = listaSenzora.stream().filter(m -> m.getMjernaJedinica().contains(senzoriFilterTextField.getText()))
					.collect(Collectors.toList());
		} else {
			filtriraniSenzori = listaSenzora;
		}
		ObservableList<Senzor> listaSenzora = FXCollections.observableArrayList(filtriraniSenzori);
		senzoriTableView.setItems(listaSenzora);
	}
	
}
