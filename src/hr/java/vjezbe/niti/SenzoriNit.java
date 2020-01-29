package hr.java.vjezbe.niti;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import hr.java.vjezbe.baza.podataka.BazaPodataka;
import hr.java.vjezbe.javafx.PocetniEkranController;

public class SenzoriNit implements Runnable{

	@Override
	public void run() {
		
		try {
			Connection veza = BazaPodataka.spajanjeNaBazuPodataka();
			Statement statementSenzori = veza.createStatement();
			ResultSet resultSetSenzori = statementSenzori.executeQuery("SELECT COUNT(*) AS broj FROM POSTAJE.SENZOR WHERE VRIJEDNOST < 0 AND AKTIVAN = TRUE;");
		
			while (resultSetSenzori.next()) {
				Integer brojNegativnihSenzora = resultSetSenzori.getInt("broj");
				if(brojNegativnihSenzora>0) {
					PocetniEkranController.prikaziObavijestNegVrijedSenzora();
				}
			}
		
			BazaPodataka.zatvaranjeVezeNaBazuPodataka(veza);
			
		}
		catch(Exception e){
			e.printStackTrace();			
		}
		
	}
}
