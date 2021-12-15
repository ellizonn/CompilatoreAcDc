package scanner;

public class LexicalException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4876663806801440413L;
	
	public LexicalException() {
		super();
	}
	
	public LexicalException(String message) {
        super(message);
    }
	
	public LexicalException(String message, Throwable cause) {
        super(message, cause);
    }

}
