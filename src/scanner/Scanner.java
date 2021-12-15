package scanner;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PushbackReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import token.*;

public class Scanner {
	final char EOF = (char) -1; // int 65535
	private int riga;
	private PushbackReader buffer;
	private Token followingToken = null;

	private List<Character> skipChars; // ' ', '\n', '\t', '\r', EOF
	private List<Character> letters; // 'a',...'z'
	private List<Character> numbers; // '0',...'9'
	
	private HashMap<Character, TokenType> operatorsMap;  //'+', '-', '*', '/', '=', ';'
	private HashMap<String, TokenType> keyWordsMap;  //"print", "tyfloat", "tyint"
	
	
	public Scanner(String fileName) throws FileNotFoundException {
		this.buffer = new PushbackReader(new FileReader(fileName));
		riga = 1;
		//inizializzare le liste e HashMaps
		this.skipChars = new ArrayList<Character>(
				Arrays.asList(new Character[] { ' ', '\n', '\t', '\r', EOF }));;
		this.letters = new ArrayList<Character>(
				Arrays.asList(new Character[] { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j',  'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z' }));;
		this.numbers = new ArrayList<Character>(
				Arrays.asList(new Character[] { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' }));;
		this.operatorsMap = new HashMap<Character, TokenType>();
		this.operatorsMap.put('+', TokenType.PLUS);
		this.operatorsMap.put('-', TokenType.MINUS);
		this.operatorsMap.put('*', TokenType.TIMES);
		this.operatorsMap.put('/', TokenType.DIV);
		this.operatorsMap.put('=', TokenType.ASSIGN);
		this.operatorsMap.put(';', TokenType.SEMI);
		this.keyWordsMap = new HashMap<String, TokenType>();
		this.keyWordsMap.put("print", TokenType.PRINT);
		this.keyWordsMap.put("float", TokenType.TYFLOAT);
		this.keyWordsMap.put("int", TokenType.TYINT);		
	}

	
	
	public Token peekToken() throws LexicalException {
		if (this.followingToken == null) {
			this.followingToken = this.nextToken();
		}
		return followingToken;
	}

	public Token nextToken() throws LexicalException {
		
		// Se followingToken!=null lo dobbiamo ritornare e poi impostare a null,
		// altrimenti vado avanti consumando l'input
		if (this.followingToken != null) {
			Token copyToken = this.followingToken;
			this.followingToken = null;
			return copyToken;
		}

		// nextChar contiene il prossimo carattere dell'input.
		char nextChar;
		try {
			nextChar = peekChar();
		} catch (IOException e) {
			throw new LexicalException("Eccezione di tipo IOException", e);
		}
		
		// Avanza nel buffer leggendo i carattere in skipChars
		// incrementando riga se leggi '\n'.
		// Se raggiungi la fine del file ritorna il Token EOF
		while (skipChars.contains(nextChar)) {
			if (nextChar=='\n') {
				this.riga+=1;
			}
			if (nextChar==EOF) {
				return new Token(TokenType.EOF, this.riga);
			}
			
			try {
				readChar();
				nextChar = peekChar();
			} catch (IOException e) {
				throw new LexicalException("Eccezione di tipo IOException", e);
			}
		}


		// Se nextChar e' in numbers
		//                return scanNumber()
		// che legge sia un intero che un float e ritorna il Token INUM o FNUM
		// i caratteri che leggete devono essere accumulati in una stringa
		// che verra' assegnata al campo valore del Token
		if (numbers.contains(nextChar)) {
			return scanNumber();
		}
		
		// Se nextChar e' in letters
		//                return scanId()
		// che legge tutte le lettere minuscole e ritorna un Token ID o
		// il Token associato Parola Chiave (per generare i Token per le
		// parole chiave usate l'HaskMap di corrispondenza
		if (letters.contains(nextChar)) {
			return scanId();
		}

		// Se nextChar e' in operators
		// ritorna il Token associato con l'operatore o il delimitatore
		if (operatorsMap.containsKey(nextChar)) {
			try {
				readChar();
			} catch (IOException e) {
				throw new LexicalException("Eccezione di tipo IOException", e);
			}
			return new Token(operatorsMap.get(nextChar), this.riga);
		}
		

		// Altrimenti il carattere NON E' UN CARATTERE LEGALE
		try {
			readChar();
		} catch (IOException e) {
			throw new LexicalException("Eccezione di tipo IOException", e);
		}
		throw new LexicalException("E' stato rilevato il carattere illegale '"+nextChar+"' alla riga "+this.riga);

	}
	
	// legge sia un intero che un float e ritorna il Token INUM o FNUM
	// i caratteri che leggete devono essere accumulati in una stringa
	// che verra' assegnata al campo valore del Token
	private Token scanNumber() throws LexicalException {
		char nextChar;
		try {
			nextChar = peekChar();
		} catch (IOException e) {
			throw new LexicalException("Eccezione di tipo IOException", e);
		}
		
		String number = new String();
		while(numbers.contains(nextChar)) {
			number += nextChar;
			try {
				readChar();
				nextChar = peekChar();
			} catch (Exception e) {
				throw new LexicalException("Eccezione di tipo IOException", e);
			}
		}
		if (nextChar=='.') {
			number += nextChar;
			try {
				readChar();
				nextChar = peekChar();
			} catch (Exception e) {
				throw new LexicalException("Eccezione di tipo IOException", e);
			}
			if (!numbers.contains(nextChar)) {
				throw new LexicalException("Rilevato numero float con parte decimale nulla");
			}
			int decimals = 1;
			while(numbers.contains(nextChar)) {
				number += nextChar;
				try {
					readChar();
					nextChar = peekChar();
				} catch (Exception e) {
					throw new LexicalException("Eccezione di tipo IOException", e);
				}
				decimals+=1;
			}
			if (decimals >= 5) {
				throw new LexicalException("Rilevato numero float con parte decimale troppo lunga");
			}
			return new Token(TokenType.FLOAT, this.riga, number);
		}
		else return new Token(TokenType.INT, this.riga, number);
	}
	
	// legge tutte le lettere minuscole e ritorna un Token ID o
	// il Token associato Parola Chiave (per generare i Token per le
	// parole chiave usate l'HaskMap di corrispondenza
	private Token scanId() throws LexicalException {
		char nextChar;
		try {
			nextChar = peekChar();
		} catch (IOException e) {
			throw new LexicalException("Eccezione di tipo IOException", e);
		}
		String keyword = new String();
		while(letters.contains(nextChar)) {
			keyword += nextChar;
			try {
				readChar();
				nextChar = peekChar();
			} catch (Exception e) {
				throw new LexicalException("Eccezione di tipo IOException", e);
			}
		}
		if (keyWordsMap.containsKey(keyword)) {
			return new Token(keyWordsMap.get(keyword), this.riga);
		}
		return new Token(TokenType.ID, this.riga, keyword);
	}

	private char readChar() throws IOException {
		return ((char) this.buffer.read());
	}

	private char peekChar() throws IOException {
		char c = (char) buffer.read();
		buffer.unread(c);
		return c;
	}

}
