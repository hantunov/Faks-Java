package hr.java.vjezbe.glavna;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
import hr.java.vjezbe.iznimke.NiskaTemperaturaException;
import hr.java.vjezbe.iznimke.VisokaTemperaturaException;
import hr.java.vjezbe.sortiranje.ZupanijaSorter;

public class GlavnaDatoteke {
	
	public static void main(String[] args) {
		
		/*Map<Integer, Drzava> drzave = dohvatiDrzave(ucitajDatoteku("dat\\drzave.txt"));
		Map<Integer, Zupanija> zupanije = dohvatiZupanije(ucitajDatoteku("dat\\zupanije.txt"), drzave);
		Map<Integer, Mjesto> mjesta = dohvatiMjesta(ucitajDatoteku("dat\\mjesta.txt"), zupanije);
		Map<Integer, GeografskaTocka> geografskeTocke = dohvatiGeografskeTocke(ucitajDatoteku("dat\\geografsketocke.txt"));
		Map<Integer, Senzor> senzori = dohvatiSenzore(ucitajDatoteku("dat\\senzori.txt"));
		Map<Integer, MjernaPostaja> postaje = dohvatiPostaje(ucitajDatoteku("dat\\mjernepostaje.txt"), mjesta, geografskeTocke, senzori);
		*/
		
		Map<Integer, MjernaPostaja> postaje = dohvatiPostaje(ucitajDatoteku("dat\\mjernepostaje.txt"), dohvatiMjesta(ucitajDatoteku("dat\\mjesta.txt"),
                 							  dohvatiZupanije(ucitajDatoteku("dat\\zupanije.txt"), dohvatiDrzave(ucitajDatoteku("dat\\drzave.txt")))),
                 							  dohvatiGeografskeTocke(ucitajDatoteku("dat\\geografsketocke.txt")), dohvatiSenzore(ucitajDatoteku("dat\\senzori.txt")));
		
		ispisMjernihPostaja(postaje);
		ispisListeZupanija(postaje);
		ispisSenzoraUMjernimPostajama(postaje);
		try {
			generiranjeVrijednostiNaSenzoruTemperature(postaje);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		
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
	
	public static Map<Integer, Drzava> dohvatiDrzave(List<String> listaStringova) {
		
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
	
	public static Map<Integer, Zupanija> dohvatiZupanije(List<String> listaStringova, Map<Integer, Drzava> drzaveIzDatoteke) {
		
		int brojRedovaZaZupaniju = 2;
		int id = 0;
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
					zupanijeIzDatoteke.put(id, new Zupanija(id, naziv, drzaveIzDatoteke.get(id)));
					break;
				default:
					break;
			}			
		}
		
		return zupanijeIzDatoteke;
	}
	
	public static Map<Integer, Mjesto> dohvatiMjesta(List<String> listaStringova, Map<Integer, Zupanija> zupanijeIzDatoteke) {
		
		int brojRedovaZaMjesto = 3;
		int id = 0;
		String naziv = null;
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
					VrstaMjesta vrstaMjesta = VrstaMjesta.valueOf(red);
					mjestaIzDatoteke.put(id, new Mjesto(id, naziv, zupanijeIzDatoteke.get(id), vrstaMjesta));
				default:
					break;
			}
		}
		
		return mjestaIzDatoteke;
	}
	
	public static Map<Integer, GeografskaTocka> dohvatiGeografskeTocke(List<String> listaStringova){
		
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
	
	public static Map<Integer, Senzor> dohvatiSenzore(List<String> listaStringova) {
		
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
	
	public static Map<Integer, MjernaPostaja> dohvatiPostaje(List<String> listaStringova, Map<Integer, Mjesto> mjestaIzDatoteke, Map<Integer, GeografskaTocka> tockeIzDatoteke, Map<Integer, Senzor> senzoriIzDatoteke){
		
		int brojRedovaZaPostaju = 3;
		int id = 0;
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
					if(vrstaMjernePostaje.equals("RSMP")) {
						Senzori<Senzor> senzori = new Senzori<>();
						for(int j = 1; j<=senzoriIzDatoteke.size(); j++) {
							senzori.getSenzori().add(senzoriIzDatoteke.get(j));														
						}						
						postajeIzDatoteke.put(id, new RadioSondaznaMjernaPostaja(id, naziv, mjestaIzDatoteke.get(id), tockeIzDatoteke.get(id), senzori, 100));
					} else if (vrstaMjernePostaje.equals("MP")){
						Senzori<Senzor> senzori = new Senzori<>();
						for(int j = 1; j<=senzoriIzDatoteke.size(); j++) {
							senzori.getSenzori().add(senzoriIzDatoteke.get(j));														
						}						
						postajeIzDatoteke.put(id, new MjernaPostaja(id, naziv, mjestaIzDatoteke.get(id), tockeIzDatoteke.get(id), senzori));						
					}
					break;
				default:
					break;
			}
			
		}		
		
		return postajeIzDatoteke;	
	}
	
	public static void ispisMjernihPostaja(Map<Integer, MjernaPostaja> postaje) {
		
		for(int i = 1; i<=postaje.size(); i++) {
			if(postaje.get(i) instanceof RadioSondaznaMjernaPostaja) {
				
				System.out.println("\n\n--------------------\n");
				System.out.println("Naziv mjerne postaje: " + postaje.get(i).getNaziv());
				System.out.println("Postaja je radio sondazna");
				System.out.println("Visina radio sondazne mjerne postaje: " + ((RadioSondaznaMjernaPostaja)postaje.get(i)).dohvatiVisinuPostaje());
				System.out.println("\nPostaja se nalazi u mjestu " + postaje.get(i).getMjesto().getNaziv()
					+ "(" + postaje.get(i).getMjesto().getVrstaMjesta() + ")"
					+ ", županiji " + postaje.get(i).getMjesto().getZupanija().getNaziv()
					+ ", državi " + postaje.get(i).getMjesto().getZupanija().getDrzava().getNaziv());
				System.out.println("\nTocne koordinate postaje su x:" + postaje.get(i).getGeografskaTocka().getX()
					+ " y:" + postaje.get(i).getGeografskaTocka().getY());
				System.out.println("Vrijednosti senzora postaje su: ");
				postaje.get(i).dohvatiSenzore().stream().forEach(senzor -> System.out.println(senzor.dohvatiVrijednost()));		
				
			} else {		
		
				System.out.println("\n\n--------------------\n");
				System.out.println("Naziv mjerne postaje: " + postaje.get(i).getNaziv());
				System.out.println("\nPostaja se nalazi u mjestu " + postaje.get(i).getMjesto().getNaziv()
					+ "(" + postaje.get(i).getMjesto().getVrstaMjesta() + ")"
					+ ", županiji " + postaje.get(i).getMjesto().getZupanija().getNaziv()
					+ ", državi " + postaje.get(i).getMjesto().getZupanija().getDrzava().getNaziv());
				System.out.println("\nTocne koordinate postaje su x:" + postaje.get(i).getGeografskaTocka().getX()
					+ " y:" + postaje.get(i).getGeografskaTocka().getY());
				System.out.println("Vrijednosti senzora postaje su: ");
				postaje.get(i).dohvatiSenzore().stream().forEach(senzor -> System.out.println(senzor.dohvatiVrijednost()));
			}
			
		}
	}
	
	public static void ispisListeZupanija(Map<Integer, MjernaPostaja> postaje) {
		
		Set<Zupanija> setZupanija = new HashSet<>();
		
		for (int i = 1; i <= postaje.size(); i++) {
			setZupanija.add(postaje.get(i).getMjesto().getZupanija());
		}

		List<Zupanija> listZupanija = new ArrayList<>(setZupanija);
		
		Collections.sort(listZupanija, new ZupanijaSorter());
		
		System.out.println();
		System.out.println("Lista zupanija: ");
		for (Zupanija z : listZupanija) {
			System.out.println(z.getNaziv());
		}
	}
	
	public static void ispisSenzoraUMjernimPostajama(Map<Integer, MjernaPostaja> postaje) {
		
		System.out.println();
		
		for(int i = 1; i<=postaje.size(); i++) {
			System.out.println("U mjestu " + postaje.get(i).getMjesto().getNaziv() + " se nalaze slijedeci senzori: ");
			for(int j = 0; j<postaje.get(i).getSenzori().getSenzori().size(); j++) {
				if(postaje.get(i).getSenzori().getSenzori().get(j) instanceof SenzorBrzineVjetra) {
					System.out.println("Senzor brzine vjetra");
				}
				if(postaje.get(i).getSenzori().getSenzori().get(j) instanceof SenzorVlage) {
					System.out.println("Senzor vlage");
				}
				if(postaje.get(i).getSenzori().getSenzori().get(j) instanceof SenzorTemperature) {
					System.out.println("Senzor temperature");
				}
			}
			System.out.println();
		}
	}
	
	public static void generiranjeVrijednostiNaSenzoruTemperature(Map<Integer, MjernaPostaja> postaje) throws InterruptedException {
		do {
			for(int i=1; i<=postaje.size(); i++) {
				for(int j=0; j<postaje.get(i).getSenzori().getSenzori().size(); j++) {
					if(postaje.get(i).getSenzori().getSenzori().get(j) instanceof SenzorTemperature) {
						try {
							((SenzorTemperature) postaje.get(i).getSenzori().getSenzori().get(j)).generirajVrijednost();							
						} catch (VisokaTemperaturaException e) {
							System.out.println(e.getMessage() + "Postaja " + postaje.get(i).getNaziv() + " javlja temperaturu od " + postaje.get(i).getSenzori().getSenzori().get(j).getVrijednostMjerenja() + " °C");
							Glavna.logger.error(e.getMessage(), e);
						} catch (NiskaTemperaturaException e) {							
							System.out.println(e.getMessage() + "Postaja " + postaje.get(i).getNaziv() + " javlja temperaturu od " + postaje.get(i).getSenzori().getSenzori().get(j).getVrijednostMjerenja() + " °C");
							Glavna.logger.error(e.getMessage(), e);
						}
					}
				}
			}
			Thread.sleep(1000);
		} while(true);
	}
}
