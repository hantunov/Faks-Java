package hr.java.vjezbe.iznimke;

/**
 * Klasa koja nasljedjuje RuntimeException. Cetiri konstruktora - bez parametara(ispisuje poruku), sa String parametrom, sa Throwable parametrom i sa String i Throwable parametrima. 
 * 
 * @author Hrvoje
 *
 */

public class NiskaTemperaturaException extends RuntimeException {
	
	private static final long serialVersionUID = 4787462950417267751L;

	public NiskaTemperaturaException() {
		super("Temperatura je preniska! ");
	}	
	
	public NiskaTemperaturaException(String poruka) {
		super(poruka);
	}	
	
	public NiskaTemperaturaException(Throwable uzrok) {
		super(uzrok);
	}	
	
	public NiskaTemperaturaException(String poruka, Throwable uzrok) {
		super(poruka, uzrok);
	}	

}
