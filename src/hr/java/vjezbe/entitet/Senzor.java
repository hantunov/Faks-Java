package hr.java.vjezbe.entitet;

import java.math.BigDecimal;

/**
 * Apstraktna klasa sa tri clanske varijable - String mjernaJedinica, byte preciznostSenzora i BigDecimal vrijednostMjerenja. Sve tri imaju javne set i get metode.
 * 
 * Apstraktna metoda dohvatiVrijednost() koju je potrebno implementirati u klasama koje nasljedjuju ovu.
 * 
 * @author Hrvoje
 *
 */

public abstract class Senzor {
	
	private String mjernaJedinica;
	private byte preciznostSenzora;
	private BigDecimal vrijednostMjerenja;
	
	public Senzor(String mjernaJedinica, byte preciznostSenzora) {
		this.mjernaJedinica = mjernaJedinica;
		this.preciznostSenzora = preciznostSenzora;
	}
	
	/**
	 * Apstraktna klasa koju je potrebno implementirati u klasama koje nasljedjuju ovu.
	 * 
	 * @return String
	 */
	
	public abstract String dohvatiVrijednost();

	public String getMjernaJedinica() {
		return mjernaJedinica;
	}

	public void setMjernaJedinica(String mjernaJedinica) {
		this.mjernaJedinica = mjernaJedinica;
	}

	public byte getPreciznostSenzora() {
		return preciznostSenzora;
	}

	public void setPreciznostSenzora(byte preciznostSenzora) {
		this.preciznostSenzora = preciznostSenzora;
	}

	public BigDecimal getVrijednostMjerenja() {
		return vrijednostMjerenja;
	}

	public void setVrijednostMjerenja(BigDecimal vrijednostMjerenja) {
		this.vrijednostMjerenja = vrijednostMjerenja;
	}	
}
