package hr.java.vjezbe.javafx;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import hr.java.vjezbe.baza.podataka.BazaPodataka;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.stage.Stage;

public class IzmijeniSenzorController {

	@FXML
	private Button izmijeniButton;
	@FXML
	private CheckBox checkBox;
	
	@FXML	
	public void initialize(){
		
	}
	
	public void izmijeniPodatke() throws SQLException, IOException {
		
		if (checkBox.isSelected()) {
			Connection veza = BazaPodataka.spajanjeNaBazuPodataka();
			veza.setAutoCommit(false);
			try {
				PreparedStatement insertSenzor = veza.prepareStatement("UPDATE POSTAJE.SENZOR SET AKTIVAN = TRUE WHERE ID = ?;");
				insertSenzor.setInt(1, getZadnjiId());
				insertSenzor.executeUpdate();
				veza.commit();
				veza.setAutoCommit(true);
				
			} catch (Throwable ex) {
				veza.rollback();
				throw ex;
			}
			BazaPodataka.zatvaranjeVezeNaBazuPodataka(veza);
		} else {
			Connection veza = BazaPodataka.spajanjeNaBazuPodataka();
			veza.setAutoCommit(false);
			try {
				PreparedStatement insertSenzor = veza.prepareStatement("UPDATE POSTAJE.SENZOR SET AKTIVAN = FALSE WHERE ID = ?;");
				insertSenzor.setInt(1, getZadnjiId());
				insertSenzor.executeUpdate();
				veza.commit();
				veza.setAutoCommit(true);
				
			} catch (Throwable ex) {
				veza.rollback();
				throw ex;
			}
			BazaPodataka.zatvaranjeVezeNaBazuPodataka(veza);
		}
		Stage stage = (Stage) izmijeniButton.getScene().getWindow();
		stage.close();
	}

	private int getZadnjiId() {
		
		return SenzoriController.odabraniSenzor.getId();
	}
	
}
