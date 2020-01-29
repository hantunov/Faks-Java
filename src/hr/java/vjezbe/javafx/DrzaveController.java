package hr.java.vjezbe.javafx;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import hr.java.vjezbe.entitet.Drzava;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

public class DrzaveController {

	private List<Drzava> listaDrzava;
	
	@FXML
	private TextField drzaveFilterTextField;
	@FXML
	private TableView<Drzava> drzaveTableView;
	@FXML
	private TableColumn<Drzava, String> nazivColumn;
	@FXML
	private TableColumn<Drzava, String> povrsinaColumn;
	
	@FXML
	public void initialize() {
		nazivColumn.setCellValueFactory(new PropertyValueFactory<Drzava, String>("naziv"));
		povrsinaColumn.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Drzava, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Drzava, String> param) {
						return new ReadOnlyObjectWrapper<String>(param.getValue().getPovrsina().toString());
					}
				});
		
		listaDrzava = Main.dohvatiDrzave();
	}

	public void prikaziDrzave() {
		List<Drzava> filtriraneDrzave = new ArrayList<Drzava>();
		if (drzaveFilterTextField.getText().isEmpty() == false) {
			filtriraneDrzave = listaDrzava.stream().filter(m -> m.getNaziv().contains(drzaveFilterTextField.getText()))
					.collect(Collectors.toList());
		} else {
			filtriraneDrzave = listaDrzava;
		}
		ObservableList<Drzava> listaDrzava = FXCollections.observableArrayList(filtriraneDrzave);
		drzaveTableView.setItems(listaDrzava);
	}
	
}
