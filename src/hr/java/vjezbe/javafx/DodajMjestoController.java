package hr.java.vjezbe.javafx;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import hr.java.vjezbe.entitet.Mjesto;
import hr.java.vjezbe.entitet.VrstaMjesta;
import hr.java.vjezbe.entitet.Zupanija;
import hr.java.vjezbe.glavna.Glavna;
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
		File mjestaFile = new File("dat/mjesta.txt");
		int noviId = getZadnjiId() + 1;
		Mjesto mjesto = new Mjesto(noviId, naziv, zupanija, vrstaMjesta);		
		
		try (FileWriter writer = new FileWriter(mjestaFile, true);) {
			writer.write(noviId + "\n");
			writer.write(naziv + "\n");
			writer.write(vrstaMjesta.toString() + "\n");
			writer.write(zupanija.getId() + "\n");
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Uspješno spremanje mjesta!");
			alert.setHeaderText("Uspješno spremanje mjesta!");
			alert.setContentText("Uneseni podaci za mjesto su uspješno spremljeni.");
			Stage stage = (Stage) spremiButton.getScene().getWindow();
			stage.close();
			MjestaController.dodajNovoMjesto(noviId, mjesto);
		} catch (IOException e) {
			Glavna.logger.error("Pogreška kod spremanja podataka!", e);
			e.printStackTrace();
		}
	}

	private int getZadnjiId() {
		
		return Main.observableListaMjesta.size();
	}
}