package hr.java.vjezbe.baza.podataka;

import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import hr.java.vjezbe.entitet.Drzava;
import hr.java.vjezbe.entitet.GeografskaTocka;
import hr.java.vjezbe.entitet.MjernaPostaja;
import hr.java.vjezbe.entitet.Mjesto;
import hr.java.vjezbe.entitet.RadSenzora;
import hr.java.vjezbe.entitet.Senzor;
import hr.java.vjezbe.entitet.VrstaMjesta;
import hr.java.vjezbe.entitet.Zupanija;

public class BazaPodataka {
	
	private static final String DATABASE_FILE = "bazaPodataka.properties";
	
	private static Connection spajanjeNaBazuPodataka() throws SQLException, IOException{
		
		Properties svojstva = new Properties();
		
		svojstva.load(new FileReader(DATABASE_FILE));
		
		String urlBazePodataka = svojstva.getProperty("bazaPodatakaUrl");
		String korisnickoIme = svojstva.getProperty("korisnickoIme");
		String lozinka = svojstva.getProperty("lozinka");
		
		Connection veza = DriverManager.getConnection(urlBazePodataka,korisnickoIme,lozinka);
		return veza;
	}
	
	private static void zatvaranjeVezeNaBazuPodataka(Connection veza) throws SQLException, IOException{
		
		veza.close();		
	}
	
	public static void spremiDrzavu(Drzava drzava) throws SQLException, IOException {
		Connection veza = spajanjeNaBazuPodataka();
		veza.setAutoCommit(false);
	
		try {
			PreparedStatement insertDrzava = veza.prepareStatement("INSERT INTO POSTAJE.DRZAVA	(NAZIV, POVRSINA) VALUES (?, ?);");
			insertDrzava.setString(1, drzava.getNaziv());
			insertDrzava.setDouble(2, drzava.getPovrsina().doubleValue());
			insertDrzava.executeUpdate();
			
			veza.commit();
			veza.setAutoCommit(true);
		}
		catch(Throwable ex) {
			veza.rollback();
			throw ex;
		}
		
		zatvaranjeVezeNaBazuPodataka(veza);
	}
	
	public static List<Drzava> dohvatiDrzave() throws SQLException, IOException {
		Connection veza = spajanjeNaBazuPodataka();
		Statement statementDrzave = veza.createStatement();
		ResultSet resultSetDrzave = statementDrzave.executeQuery("SELECT * FROM POSTAJE.DRZAVA");
		List<Drzava> listaDrzava = new ArrayList<>();
	
		while (resultSetDrzave.next()) {
			
			Integer drzavaId = resultSetDrzave.getInt("ID");
			String naziv = resultSetDrzave.getString("NAZIV");
			BigDecimal povrsina = new BigDecimal(resultSetDrzave.getDouble("POVRSINA"));
			Drzava drzava = new Drzava(drzavaId, naziv, povrsina);
			
			listaDrzava.add(drzava);
		}
	
		zatvaranjeVezeNaBazuPodataka(veza);
	
		return listaDrzava;
	}
	
	public static void spremiZupaniju(Zupanija zupanija) throws SQLException, IOException {
		Connection veza = spajanjeNaBazuPodataka();
		veza.setAutoCommit(false);
	
		try {
			PreparedStatement insertZupanija = veza.prepareStatement("INSERT INTO POSTAJE.ZUPANIJA	(NAZIV, DRZAVA_ID) VALUES (?, ?);");
			insertZupanija.setString(1, zupanija.getNaziv());
			insertZupanija.setDouble(2, zupanija.getDrzava().getId());
			insertZupanija.executeUpdate();
			
			veza.commit();
			veza.setAutoCommit(true);
		}
		catch(Throwable ex) {
			veza.rollback();
			throw ex;
		}
		
		zatvaranjeVezeNaBazuPodataka(veza);
	}
	
	public static List<Zupanija> dohvatiZupanije() throws SQLException, IOException {
		Connection veza = spajanjeNaBazuPodataka();
		Statement statementZupanije = veza.createStatement();
		ResultSet resultSetZupanije = statementZupanije.executeQuery("SELECT * FROM POSTAJE.ZUPANIJA");
		List<Zupanija> listaZupanija = new ArrayList<>();
	
		while (resultSetZupanije.next()) {
			Integer zupanijaId = resultSetZupanije.getInt("ID");
			String naziv = resultSetZupanije.getString("NAZIV");
			Integer drzavaId = resultSetZupanije.getInt("DRZAVA_ID");
			List<Drzava> drzave = dohvatiDrzave();
			Zupanija zupanija = new Zupanija(zupanijaId, naziv, drzave.get(drzavaId-1));
			
			listaZupanija.add(zupanija);
		}
	
		zatvaranjeVezeNaBazuPodataka(veza);
	
		return listaZupanija;
	}
	
	public static void spremiMjesto(Mjesto mjesto) throws SQLException, IOException {
		Connection veza = spajanjeNaBazuPodataka();
		veza.setAutoCommit(false);
	
		try {
			PreparedStatement insertMjesto = veza.prepareStatement("INSERT INTO POSTAJE.MJESTO	(NAZIV, VRSTA, ZUPANIJA_ID) VALUES (?, ?, ?);");
			insertMjesto.setString(1, mjesto.getNaziv());
			insertMjesto.setString(2, mjesto.getVrstaMjesta().toString());
			insertMjesto.setDouble(3, mjesto.getZupanija().getId());
			insertMjesto.executeUpdate();
			
			veza.commit();
			veza.setAutoCommit(true);
		}
		catch(Throwable ex) {
			veza.rollback();
			throw ex;
		}
		
		zatvaranjeVezeNaBazuPodataka(veza);
	}
	
	public static List<Mjesto> dohvatiMjesta() throws SQLException, IOException {
		Connection veza = spajanjeNaBazuPodataka();
		Statement statementMjesta = veza.createStatement();
		ResultSet resultSetMjesta = statementMjesta.executeQuery("SELECT * FROM POSTAJE.MJESTO");
		List<Mjesto> listaMjesta = new ArrayList<>();
	
		while (resultSetMjesta.next()) {
			Integer mjestoId = resultSetMjesta.getInt("ID");
			String naziv = resultSetMjesta.getString("NAZIV");
			String vrstaMjesta = resultSetMjesta.getString("VRSTA");
			Integer zupanijaId = resultSetMjesta.getInt("ZUPANIJA_ID");
			List<Zupanija> zupanije = dohvatiZupanije();
			Mjesto mjesto = new Mjesto(mjestoId, naziv, zupanije.get(zupanijaId-1), VrstaMjesta.valueOf(vrstaMjesta));
			
			listaMjesta.add(mjesto);
		}
	
		zatvaranjeVezeNaBazuPodataka(veza);
	
		return listaMjesta;
	}
	
	public static void spremiMjernuPostaju(MjernaPostaja mjernaPostaja) throws SQLException, IOException {
		Connection veza = spajanjeNaBazuPodataka();
		veza.setAutoCommit(false);
	
		try {
			PreparedStatement insertMjernaPostaja = veza.prepareStatement("INSERT INTO POSTAJE.MJERNA_POSTAJA	(NAZIV, MJESTO_ID, LAT, LNG) VALUES (?, ?, ?, ?);");
			insertMjernaPostaja.setString(1, mjernaPostaja.getNaziv());
			insertMjernaPostaja.setDouble(2, mjernaPostaja.getMjesto().getId());
			insertMjernaPostaja.setDouble(3, mjernaPostaja.getGeografskaTocka().getX().doubleValue());
			insertMjernaPostaja.setDouble(3, mjernaPostaja.getGeografskaTocka().getY().doubleValue());
			insertMjernaPostaja.executeUpdate();
			
			veza.commit();
			veza.setAutoCommit(true);
		}
		catch(Throwable ex) {
			veza.rollback();
			throw ex;
		}
		
		zatvaranjeVezeNaBazuPodataka(veza);
	}
	
	public static List<MjernaPostaja> dohvatiMjernePostaje() throws SQLException, IOException {
		Connection veza = spajanjeNaBazuPodataka();
		Statement statementMjernihPostaja = veza.createStatement();
		ResultSet resultSetMjernihPostaja = statementMjernihPostaja.executeQuery("SELECT * FROM POSTAJE.MJERNA_POSTAJA");
		List<MjernaPostaja> listaMjernihPostaja = new ArrayList<>();
	
		while (resultSetMjernihPostaja.next()) {
			Integer mjernaPostajaId = resultSetMjernihPostaja.getInt("ID");
			String naziv = resultSetMjernihPostaja.getString("NAZIV");
			Integer mjestoId = resultSetMjernihPostaja.getInt("MJESTO_ID");
			BigDecimal geoTockaX = new BigDecimal(resultSetMjernihPostaja.getDouble("LAT"));
			BigDecimal geoTockaY = new BigDecimal(resultSetMjernihPostaja.getDouble("LNG"));
			GeografskaTocka geografskaTocka = new GeografskaTocka(geoTockaX, geoTockaY);
			List<Mjesto> mjesta = dohvatiMjesta();
			MjernaPostaja mjernaPostaja = new MjernaPostaja(mjernaPostajaId, naziv, mjesta.get(mjestoId-1), geografskaTocka);
			
			listaMjernihPostaja.add(mjernaPostaja);
		}
	
		zatvaranjeVezeNaBazuPodataka(veza);
	
		return listaMjernihPostaja;
	}
	
	public static void spremiSenzor(Senzor senzor) throws SQLException, IOException {
		Connection veza = spajanjeNaBazuPodataka();
		veza.setAutoCommit(false);
		
		try {
			PreparedStatement insertSenzor = veza.prepareStatement("INSERT INTO POSTAJE.SENZOR (MJERNA_JEDINICA, PRECIZNOST, VRIJEDNOST, RAD_SENZORA, MJERNA_POSTAJA_ID) VALUES (?, ?, ?, ?, ?);");
			insertSenzor.setString(1, senzor.getMjernaJedinica());
			Integer preciznostSenzora = (int) senzor.getPreciznostSenzora();
			insertSenzor.setDouble(2, preciznostSenzora.doubleValue());
			insertSenzor.setDouble(3, senzor.getVrijednostMjerenja().doubleValue());
			insertSenzor.setString(4, senzor.getRadSenzora().toString());
			insertSenzor.setInt(5, senzor.getMjernaPostaja().getId());
			insertSenzor.executeUpdate();
			veza.commit();
			veza.setAutoCommit(true);

		} catch (Throwable ex) {
			veza.rollback();
			throw ex;
		}
		zatvaranjeVezeNaBazuPodataka(veza);
	}

	public static List<Senzor> dohvatiSenzore() throws SQLException, IOException {
		Connection veza = spajanjeNaBazuPodataka();
		Statement statementSenzora = veza.createStatement();
		ResultSet resultSetSenzora = statementSenzora.executeQuery("SELECT * FROM POSTAJE.SENZOR");
		List<Senzor> listaSenzora = new ArrayList<>();
		
		while (resultSetSenzora.next()) {
			Integer senzorId = resultSetSenzora.getInt("ID");
			String mjernaJedinica = resultSetSenzora.getString("MJERNA_JEDINICA");
			byte preciznost = (byte)resultSetSenzora.getDouble("PRECIZNOST");
			BigDecimal vrijednost = new BigDecimal(resultSetSenzora.getDouble("VRIJEDNOST"));
			String rad = resultSetSenzora.getString("RAD_SENZORA");
			Integer postajaId = resultSetSenzora.getInt("MJERNA_POSTAJA_ID");
			List<MjernaPostaja> listaPostaja = dohvatiMjernePostaje();
			Senzor senzor = new Senzor(senzorId, mjernaJedinica, preciznost, vrijednost, RadSenzora.valueOf(rad), listaPostaja.get(postajaId-1));
			
			listaSenzora.add(senzor);
		}
		zatvaranjeVezeNaBazuPodataka(veza);
		
		return listaSenzora;
	}
}
