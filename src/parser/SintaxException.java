package parser;

public class SintaxException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8292988087725586865L;

	public SintaxException() {
		super();
	}
	
	public SintaxException(String message) {
        super(message);
    }
	
	public SintaxException(String message, Throwable cause) {
        super(message, cause);
    }

}
