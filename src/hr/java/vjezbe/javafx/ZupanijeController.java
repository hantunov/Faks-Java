package hr.java.vjezbe.javafx;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import hr.java.vjezbe.baza.podataka.BazaPodataka;
import hr.java.vjezbe.entitet.Zupanija;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

public class ZupanijeController {

	private static List<Zupanija> listaZupanija;
	
	@FXML
	private TextField zupanijeFilterTextField;
	@FXML
	private TableView<Zupanija> zupanijeTableView;
	@FXML
	private TableColumn<Zupanija, String> nazivColumn;
	@FXML
	private TableColumn<Zupanija, String> drzavaColumn;
	
	@FXML
	public void initialize() {
		nazivColumn.setCellValueFactory(new PropertyValueFactory<Zupanija, String>("naziv"));
		drzavaColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Zupanija, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Zupanija, String> param) {
						return new ReadOnlyObjectWrapper<String>(param.getValue().getDrzava().getNaziv().toString());
					}
				});
		
		try {
			listaZupanija = BazaPodataka.dohvatiZupanije();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		zupanijeTableView.setItems(Main.observableListaZupanija);
	}

	public void prikaziZupanije() {
		List<Zupanija> filtriraneZupanije = new ArrayList<Zupanija>();
		if (zupanijeFilterTextField.getText().isEmpty() == false) {
			filtriraneZupanije = listaZupanija.stream().filter(m -> m.getNaziv().contains(zupanijeFilterTextField.getText()))
					.collect(Collectors.toList());
		} else {
			filtriraneZupanije = listaZupanija;
		}
		ObservableList<Zupanija> listaZupanija = FXCollections.observableArrayList(filtriraneZupanije);
		zupanijeTableView.setItems(listaZupanija);
	}

	public static void dodajNovuZupaniju(int noviId, Zupanija novaZupanija) {
		
		listaZupanija.add(novaZupanija);
		Main.observableListaZupanija = FXCollections.observableArrayList(listaZupanija);
		Main.prikaziEkranZupanije();	
		
	}
}
