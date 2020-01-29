package hr.java.vjezbe.javafx;
	
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import hr.java.vjezbe.entitet.Drzava;
import hr.java.vjezbe.entitet.GeografskaTocka;
import hr.java.vjezbe.entitet.MjernaPostaja;
import hr.java.vjezbe.entitet.Mjesto;
import hr.java.vjezbe.entitet.RadSenzora;
import hr.java.vjezbe.entitet.RadioSondaznaMjernaPostaja;
import hr.java.vjezbe.entitet.Senzor;
import hr.java.vjezbe.entitet.SenzorBrzineVjetra;
import hr.java.vjezbe.entitet.SenzorTemperature;
import hr.java.vjezbe.entitet.SenzorVlage;
import hr.java.vjezbe.entitet.Senzori;
import hr.java.vjezbe.entitet.VrstaMjesta;
import hr.java.vjezbe.entitet.Zupanija;
import hr.java.vjezbe.glavna.Glavna;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;


public class Main extends Application {
	
	public static Map<Integer, Drzava> drzave = ucitajDrzave(ucitajDatoteku("dat\\drzave.txt"));
	public static Map<Integer, Zupanija> zupanije = ucitajZupanije(ucitajDatoteku("dat\\zupanije.txt"), drzave);
	public static Map<Integer, Mjesto> mjesta = ucitajMjesta(ucitajDatoteku("dat\\mjesta.txt"), zupanije);
	public static Map<Integer, GeografskaTocka> geografskeTocke = ucitajGeografskeTocke(ucitajDatoteku("dat\\geografsketocke.txt"));
	public static Map<Integer, Senzor> senzori = ucitajSenzore(ucitajDatoteku("dat\\senzori.txt"));
	public static Map<Integer, MjernaPostaja> postaje = ucitajPostaje(ucitajDatoteku("dat\\mjernepostaje.txt"), mjesta, geografskeTocke, senzori);
	
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
	
	/*@Override
	public void start(Stage primaryStage) {
		try {
			BorderPane root = (BorderPane)FXMLLoader.load(getClass().getResource("PocetniEkran.fxml"));
			Scene scene = new Scene(root,400,400);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}*/
	
	public static void main(String[] args) {
		launch(args);
	}
	
	public static List<String> ucitajDatoteku(String imeDatoteke){
		
		List<String> listaOdDatoteke = new ArrayList<>();
		
		try (BufferedReader input = new BufferedReader(new FileReader(imeDatoteke))) {
			String linija;
			while ((linija = input.readLine()) != null) {
				listaOdDatoteke.add(linija);
			}
		}
		catch(IOException e){
			System.out.println("Greska pri ucitavanju datoteke!");
			Glavna.logger.error("Greska pri ucitavanju datoteke!");
			e.printStackTrace();
		}		
		
		return listaOdDatoteke;
	}
		
	public static Map<Integer, Drzava> ucitajDrzave(List<String> listaStringova) {
		
		int brojRedovaZaDrzavu = 3;
		int id = 0;
		String naziv = null;
		BigDecimal povrsina;
		Map<Integer, Drzava> drzaveIzDatoteke = new HashMap<>();
		
		for (int i = 0; i < listaStringova.size(); i++) {
			
			String red = listaStringova.get(i);
			
			switch (i % brojRedovaZaDrzavu) {
				case 0:
					id = Integer.parseInt(red);
					break;
				case 1:
					naziv = red;
					break;
				case 2:
					povrsina = new BigDecimal(red);
					drzaveIzDatoteke.put(id, new Drzava(id, naziv, povrsina));
					break;
				default:
					break;
			}
		}
		
		return drzaveIzDatoteke;
	}
	
	public static Map<Integer, Zupanija> ucitajZupanije(List<String> listaStringova, Map<Integer, Drzava> drzaveIzDatoteke) {
		
		int brojRedovaZaZupaniju = 3;
		int id = 0;
		int idDrzave = 0;
		String naziv = null;
		Map<Integer, Zupanija> zupanijeIzDatoteke = new HashMap<>();
		
		for (int i = 0; i < listaStringova.size(); i++) {
			
			String red = listaStringova.get(i);
			
			switch (i % brojRedovaZaZupaniju) {
				case 0:
					id = Integer.parseInt(red);
					break;
				case 1:
					naziv = red;
					break;
				case 2:
					idDrzave = Integer.parseInt(red);
					zupanijeIzDatoteke.put(id, new Zupanija(id, naziv, drzaveIzDatoteke.get(idDrzave)));
					break;
				default:
					break;
			}			
		}
		
		return zupanijeIzDatoteke;
	}
	
	public static Map<Integer, Mjesto> ucitajMjesta(List<String> listaStringova, Map<Integer, Zupanija> zupanijeIzDatoteke) {
		
		int brojRedovaZaMjesto = 4;
		int id = 0;
		int idZupanije = 0;
		String naziv = null;
		VrstaMjesta vrstaMjesta = null;
		Map<Integer, Mjesto> mjestaIzDatoteke = new HashMap<>();
		
		for (int i = 0; i < listaStringova.size(); i++) {
			
			String red = listaStringova.get(i);
			
			switch (i % brojRedovaZaMjesto) {
				case 0:
					id = Integer.parseInt(red);
					break;
				case 1:
					naziv = red;
					break;
				case 2:
					vrstaMjesta = VrstaMjesta.valueOf(red);
					break;
				case 3:
					idZupanije = Integer.parseInt(red);					
					mjestaIzDatoteke.put(id, new Mjesto(id, naziv, zupanijeIzDatoteke.get(idZupanije), vrstaMjesta));
				default:
					break;
			}
		}
		
		return mjestaIzDatoteke;
	}
	
	public static Map<Integer, GeografskaTocka> ucitajGeografskeTocke(List<String> listaStringova){
		
		int brojRedovaZaGeografskuTocku = 3;
		int id = 0;
		BigDecimal x = null;
		BigDecimal y = null;
		Map<Integer, GeografskaTocka> tockeIzDatoteke = new HashMap<>();
		
		for (int i = 0; i < listaStringova.size(); i++) {
			String red = listaStringova.get(i);
			
			switch(i % brojRedovaZaGeografskuTocku) {
				case 0:
					id = Integer.parseInt(red);
					break;
				case 1:
					x = BigDecimal.valueOf(Double.parseDouble(red));
					break;
				case 2:
					y = BigDecimal.valueOf(Double.parseDouble(red));
					tockeIzDatoteke.put(id, new GeografskaTocka(id, x, y));
					break;
				default:
					break;
			}
		}
		
		return tockeIzDatoteke;
	}
	
	public static Map<Integer, Senzor> ucitajSenzore(List<String> listaStringova) {
		
		int brojRedovaZaSenzor = 4;
		Integer id = 0;
		String vrstaSenzora = null;
		BigDecimal vrijednostMjerenja = null;
		RadSenzora radSenzora;
		Map<Integer, Senzor> senzoriIzDatoteke = new HashMap<>();
		
		for (int i = 0; i < listaStringova.size(); i++) {
			
			String red = listaStringova.get(i);
			
			switch (i % brojRedovaZaSenzor) {
				case 0:
					id = Integer.parseInt(red);
					break;
				case 1:
					vrstaSenzora = red;
					break;
				case 2:
					vrijednostMjerenja = BigDecimal.valueOf(Integer.parseInt(red));
					break;
				case 3:
					radSenzora = RadSenzora.valueOf(red);
					if(vrstaSenzora.equals("ST")) {
						senzoriIzDatoteke.put(id, new SenzorTemperature("TK001"));
						senzoriIzDatoteke.get(id).setVrijednostMjerenja(vrijednostMjerenja);
						senzoriIzDatoteke.get(id).setRadSenzora(radSenzora);						
					} else if (vrstaSenzora.equals("SV")) {
						senzoriIzDatoteke.put(id, new SenzorVlage());
						senzoriIzDatoteke.get(id).setVrijednostMjerenja(vrijednostMjerenja);
						senzoriIzDatoteke.get(id).setRadSenzora(radSenzora);						
					} else if (vrstaSenzora.equals("SBV")) {
						senzoriIzDatoteke.put(id, new SenzorBrzineVjetra());
						senzoriIzDatoteke.get(id).setVrijednostMjerenja(vrijednostMjerenja);
						senzoriIzDatoteke.get(id).setRadSenzora(radSenzora);		
					}
					break;
				default:
					break;
			}
		}
		return senzoriIzDatoteke;		
	}
	
	public static Map<Integer, MjernaPostaja> ucitajPostaje(List<String> listaStringova, Map<Integer, Mjesto> mjestaIzDatoteke, Map<Integer, GeografskaTocka> tockeIzDatoteke, Map<Integer, Senzor> senzoriIzDatoteke){
		
		int brojRedovaZaPostaju = 5;
		int id = 0;
		int idMjesta = 0;
		int idTocke = 0;
		String vrstaMjernePostaje = null;
		String naziv = null;
		Map<Integer, MjernaPostaja> postajeIzDatoteke = new HashMap<>();
		
		for (int i = 0; i < listaStringova.size(); i++) {
			
			String red = listaStringova.get(i);
			
			switch (i % brojRedovaZaPostaju) {
				case 0: 
					id = Integer.parseInt(red);
					break;
				case 1:
					vrstaMjernePostaje = red;
					break;
				case 2:
					naziv = red;
					break;
				case 3:
					idMjesta = Integer.parseInt(red);
					break;
				case 4:
					idTocke = Integer.parseInt(red);
					if(vrstaMjernePostaje.equals("RSMP")) {
						Senzori<Senzor> senzori = new Senzori<>();
						for(int j = 1; j<=senzoriIzDatoteke.size(); j++) {
							senzori.getSenzori().add(senzoriIzDatoteke.get(j));														
						}						
						postajeIzDatoteke.put(id, new RadioSondaznaMjernaPostaja(id, naziv, mjestaIzDatoteke.get(idMjesta), tockeIzDatoteke.get(idTocke), senzori, 100));
					} else if (vrstaMjernePostaje.equals("MP")){
						Senzori<Senzor> senzori = new Senzori<>();
						for(int j = 1; j<=senzoriIzDatoteke.size(); j++) {
							senzori.getSenzori().add(senzoriIzDatoteke.get(j));														
						}						
						postajeIzDatoteke.put(id, new MjernaPostaja(id, naziv, mjestaIzDatoteke.get(idMjesta), tockeIzDatoteke.get(idTocke), senzori));						
					}
					break;
				default:
					break;
			}
			
		}		
		
		return postajeIzDatoteke;	
	}

	public static List<Drzava> dohvatiDrzave() {
		
		return drzave.values().stream().collect(Collectors.toList());		
	}

	public static List<Zupanija> dohvatiZupanije() {

		return zupanije.values().stream().collect(Collectors.toList());
	}

	public static List<Mjesto> dohvatiMjesta() {
		
		return mjesta.values().stream().collect(Collectors.toList());
	}

	public static List<Senzor> dohvatiSenzore() {

		return senzori.values().stream().collect(Collectors.toList());
	}

	public static List<MjernaPostaja> dohvatiPostaje() {
		
		return postaje.values().stream().collect(Collectors.toList());
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


