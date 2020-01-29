package hr.java.vjezbe.entitet;

import java.math.BigDecimal;

/**
 * Sadrzi dvije clanske varijable - BigDecimal x i BigDecimal y. Obje imaju public getter i setter metode.
 * 
 * Konstruktor prima BigDecimal x i BigDecimal y.  
 * 
 * @author Hrvoje
 * 
 */

public class GeografskaTocka {

	private BigDecimal x;
	private BigDecimal y;
	
	public GeografskaTocka(BigDecimal x, BigDecimal y) {
		super();
		this.x = x;
		this.y = y;
	}

	public BigDecimal getX() {
		return x;
	}

	public void setX(BigDecimal x) {
		this.x = x;
	}

	public BigDecimal getY() {
		return y;
	}

	public void setY(BigDecimal y) {
		this.y = y;
	}	
}
