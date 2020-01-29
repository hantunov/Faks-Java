package hr.java.vjezbe.javafx;

import java.io.IOException;

import hr.java.vjezbe.entitet.Drzava;
import hr.java.vjezbe.entitet.GeografskaTocka;
import hr.java.vjezbe.entitet.MjernaPostaja;
import hr.java.vjezbe.entitet.Mjesto;
import hr.java.vjezbe.entitet.Senzor;
import hr.java.vjezbe.entitet.Zupanija;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Main extends Application {

	static ObservableList<Mjesto> observableListaMjesta;
	static ObservableList<Zupanija> observableListaZupanija;
	static ObservableList<MjernaPostaja> observableListaPostaja;
	static ObservableList<Drzava> observableListaDrzava;
	static ObservableList<Senzor> observableListaSenzora;
	static ObservableList<GeografskaTocka> observableListaGeografskihTocaka;

	private static BorderPane root;
	private Stage primaryStage;

	@Override
	public void start(Stage stage) {
		primaryStage = stage;
		try {
			root = (BorderPane) FXMLLoader.load(getClass().getResource("PocetniEkran.fxml"));
			Scene scene = new Scene(root, 600, 400);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void setCenterPane(BorderPane centerPane) {
		root.setCenter(centerPane);
	}

	public static void main(String[] args) {
		launch(args);
	}

	public static void prikaziEkranMjesta() {
		try {
			BorderPane mjestaPane = FXMLLoader.load(Main.class.getResource("Mjesta.fxml"));
			Main.setCenterPane(mjestaPane);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void prikaziEkranZupanije() {
		try {
			BorderPane zupanijePane = FXMLLoader.load(Main.class.getResource("Zupanije.fxml"));
			Main.setCenterPane(zupanijePane);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void prikaziEkranDrzave() {
		try {
			BorderPane drzavePane = FXMLLoader.load(Main.class.getResource("Drzave.fxml"));
			Main.setCenterPane(drzavePane);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void prikaziEkranMjernePostaje() {
		try {
			BorderPane postajePane = FXMLLoader.load(Main.class.getResource("Postaje.fxml"));
			Main.setCenterPane(postajePane);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void prikaziEkranSenzori() {
		try {
			BorderPane senzoriPane = FXMLLoader.load(Main.class.getResource("Senzori.fxml"));
			Main.setCenterPane(senzoriPane);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}