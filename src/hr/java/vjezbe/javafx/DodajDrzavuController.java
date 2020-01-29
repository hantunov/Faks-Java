package hr.java.vjezbe.javafx;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;

import hr.java.vjezbe.entitet.Drzava;
import hr.java.vjezbe.glavna.Glavna;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class DodajDrzavuController {
	
	@FXML
	private TextField nazivTextfield;
	@FXML
	private TextField povrsinaTextField;
	@FXML
	private Button spremiButton;
	@FXML
	public void initialize(){		
		
	}
	
	public void spremiPodatke() {
		
		String naziv = nazivTextfield.getText();
		BigDecimal povrsina = new BigDecimal(povrsinaTextField.getText());
		File drzaveFile = new File("dat/drzave.txt");
		int noviId = getZadnjiId() + 1;
		Drzava drzava = new Drzava(noviId, naziv, povrsina);		
		
		try (FileWriter writer = new FileWriter(drzaveFile, true);) {
			writer.write(noviId + "\n");
			writer.write(naziv + "\n");
			writer.write(povrsina.toString() + "\n");
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Uspješno spremanje mjesta!");
			alert.setHeaderText("Uspješno spremanje mjesta!");
			alert.setContentText("Uneseni podaci za mjesto su uspješno spremljeni.");
			Stage stage = (Stage) spremiButton.getScene().getWindow();
			stage.close();
			DrzaveController.dodajNovuDrzavu(noviId, drzava);
		} catch (IOException e) {
			Glavna.logger.error("Pogreška kod spremanja podataka!", e);
			e.printStackTrace();
		}
	}

	private int getZadnjiId() {
		
		return Main.observableListaDrzava.size();
	}
	
}
