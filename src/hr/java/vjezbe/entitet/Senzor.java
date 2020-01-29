package hr.java.vjezbe.entitet;

import java.math.BigDecimal;

public abstract class Senzor {
	
	private String mjernaJedinica;
	private byte preciznostSenzora;
	private BigDecimal vrijednostMjerenja;
	
	public Senzor(String mjernaJedinica, byte preciznostSenzora) {
		this.mjernaJedinica = mjernaJedinica;
		this.preciznostSenzora = preciznostSenzora;
	}
	
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
