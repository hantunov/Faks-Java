package hr.java.vjezbe.entitet;

import java.math.BigDecimal;

/**
 * Sadrzi dvije clanske varijable - String naziv i BigDecimal povrsina, obje imaju public getter i setter metode.
 * 
 * Konstruktor prima String naziv i BigDecimal povrsina. 
 * 
 * @author Hrvoje
 *
 */

public class Drzava {
	
	private String naziv;
	private BigDecimal povrsina;
	
	public Drzava(String naziv, BigDecimal povrsina) {
		super();
		this.naziv = naziv;
		this.povrsina = povrsina;
	}

	public String getNaziv() {
		return naziv;
	}

	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}

	public BigDecimal getPovrsina() {
		return povrsina;
	}

	public void setPovrsina(BigDecimal povrsina) {
		this.povrsina = povrsina;
	}
}
