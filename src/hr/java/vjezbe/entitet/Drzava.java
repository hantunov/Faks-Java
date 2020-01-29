package hr.java.vjezbe.entitet;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Sadrzi dvije clanske varijable - String naziv i BigDecimal povrsina, obje
 * imaju public getter i setter metode.
 * 
 * Konstruktor prima String naziv i BigDecimal povrsina.
 * 
 * @author Hrvoje
 *
 */

public class Drzava extends BazniEntitet {

	private String naziv;
	private BigDecimal povrsina;
	private List<Zupanija> zupanije;

	public Drzava(String naziv, BigDecimal povrsina) {
		this.naziv = naziv;
		this.povrsina = povrsina;
		this.zupanije = new ArrayList<>();
	}
	
	public Drzava(int id, String naziv, BigDecimal povrsina) {
		this.naziv = naziv;
		this.povrsina = povrsina;
		this.zupanije = new ArrayList<>();
		setId(id);
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

	public List<Zupanija> getZupanije() {
		return zupanije;
	}

}
