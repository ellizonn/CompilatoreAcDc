package parser;

import scanner.*;
import token.*;

public class Parser {
	
	private Scanner scanner;
	
	public Parser(Scanner scanner) {
		this.scanner = scanner;
	}
	
	public void parse() throws SintaxException {
		parsePrg();
		return;
	}
	
	private Token match(TokenType type) throws SintaxException {
		Token tk = null;
		try {
			tk = scanner.peekToken();
		} catch (LexicalException e) {
			throw new SintaxException("Eccezione di tipo LexicalException", e);
		}
		if (type.equals(tk.getType())) {
			try {
				return scanner.nextToken();
			} catch (LexicalException e) {
				throw new SintaxException("Eccezione di tipo LexicalException", e);
			}
		}
		else throw new SintaxException("Errore sintattico alla riga "+tk.getLine()+": aspettavo "+type+", invece ho ricevuto "+tk.getType());
	}
	
	private void parsePrg() throws SintaxException {
		Token tk = null;
		try {
			tk = scanner.peekToken();
		} catch (LexicalException e) {
			throw new SintaxException("Eccezione di tipo LexicalException", e);
		}
		switch (tk.getType()) {
		case TYINT,TYFLOAT,ID,PRINT,EOF:
			parseDSs();
			match(TokenType.EOF);
			return;
		default:
			throw new SintaxException("Errore sintattico alla riga "+tk.getLine());
		}
	}
	
	private void parseDSs() throws SintaxException {
		Token tk = null;
		try {
			tk = scanner.peekToken();
		} catch (LexicalException e) {
			throw new SintaxException("Eccezione di tipo LexicalException", e);
		}
		switch (tk.getType()) {
		case TYINT,TYFLOAT:
			parseDcl();
			parseDSs();
			return;
		case ID,PRINT:
			parseStm();
			parseDSs();
			return;
		case EOF:
			return;
		default:
			throw new SintaxException("Errore sintattico alla riga "+tk.getLine());
		}
	}
	
	private void parseDcl() throws SintaxException {
		Token tk = null;
		try {
			tk = scanner.peekToken();
		} catch (LexicalException e) {
			throw new SintaxException("Eccezione di tipo LexicalException", e);
		}
		switch (tk.getType()) {
		case TYFLOAT:
			match(TokenType.TYFLOAT);
			match(TokenType.ID);
			match(TokenType.SEMI);
			return;
		case TYINT:
			match(TokenType.TYINT);
			match(TokenType.ID);
			match(TokenType.SEMI);
			return;
		default:
			throw new SintaxException("Errore sintattico alla riga "+tk.getLine());
		}		
	}
	
	private void parseStm() throws SintaxException {
		Token tk = null;
		try {
			tk = scanner.peekToken();
		} catch (LexicalException e) {
			throw new SintaxException("Eccezione di tipo LexicalException", e);
		}
		switch (tk.getType()) {
		case ID:
			match(TokenType.ID);
			match(TokenType.ASSIGN);
			parseExp();
			match(TokenType.SEMI);
			return;
		case PRINT:
			match(TokenType.PRINT);
			match(TokenType.ID);
			match(TokenType.SEMI);
			return;
		default:
			throw new SintaxException("Errore sintattico alla riga "+tk.getLine());
		}
	}
	
	private void parseExp() throws SintaxException {
		Token tk = null;
		try {
			tk = scanner.peekToken();
		} catch (LexicalException e) {
			throw new SintaxException("Eccezione di tipo LexicalException", e);
		}
		switch (tk.getType()) {
		case INT,FLOAT,ID:
			parseTr();
			parseExpP();
			return;
		default:
			throw new SintaxException("Errore sintattico alla riga "+tk.getLine());
		}
	}
	
	private void parseExpP() throws SintaxException {
		Token tk = null;
		try {
			tk = scanner.peekToken();
		} catch (LexicalException e) {
			throw new SintaxException("Eccezione di tipo LexicalException", e);
		}
		switch (tk.getType()) {
		case PLUS:
			match(TokenType.PLUS);
			parseTr();
			parseExpP();
			return;
		case MINUS:
			match(TokenType.MINUS);
			parseTr();
			parseExpP();
			return;
		case SEMI:
			return;
		default:
			throw new SintaxException("Errore sintattico alla riga "+tk.getLine());
		}
	}
	
	private void parseTr() throws SintaxException {
		Token tk = null;
		try {
			tk = scanner.peekToken();
		} catch (LexicalException e) {
			throw new SintaxException("Eccezione di tipo LexicalException", e);
		}
		switch (tk.getType()) {
		case INT,FLOAT,ID:
			parseVal();
			parseTrP();
			return;
		default:
			throw new SintaxException("Errore sintattico alla riga "+tk.getLine());
		}
	}
	
	private void parseTrP() throws SintaxException {
		Token tk = null;
		try {
			tk = scanner.peekToken();
		} catch (LexicalException e) {
			throw new SintaxException("Eccezione di tipo LexicalException", e);
		}
		switch (tk.getType()) {
		case TIMES:
			match(TokenType.TIMES);
			parseVal();
			parseTrP();
			return;
		case DIV:
			match(TokenType.DIV);
			parseVal();
			parseTrP();
			return;
		case PLUS,MINUS,SEMI:
			return;
		default:
			throw new SintaxException("Errore sintattico alla riga "+tk.getLine());
		}
	}
	
	private void parseVal() throws SintaxException {
		Token tk = null;
		try {
			tk = scanner.peekToken();
		} catch (LexicalException e) {
			throw new SintaxException("Eccezione di tipo LexicalException", e);
		}
		switch (tk.getType()) {
		case INT:
			match(TokenType.INT);
			return;
		case FLOAT:
			match(TokenType.FLOAT);
			return;
		case ID:
			match(TokenType.ID);
			return;
		default:
			throw new SintaxException("Errore sintattico alla riga "+tk.getLine());
		}
	}
	

}