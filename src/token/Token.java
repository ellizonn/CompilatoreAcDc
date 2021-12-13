package token;

public class Token {

	private int riga;
	private TokenType tipo;
	private String val;
	
	public Token(TokenType tipo, int riga, String val) {
		this.tipo = tipo;
		this.riga = riga;
		this.val = val;
	}
	
	public Token(TokenType tipo, int riga) {
		this.tipo = tipo;
		this.riga = riga;
		this.val = null;
	}

    // Getters per i campi
	
	public int getLine() {
		return this.riga;
	}
	
	public TokenType getType() {
		return this.tipo;
	}
	
	public String getVal() {
		return this.val;
	}
    
	public String toString() {
		if (val==null) {
			return "<"+this.tipo+",r:"+this.riga+">";
		}
		else return "<"+this.tipo+",r:"+this.riga+","+this.val+">";
	}

}
