package hr.java.vjezbe.javafx;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import hr.java.vjezbe.baza.podataka.BazaPodataka;
import hr.java.vjezbe.entitet.Drzava;
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
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.util.Callback;

public class DrzaveController {

	private static List<Drzava> listaDrzava;
	public static Drzava odabranaDrzava = null;
	
	@FXML
	private TextField drzaveFilterTextField;
	@FXML
	private TableView<Drzava> drzaveTableView;
	@FXML
	private TableColumn<Drzava, String> nazivColumn;
	@FXML
	private TableColumn<Drzava, String> povrsinaColumn;
	
	@FXML
	public void initialize() {
		nazivColumn.setCellValueFactory(new PropertyValueFactory<Drzava, String>("naziv"));
		povrsinaColumn.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Drzava, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Drzava, String> param) {
						return new ReadOnlyObjectWrapper<String>(param.getValue().getPovrsina().toString());
					}
				});
		
		try {
			listaDrzava = BazaPodataka.dohvatiDrzave();
		} catch (SQLException | IOException e) {
			e.printStackTrace();
		}
		
		drzaveTableView.setItems(Main.observableListaDrzava);
	}

	public void prikaziDrzave() {
		List<Drzava> filtriraneDrzave = new ArrayList<Drzava>();
		if (drzaveFilterTextField.getText().isEmpty() == false) {
			filtriraneDrzave = listaDrzava.stream().filter(m -> m.getNaziv().contains(drzaveFilterTextField.getText()))
					.collect(Collectors.toList());
		} else {
			filtriraneDrzave = listaDrzava;
		}
		ObservableList<Drzava> listaDrzava = FXCollections.observableArrayList(filtriraneDrzave);
		drzaveTableView.setItems(listaDrzava);
	}

	public static void dodajNovuDrzavu(int noviId, Drzava novaDrzava) {

		listaDrzava.add(novaDrzava);
		try {
			BazaPodataka.spremiDrzavu(novaDrzava);
		} catch (SQLException | IOException e) {
			Glavna.logger.error("Neuspješan upis države u bazu!");
			e.printStackTrace();
		}
		Main.observableListaDrzava = FXCollections.observableArrayList(listaDrzava);
		Main.prikaziEkranDrzave();		
	}
	
	public void izmijeniDrzavu(MouseEvent click) {
        odabranaDrzava = drzaveTableView.getSelectionModel().getSelectedItem();
        
        if (odabranaDrzava != null) {
        	System.out.println(odabranaDrzava.getId() + " " + odabranaDrzava.getNaziv());   	
        	
        	try {
    			BorderPane novaDrzavaPane = FXMLLoader.load(Main.class.getResource("IzmijeniDrzavuEkran.fxml"));
    			Scene scene = new Scene(novaDrzavaPane, 400, 400);
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