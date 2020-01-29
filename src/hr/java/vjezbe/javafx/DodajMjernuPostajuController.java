package hr.java.vjezbe.javafx;

import java.math.BigDecimal;

import hr.java.vjezbe.entitet.GeografskaTocka;
import hr.java.vjezbe.entitet.MjernaPostaja;
import hr.java.vjezbe.entitet.Mjesto;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class DodajMjernuPostajuController {
	
	@FXML
	private TextField nazivTextField;
	@FXML
	private TextField vrstaTextField;
	@FXML
	private ComboBox<Mjesto> mjestoCombobox;
	@FXML
	private TextField xTextField;
	@FXML
	private TextField yTextField;
	@FXML
	private Button spremiButton;
	@FXML
	public void initialize(){
		
		mjestoCombobox.setItems(Main.observableListaMjesta);
		mjestoCombobox.getSelectionModel().selectFirst();
		
	}
	
	public void spremiPodatke() {
		
		String naziv = nazivTextField.getText();
		Mjesto mjesto = mjestoCombobox.getValue();
		BigDecimal X = new BigDecimal(xTextField.getText());
		BigDecimal Y = new BigDecimal(yTextField.getText());
		GeografskaTocka g = new GeografskaTocka(X, Y);
		int noviId = getZadnjiId() + 1;		
		MjernaPostaja mjernaPostaja = new MjernaPostaja(noviId, naziv, mjesto, g);			
		
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Uspješno spremanje mjesta!");
		alert.setHeaderText("Uspješno spremanje mjesta!");
		alert.setContentText("Uneseni podaci za mjesto su uspješno spremljeni.");
		Stage stage = (Stage) spremiButton.getScene().getWindow();
		stage.close();
		PostajeController.dodajNovuMjernuPostaju(noviId, mjernaPostaja);
	}

	private int getZadnjiId() {
		
		return Main.observableListaPostaja.size();
	}
	
}