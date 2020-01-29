package hr.java.vjezbe.javafx;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import hr.java.vjezbe.baza.podataka.BazaPodataka;
import hr.java.vjezbe.entitet.Drzava;
import hr.java.vjezbe.entitet.MjernaPostaja;
import hr.java.vjezbe.entitet.Mjesto;
import hr.java.vjezbe.entitet.Senzor;
import hr.java.vjezbe.entitet.Zupanija;
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
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.util.Callback;

public class PocetniEkranController {

	private List<Mjesto> listaMjesta;
	private List<Zupanija> listaZupanija;
	private List<MjernaPostaja> listaPostaja;
	private List<Drzava> listaDrzava;
	private List<Senzor> listaSenzora;
	
	@FXML
	private TextField mjestaFilterTextField;
	@FXML
	private TableView<Mjesto> mjestaTableView;
	@FXML
	private TableColumn<Mjesto, String> nazivColumn;
	@FXML
	private TableColumn<Mjesto, String> tipColumn;
	@FXML
	private TableColumn<Mjesto, String> zupanijaColumn;
	
	@FXML
	public void initialize() {
		nazivColumn.setCellValueFactory(new PropertyValueFactory<Mjesto, String>("naziv"));
		tipColumn.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Mjesto, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Mjesto, String> param) {
						return new ReadOnlyObjectWrapper<String>(param.getValue().getVrstaMjesta().toString());
					}
				});
		zupanijaColumn.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Mjesto, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Mjesto, String> param) {
						return new ReadOnlyObjectWrapper<String>(param.getValue().getZupanija().getNaziv());
					}
				});
		
		try {
			listaMjesta = BazaPodataka.dohvatiMjesta();
			Main.observableListaMjesta = FXCollections.observableArrayList(listaMjesta);
		
			listaZupanija = BazaPodataka.dohvatiZupanije();
			Main.observableListaZupanija = FXCollections.observableArrayList(listaZupanija);
		
			listaPostaja = BazaPodataka.dohvatiMjernePostaje();
			Main.observableListaPostaja = FXCollections.observableArrayList(listaPostaja);
		
			listaDrzava = BazaPodataka.dohvatiDrzave();
			Main.observableListaDrzava = FXCollections.observableArrayList(listaDrzava);
				
			listaSenzora = BazaPodataka.dohvatiSenzore();
			Main.observableListaSenzora = FXCollections.observableArrayList(listaSenzora);
		} catch (SQLException | IOException e) {
			e.printStackTrace();
		}
				
		
		//mjestaTableView.setItems(Main.observableListaMjesta);
		
	}

	public void prikaziMjesta() {
		List<Mjesto> filtriranaMjesta = new ArrayList<Mjesto>();
		if (mjestaFilterTextField.getText().isEmpty() == false) {
			filtriranaMjesta = listaMjesta.stream().filter(m -> m.getNaziv().contains(mjestaFilterTextField.getText()))
					.collect(Collectors.toList());
		} else {
			filtriranaMjesta = listaMjesta;
		}
		ObservableList<Mjesto> listaMjesta = FXCollections.observableArrayList(filtriranaMjesta);
		mjestaTableView.setItems(listaMjesta);
	}
	
	public void prikaziEkranMjesta() {
		try {
			BorderPane mjestaPane = FXMLLoader.load(Main.class.getResource("Mjesta.fxml"));
			Main.setCenterPane(mjestaPane);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void prikaziEkranZupanije() {
		try {
			BorderPane zupanijePane = FXMLLoader.load(Main.class.getResource("Zupanije.fxml"));
			Main.setCenterPane(zupanijePane);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void prikaziEkranDrzave() {
		try {
			BorderPane drzavePane = FXMLLoader.load(Main.class.getResource("Drzave.fxml"));
			Main.setCenterPane(drzavePane);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void prikaziEkranMjernePostaje() {
		try {
			BorderPane postajePane = FXMLLoader.load(Main.class.getResource("Postaje.fxml"));
			Main.setCenterPane(postajePane);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void prikaziEkranSenzori() {
		try {
			BorderPane senzoriPane = FXMLLoader.load(Main.class.getResource("Senzori.fxml"));
			Main.setCenterPane(senzoriPane);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void prikaziEkranZaNovoMjesto() {
		try {
			BorderPane novoMjestoPane = FXMLLoader.load(Main.class.getResource("DodajMjestoEkran.fxml"));
			Scene scene = new Scene(novoMjestoPane, 400, 400);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			Stage stage = new Stage();
			stage.setScene(scene);
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void prikaziEkranZaNovuDrzavu() {
		try {
			BorderPane novaDrzavaPane = FXMLLoader.load(Main.class.getResource("DodajDrzavuEkran.fxml"));
			Scene scene = new Scene(novaDrzavaPane, 400, 400);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			Stage stage = new Stage();
			stage.setScene(scene);
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void prikaziEkranZaNovuZupaniju() {
		try {
			BorderPane novaZupanijaPane = FXMLLoader.load(Main.class.getResource("DodajZupanijuEkran.fxml"));
			Scene scene = new Scene(novaZupanijaPane, 400, 400);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			Stage stage = new Stage();
			stage.setScene(scene);
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void prikaziEkranZaNovuMjernuPostaju() {
		try {
			BorderPane novaMjernaPostajaPane = FXMLLoader.load(Main.class.getResource("DodajMjernuPostajuEkran.fxml"));
			Scene scene = new Scene(novaMjernaPostajaPane, 400, 400);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			Stage stage = new Stage();
			stage.setScene(scene);
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void prikaziEkranZaNoviSenzor() {
		try {
			BorderPane noviSenzorPane = FXMLLoader.load(Main.class.getResource("DodajSenzorEkran.fxml"));
			Scene scene = new Scene(noviSenzorPane, 400, 400);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			Stage stage = new Stage();
			stage.setScene(scene);
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}