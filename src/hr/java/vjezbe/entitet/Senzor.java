package hr.java.vjezbe.entitet;

import java.math.BigDecimal;

/**
 * Apstraktna klasa sa tri clanske varijable - String mjernaJedinica, byte
 * preciznostSenzora i BigDecimal vrijednostMjerenja. Sve tri imaju javne set i
 * get metode.
 * 
 * Apstraktna metoda dohvatiVrijednost() koju je potrebno implementirati u
 * klasama koje nasljedjuju ovu.
 * 
 * @author Hrvoje
 *
 */

public class Senzor extends BazniEntitet {

	private String mjernaJedinica;
	private byte preciznostSenzora;
	private BigDecimal vrijednostMjerenja;
	private RadSenzora radSenzora;
	private MjernaPostaja mjernaPostaja;

	public Senzor(String mjernaJedinica, byte preciznostSenzora) {
		this.mjernaJedinica = mjernaJedinica;
		this.preciznostSenzora = preciznostSenzora;
	}
	
	public Senzor(int id, String mjernaJedinica, byte preciznostSenzora, BigDecimal vrijednostMjerenja, RadSenzora radSenzora, MjernaPostaja mjernaPostaja) {
		setId(id);
		this.mjernaJedinica = mjernaJedinica;
		this.preciznostSenzora = preciznostSenzora;
		this.vrijednostMjerenja = vrijednostMjerenja;
		this.radSenzora = radSenzora;
		this.setMjernaPostaja(mjernaPostaja);		
	}

	/**
	 * Apstraktna klasa koju je potrebno implementirati u klasama koje nasljedjuju
	 * ovu.
	 * 
	 * @return String
	 */

	//public abstract String dohvatiVrijednost();

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

	public RadSenzora getRadSenzora() {
		return radSenzora;
	}

	public void setRadSenzora(RadSenzora radSenzora) {
		this.radSenzora = radSenzora;
	}

	public MjernaPostaja getMjernaPostaja() {
		return mjernaPostaja;
	}

	public void setMjernaPostaja(MjernaPostaja mjernaPostaja) {
		this.mjernaPostaja = mjernaPostaja;
	}
}
