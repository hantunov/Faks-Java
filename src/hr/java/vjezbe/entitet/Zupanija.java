package hr.java.vjezbe.entitet;

/**
 * Sadrzi dvije clanske varijable - String naziv i Drzava drzava, obje imaju public get i set metode.
 * 
 * Konstruktor prima String naziv i Drzava drzava.  
 *  
 * @author Hrvoje
 * 
 */

public class Zupanija {
	
	private String naziv;
	private Drzava drzava;
			
	public Zupanija(String naziv, Drzava drzava) {
		super();
		this.naziv = naziv;
		this.drzava = drzava;
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
	
}
