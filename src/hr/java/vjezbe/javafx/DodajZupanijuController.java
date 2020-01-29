package hr.java.vjezbe.javafx;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import hr.java.vjezbe.entitet.Drzava;
import hr.java.vjezbe.entitet.Zupanija;
import hr.java.vjezbe.glavna.Glavna;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class DodajZupanijuController {

	@FXML
	private TextField nazivTextfield;
	@FXML
	private ComboBox<Drzava> drzavaCombobox;
	@FXML
	private Button spremiButton;
	@FXML
	public void initialize(){
		
		drzavaCombobox.setItems(Main.observableListaDrzava);
		drzavaCombobox.getSelectionModel().selectFirst();
		
	}
	
	public void spremiPodatke() {
		
		String naziv = nazivTextfield.getText();
		Drzava drzava = drzavaCombobox.getValue();
		File zupanijeFile = new File("dat/zupanije.txt");
		int noviId = getZadnjiId() + 1;
		Zupanija zupanija = new Zupanija(noviId, naziv, drzava);		
		
		try (FileWriter writer = new FileWriter(zupanijeFile, true);) {
			writer.write(noviId + "\n");
			writer.write(naziv + "\n");
			writer.write(drzava.getId() + "\n");
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Uspješno spremanje županije!");
			alert.setHeaderText("Uspješno spremanje županije!");
			alert.setContentText("Uneseni podaci za županiju su uspješno spremljeni.");
			Stage stage = (Stage) spremiButton.getScene().getWindow();
			stage.close();
			ZupanijeController.dodajNovuZupaniju(noviId, zupanija);
		} catch (IOException e) {
			Glavna.logger.error("Pogreška kod spremanja podataka!", e);
			e.printStackTrace();
		}
	}

	private int getZadnjiId() {
		
		return Main.observableListaZupanija.size();
	}
	
}
