package hr.java.vjezbe.entitet;

/**
 *  Sadrzi dvije clanske varijable - String naziv i Zupanija zupanija, obje imaju public get i set metode.
 * 
 * Konstruktor prima String naziv i Zupanija zupanija.  
 * 
 * @author Hrvoje
 *
 *
 */

public class Mjesto {

	private String naziv;
	private Zupanija zupanija;

	public Mjesto(String naziv, Zupanija zupanija) {
		super();
		this.naziv = naziv;
		this.zupanija = zupanija;
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

}
