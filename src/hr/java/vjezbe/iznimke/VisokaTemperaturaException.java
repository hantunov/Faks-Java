package hr.java.vjezbe.iznimke;

/**
 * 
 * Klasa koja nasljedjuje Exception. Cetiri konstruktora - bez parametara (ispisuje poruku), sa String parametrom, sa Throwable parametrom i sa String i Throwable parametrima. 
 * 
 * @author Hrvoje
 *
 */

public class VisokaTemperaturaException extends Exception {
	
	private static final long serialVersionUID = 215254929347542223L;

	public VisokaTemperaturaException() {
		super("Temperatura je previsoka! ");
	}	
	
	public VisokaTemperaturaException(String poruka) {
		super(poruka);
	}	
	
	public VisokaTemperaturaException(Throwable uzrok) {
		super(uzrok);
	}	
	
	public VisokaTemperaturaException(String poruka, Throwable uzrok) {
		super(poruka, uzrok);
	}	
}
