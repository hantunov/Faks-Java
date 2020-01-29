package hr.java.vjezbe.javafx;

import hr.java.vjezbe.entitet.Mjesto;
import hr.java.vjezbe.entitet.VrstaMjesta;
import hr.java.vjezbe.entitet.Zupanija;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class DodajMjestoController {

	@FXML
	private TextField nazivTextfield;
	@FXML
	private ComboBox<Zupanija> zupanijaCombobox;
	@FXML
	private ComboBox<VrstaMjesta> vrstaCombobox;
	@FXML
	private Button spremiButton;
	@FXML
	public void initialize(){
		
		zupanijaCombobox.setItems(Main.observableListaZupanija);
		zupanijaCombobox.getSelectionModel().selectFirst();
		
		for (VrstaMjesta vrstaMjesta : VrstaMjesta.values()) {
			vrstaCombobox.getItems().add(vrstaMjesta);
		}
		vrstaCombobox.getSelectionModel().selectFirst();
		
	}
	
	public void spremiPodatke() {
		
		String naziv = nazivTextfield.getText();
		Zupanija zupanija = zupanijaCombobox.getValue();
		VrstaMjesta vrstaMjesta = vrstaCombobox.getValue();
		int noviId = getZadnjiId() + 1;
		Mjesto mjesto = new Mjesto(noviId, naziv, zupanija, vrstaMjesta);		
		
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Uspješno spremanje mjesta!");
		alert.setHeaderText("Uspješno spremanje mjesta!");
		alert.setContentText("Uneseni podaci za mjesto su uspješno spremljeni.");
		Stage stage = (Stage) spremiButton.getScene().getWindow();
		stage.close();
		MjestaController.dodajNovoMjesto(noviId, mjesto);
	}

	private int getZadnjiId() {
		
		return Main.observableListaMjesta.size();
	}
}