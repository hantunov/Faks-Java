package hr.java.vjezbe.javafx;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;

import hr.java.vjezbe.baza.podataka.BazaPodataka;
import hr.java.vjezbe.entitet.Drzava;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class IzmijeniDrzavuController {
	
	@FXML
	private TextField nazivTextField;
	@FXML
	private TextField povrsinaTextField;
	@FXML
	private Button izmijeniButton;
	@FXML
	public void initialize(){
		
	}
	
	public void izmijeniPodatke() {
		
		String naziv = nazivTextField.getText();
		BigDecimal povrsina = new BigDecimal(povrsinaTextField.getText());
		int noviId = getZadnjiId();
		Drzava drzava = new Drzava(noviId, naziv, povrsina);		
		
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Uspješna izmjena podataka!");
		alert.setHeaderText("Uspješno izmjena podataka!");
		alert.setContentText("Uneseni podaci za su uspješno izmijenjeni.");
		Stage stage = (Stage) izmijeniButton.getScene().getWindow();
		stage.close();
		try {
			BazaPodataka.izmijeniDrzavu(drzava);
		} catch (SQLException | IOException e) {
			e.printStackTrace();
		}
	}

	private int getZadnjiId() {
		
		return DrzaveController.odabranaDrzava.getId();
	}
	
}
