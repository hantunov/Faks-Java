package hr.java.vjezbe.javafx;

import java.math.BigDecimal;

import hr.java.vjezbe.entitet.Drzava;
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
		int noviId = getZadnjiId() + 1;
		Drzava drzava = new Drzava(noviId, naziv, povrsina);		
		
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Uspješno spremanje države!");
		alert.setHeaderText("Uspješno spremanje države!");
		alert.setContentText("Uneseni podaci za državu su uspješno spremljeni.");
		Stage stage = (Stage) spremiButton.getScene().getWindow();
		stage.close();
		DrzaveController.dodajNovuDrzavu(noviId, drzava);
	}

	private int getZadnjiId() {
		
		return Main.observableListaDrzava.size();
	}
	
}
