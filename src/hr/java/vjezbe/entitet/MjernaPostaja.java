package hr.java.vjezbe.entitet;

import java.util.Collections;
import java.util.List;

/**
 * Sadrzi cetiri clanske varijable - String naziv, Mjesto mjesto, GeografskaTocka geografskaTocka i Senzor[] senzori. Sve imaju public get i set metode.
 * 
 * Posebna metoda dohvatiSenzore() dohvaca polje senzora iz objekta, sortira ga po imenu mjerne jedinice (po abecedi uzlazno) i vraca sortirano polje.
 * 
 * Konstruktor prima sve cetiri clanske varijable kao argumente.
 * 
 * @author Hrvoje
 *
 */

public class MjernaPostaja {

	private String naziv;
	private Mjesto mjesto;
	private GeografskaTocka geografskaTocka;
	private List<Senzor> senzori;
	
	public MjernaPostaja(String naziv, Mjesto mjesto, GeografskaTocka geografskaTocka, List<Senzor> senzori) {
		this.naziv = naziv;
		this.mjesto = mjesto;
		this.geografskaTocka = geografskaTocka;
		this.senzori = senzori;
	}
	

	/**
	 * Metoda dohvaca ArrayList senzora iz objekta, sortira ga po imenu mjerne jedinice (po abecedi uzlazno) i vraca sortiranu listu.
	 * 
	 * @return sortirano polje senzora
	 */
	
	public List<Senzor> dohvatiSenzore() {
		Collections.sort(senzori, (p1, p2) -> p1.getMjernaJedinica().compareTo(p2.getMjernaJedinica()));
		return senzori;
	}
	
	public String getNaziv() {
		return naziv;
	}

	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}

	public Mjesto getMjesto() {
		return mjesto;
	}

	public void setMjesto(Mjesto mjesto) {
		this.mjesto = mjesto;
	}

	public GeografskaTocka getGeografskaTocka() {
		return geografskaTocka;
	}

	public void setGeografskaTocka(GeografskaTocka geografskaTocka) {
		this.geografskaTocka = geografskaTocka;
	}

	public List<Senzor> getSenzori() {
		return senzori;
	}

	public void setSenzori(List<Senzor> senzori) {
		this.senzori = senzori;
	}

}
