package parser;

public class SyntaxException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8292988087725586865L;

	public SyntaxException() {
		super();
	}
	
	public SyntaxException(String message) {
        super(message);
    }
	
	public SyntaxException(String message, Throwable cause) {
        super(message, cause);
    }

}
