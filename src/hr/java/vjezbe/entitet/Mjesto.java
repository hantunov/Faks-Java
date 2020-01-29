package hr.java.vjezbe.entitet;

import java.util.ArrayList;
import java.util.List;

/**
 * Sadrzi dvije clanske varijable - String naziv i Zupanija zupanija, obje imaju
 * public get i set metode.
 * 
 * Konstruktor prima String naziv i Zupanija zupanija.
 * 
 * @author Hrvoje
 *
 *
 */

public class Mjesto extends BazniEntitet {

	private String naziv;
	private Zupanija zupanija;
	private VrstaMjesta vrstaMjesta;
	private List<MjernaPostaja> mjernePostaje;

	public Mjesto(String naziv, Zupanija zupanija) {
		this.naziv = naziv;
		this.zupanija = zupanija;
		this.mjernePostaje = new ArrayList<>();
	}
	
	public Mjesto(int id, String naziv, Zupanija zupanija, VrstaMjesta vrstaMjesta) {
		this.naziv = naziv;
		this.zupanija = zupanija;
		this.mjernePostaje = new ArrayList<>();
		setId(id);
		this.vrstaMjesta = vrstaMjesta;
	}

	public String getNaziv() {
		return naziv;
	}

	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}

	public Zupanija getZupanija() {
		return zupanija;
	}

	public void setZupanija(Zupanija zupanija) {
		this.zupanija = zupanija;
	}

	public VrstaMjesta getVrstaMjesta() {
		return vrstaMjesta;
	}

	public void setVrstaMjesta(VrstaMjesta vrstaMjesta) {
		this.vrstaMjesta = vrstaMjesta;
	}

	public List<MjernaPostaja> getMjernePostaje() {
		return mjernePostaje;
	}
}
