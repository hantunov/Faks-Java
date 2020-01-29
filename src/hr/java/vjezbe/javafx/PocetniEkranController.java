package hr.java.vjezbe.javafx;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import hr.java.vjezbe.entitet.Mjesto;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.util.Callback;

public class PocetniEkranController {

	private List<Mjesto> listaMjesta;
	
	@FXML
	private TextField mjestaFilterTextField;
	@FXML
	private TableView<Mjesto> mjestaTableView;
	@FXML
	private TableColumn<Mjesto, String> nazivColumn;
	@FXML
	private TableColumn<Mjesto, String> tipColumn;
	@FXML
	private TableColumn<Mjesto, String> zupanijaColumn;
	
	@FXML
	public void initialize() {
		nazivColumn.setCellValueFactory(new PropertyValueFactory<Mjesto, String>("naziv"));
		tipColumn.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Mjesto, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Mjesto, String> param) {
						return new ReadOnlyObjectWrapper<String>(param.getValue().getVrstaMjesta().toString());
					}
				});
		zupanijaColumn.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Mjesto, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Mjesto, String> param) {
						return new ReadOnlyObjectWrapper<String>(param.getValue().getZupanija().getNaziv());
					}
				});
		
		listaMjesta = Main.dohvatiMjesta();
	}

	public void prikaziMjesta() {
		List<Mjesto> filtriranaMjesta = new ArrayList<Mjesto>();
		if (mjestaFilterTextField.getText().isEmpty() == false) {
			filtriranaMjesta = listaMjesta.stream().filter(m -> m.getNaziv().contains(mjestaFilterTextField.getText()))
					.collect(Collectors.toList());
		} else {
			filtriranaMjesta = listaMjesta;
		}
		ObservableList<Mjesto> listaMjesta = FXCollections.observableArrayList(filtriranaMjesta);
		mjestaTableView.setItems(listaMjesta);
	}
	
	public void prikaziEkranMjesta() {
		try {
			BorderPane mjestaPane = FXMLLoader.load(Main.class.getResource("Mjesta.fxml"));
			Main.setCenterPane(mjestaPane);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void prikaziEkranZupanije() {
		try {
			BorderPane zupanijePane = FXMLLoader.load(Main.class.getResource("Zupanije.fxml"));
			Main.setCenterPane(zupanijePane);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void prikaziEkranDrzave() {
		try {
			BorderPane drzavePane = FXMLLoader.load(Main.class.getResource("Drzave.fxml"));
			Main.setCenterPane(drzavePane);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void prikaziEkranMjernePostaje() {
		try {
			BorderPane postajePane = FXMLLoader.load(Main.class.getResource("Postaje.fxml"));
			Main.setCenterPane(postajePane);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void prikaziEkranSenzori() {
		try {
			BorderPane senzoriPane = FXMLLoader.load(Main.class.getResource("Senzori.fxml"));
			Main.setCenterPane(senzoriPane);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}