package hr.java.vjezbe.javafx;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import hr.java.vjezbe.baza.podataka.BazaPodataka;
import hr.java.vjezbe.entitet.MjernaPostaja;
import hr.java.vjezbe.glavna.Glavna;
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

public class PostajeController {
	
	private static List<MjernaPostaja> listaPostaja;
	
	@FXML
	private TextField postajeFilterTextField;
	@FXML
	private TableView<MjernaPostaja> postajeTableView;
	@FXML
	private TableColumn<MjernaPostaja, String> nazivColumn;
	@FXML
	private TableColumn<MjernaPostaja, String> mjestoColumn;
	@FXML
	private TableColumn<MjernaPostaja, String> geografskaTockaColumn;
	
	@FXML
	public void initialize() {
		nazivColumn.setCellValueFactory(new PropertyValueFactory<MjernaPostaja, String>("naziv"));
		mjestoColumn.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<MjernaPostaja, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<MjernaPostaja, String> param) {
						return new ReadOnlyObjectWrapper<String>(param.getValue().getMjesto().getNaziv().toString());
					}
				});
		geografskaTockaColumn.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<MjernaPostaja, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<MjernaPostaja, String> param) {
						return new ReadOnlyObjectWrapper<String>(param.getValue().getGeografskaTocka().getX().toString() + " " + param.getValue().getGeografskaTocka().getY().toString());
					}
				});
		
		try {
			listaPostaja = BazaPodataka.dohvatiMjernePostaje();
		} catch (SQLException | IOException e) {
			e.printStackTrace();
		}
		
		postajeTableView.setItems(Main.observableListaPostaja);
	}

	public void prikaziPostaje() {
		List<MjernaPostaja> filtriranePostaje = new ArrayList<MjernaPostaja>();
		if (postajeFilterTextField.getText().isEmpty() == false) {
			filtriranePostaje = listaPostaja.stream().filter(m -> m.getNaziv().contains(postajeFilterTextField.getText()))
					.collect(Collectors.toList());
		} else {
			filtriranePostaje = listaPostaja;
		}
		ObservableList<MjernaPostaja> listaPostaja = FXCollections.observableArrayList(filtriranePostaje);
		postajeTableView.setItems(listaPostaja);
	}

	public static void dodajNovuMjernuPostaju(int noviId, MjernaPostaja novaMjernaPostaja) {

		listaPostaja.add(novaMjernaPostaja);
		try {
			BazaPodataka.spremiMjernuPostaju(novaMjernaPostaja);
		} catch (SQLException | IOException e) {
			Glavna.logger.error("Neuspješan upis mjerne postaje u bazu!");
			e.printStackTrace();
		}
		Main.observableListaPostaja = FXCollections.observableArrayList(listaPostaja);
		Main.prikaziEkranMjernePostaje();		
		
	}
	
}
