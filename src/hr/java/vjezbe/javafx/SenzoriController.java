package hr.java.vjezbe.javafx;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import hr.java.vjezbe.baza.podataka.BazaPodataka;
import hr.java.vjezbe.entitet.Senzor;
import hr.java.vjezbe.glavna.Glavna;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.util.Callback;

public class SenzoriController {
	
	private static List<Senzor> listaSenzora;
	public static Senzor odabraniSenzor = null;
	
	@FXML
	private TextField senzoriFilterTextField;
	@FXML
	private TableView<Senzor> senzoriTableView;
	@FXML
	private TableColumn<Senzor, String> mjernaJedinicaColumn;
	@FXML
	private TableColumn<Senzor, String> preciznostColumn;
	@FXML
	private TableColumn<Senzor, String> vrijednostSenzoraColumn;
	
	
	@FXML
	public void initialize() {
		mjernaJedinicaColumn.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Senzor, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Senzor, String> param) {
						return new ReadOnlyObjectWrapper<String>(param.getValue().getMjernaJedinica().toString());
					}
				});
		
		preciznostColumn.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Senzor, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Senzor, String> param) {
						return new ReadOnlyObjectWrapper<String>(param.getValue().getPreciznostSenzora() + "");
					}
				});
		vrijednostSenzoraColumn.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Senzor, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Senzor, String> param) {
						return new ReadOnlyObjectWrapper<String>(param.getValue().getVrijednostMjerenja().toString());
					}
				});
		
		
		try {
			listaSenzora = BazaPodataka.dohvatiSenzore();
		} catch (SQLException | IOException e) {
			e.printStackTrace();
		}
		
		senzoriTableView.setItems(Main.observableListaSenzora);
	}

	public void prikaziSenzore() {
		List<Senzor> filtriraniSenzori = new ArrayList<Senzor>();
		if (senzoriFilterTextField.getText().isEmpty() == false) {
			filtriraniSenzori = listaSenzora.stream().filter(m -> m.getMjernaJedinica().contains(senzoriFilterTextField.getText()))
					.collect(Collectors.toList());
		} else {
			filtriraniSenzori = listaSenzora;
		}
		ObservableList<Senzor> listaSenzora = FXCollections.observableArrayList(filtriraniSenzori);
		senzoriTableView.setItems(listaSenzora);
	}
	
	public static void dodajNoviSenzor(int key, Senzor noviSenzor) {
		
		listaSenzora.add(noviSenzor);
		try {
			BazaPodataka.spremiSenzor(noviSenzor);
		} catch (SQLException | IOException e) {
			Glavna.logger.error("Neuspješan upis senzora u bazu!");
			e.printStackTrace();
		}
		Main.observableListaSenzora = FXCollections.observableArrayList(listaSenzora);
		Main.prikaziEkranSenzori();		
	}
	
	public void izmijeniSenzor(MouseEvent click) {
        odabraniSenzor = senzoriTableView.getSelectionModel().getSelectedItem();
        
        if (odabraniSenzor != null) {
        	System.out.println(odabraniSenzor.getId() + " " + odabraniSenzor.getMjernaJedinica());   	
        	
        	try {
    			BorderPane noviSenzorPane = FXMLLoader.load(Main.class.getResource("IzmijeniSenzorEkran.fxml"));
    			Scene scene = new Scene(noviSenzorPane, 250, 250);
    			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
    			Stage stage = new Stage();
    			stage.setScene(scene);
    			stage.show();
    		} catch (IOException e) {
    			e.printStackTrace();
    		}
        }
	}
}
