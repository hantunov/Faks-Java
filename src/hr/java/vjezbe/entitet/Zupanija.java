package hr.java.vjezbe.entitet;

import java.util.ArrayList;
import java.util.List;

/**
 * Sadrzi dvije clanske varijable - String naziv i Drzava drzava, obje imaju
 * public get i set metode.
 * 
 * Konstruktor prima String naziv i Drzava drzava.
 * 
 * @author Hrvoje
 * 
 */

public class Zupanija extends BazniEntitet {

	private String naziv;
	private Drzava drzava;
	private List<Mjesto> mjesta;

	public Zupanija(String naziv, Drzava drzava) {
		this.naziv = naziv;
		this.drzava = drzava;
		this.mjesta = new ArrayList<>();
	}
	
	public Zupanija(int id, String naziv, Drzava drzava) {
		this.naziv = naziv;
		this.drzava = drzava;
		this.mjesta = new ArrayList<>();
		setId(id);
	}

	public String getNaziv() {
		return naziv;
	}

	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}

	public Drzava getDrzava() {
		return drzava;
	}

	public void setDrzava(Drzava drzava) {
		this.drzava = drzava;
	}

	public List<Mjesto> getMjesta() {
		return mjesta;
	}
}
