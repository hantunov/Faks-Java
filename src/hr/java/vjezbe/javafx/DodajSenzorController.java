package hr.java.vjezbe.javafx;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

import hr.java.vjezbe.baza.podataka.BazaPodataka;
import hr.java.vjezbe.entitet.MjernaPostaja;
import hr.java.vjezbe.entitet.RadSenzora;
import hr.java.vjezbe.entitet.Senzor;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class DodajSenzorController {

	private List<MjernaPostaja> listaPostaja;
	
	
	@FXML
	private TextField mjernaJedinicaTextField;
	@FXML
	private TextField preciznostTextField;
	@FXML
	private TextField vrijednostMjerenjaTextField;	
	@FXML
	private ComboBox<RadSenzora> radSenzoraComboBox;
	@FXML
	private ComboBox<MjernaPostaja> mjernaPostajaComboBox;
	@FXML
	private Button spremiButton;
	@FXML
	public void initialize(){
		
		for (RadSenzora radSenzora : RadSenzora.values()) {
			radSenzoraComboBox.getItems().add(radSenzora);
		}
		radSenzoraComboBox.getSelectionModel().selectFirst();
		
		try {
			listaPostaja = BazaPodataka.dohvatiMjernePostaje();
		} catch (SQLException | IOException e) {
			e.printStackTrace();
		}

		for (MjernaPostaja mjernaPostaja : listaPostaja) {
			mjernaPostajaComboBox.getItems().add(mjernaPostaja);
		}
		mjernaPostajaComboBox.getSelectionModel().selectFirst();
		
	}	
	
	public void spremiPodatke() {
		
		String mjernaJedinica = mjernaJedinicaTextField.getText();
		byte preciznost = (byte)Integer.parseInt(preciznostTextField.getText());
		BigDecimal vrijednostMjerenja = new BigDecimal(vrijednostMjerenjaTextField.getText());
		RadSenzora radSenzora = radSenzoraComboBox.getValue();
		MjernaPostaja mjernaPostaja = mjernaPostajaComboBox.getValue();
		int noviId = getZadnjiId() + 1;
		Senzor senzor = new Senzor(noviId, mjernaJedinica, preciznost, vrijednostMjerenja, radSenzora, mjernaPostaja);	
		
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Uspješno spremanje senzora!");
		alert.setHeaderText("Uspješno spremanje senzora!");
		alert.setContentText("Uneseni podaci za senzor su uspješno spremljeni.");
		alert.showAndWait();
		Stage stage = (Stage) spremiButton.getScene().getWindow();
		stage.close();
		SenzoriController.dodajNoviSenzor(noviId, senzor);;
	}

	private int getZadnjiId() {
		
		return Main.observableListaSenzora.size();
	}
}