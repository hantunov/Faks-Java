package hr.java.vjezbe.entitet;

import java.math.BigDecimal;
import java.util.concurrent.ThreadLocalRandom;

import hr.java.vjezbe.iznimke.NiskaTemperaturaException;
import hr.java.vjezbe.iznimke.VisokaTemperaturaException;

/**
 * Klasa koja nasljedjuje Senzor, sluzi za mjerenje temperature, mjerna jedinica su °C, preciznost je 2. Ima posebnu metodu za simuliranje temperature na senzoru - generirajVrijednost().
 * 
 * @author Hrvoje
 *
 */

public class SenzorTemperature extends Senzor {
	
	private static final String MJERNA_JEDINICA = "°C"; 
	private static final byte PRECIZNOST = 2;
			
	private String nazivElektroKomponente;
	
	public SenzorTemperature(String nazivElektroKomponente) {
		super(MJERNA_JEDINICA, PRECIZNOST);
		this.nazivElektroKomponente = nazivElektroKomponente;
	}
	
	/**
	 * Metoda dohvaca vrijednost senzora 
	 * @return String koji sadrzi vrijednost i mjernu jedinicu
	 */
	
	@Override
	public String dohvatiVrijednost() {
		String str = "Komponenta: " + this.nazivElektroKomponente + ", vrijednost: " + this.getVrijednostMjerenja() + super.getMjernaJedinica() + ", način rada: " + super.getRadSenzora();
		return str;
	}
	
	/**
	 * Metoda za simuliranje mjerenja temperature na senzoru. Generira nasumicni broj izmedju -50 i 50 °C, zapisuje ga u vrijednost mjerenja preko setter metode, te baca exceptione ako je temperatura ispod -10°C ili iznad
	 * +40°C
	 * 
	 * @throws VisokaTemperaturaException ako je temperatura visa od 40
	 * @throws NiskaTemperaturaException ako je temperatura niza od -10
	 */
	
	public void generirajVrijednost() throws VisokaTemperaturaException{
		int min = -50;
		int max = 50;
		
		super.setVrijednostMjerenja(BigDecimal.valueOf(ThreadLocalRandom.current().nextInt(min, max + 1)));
		
		if(super.getVrijednostMjerenja().doubleValue() < -10) {
			throw new NiskaTemperaturaException();
		}
		if(super.getVrijednostMjerenja().doubleValue() > 40) {
			throw new VisokaTemperaturaException();
		}
	}
}
