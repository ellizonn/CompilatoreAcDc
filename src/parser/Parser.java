package parser;

import java.util.ArrayList;

import ast.*;
import scanner.*;
import token.*;

public class Parser {
	
	private Scanner scanner;
	
	public Parser(Scanner scanner) {
		this.scanner = scanner;
	}
	
	public NodeProgram parse() throws SyntaxException {
		return parsePrg();
	}
	
	private Token match(TokenType type) throws SyntaxException {
		Token tk = null;
		try {
			tk = scanner.peekToken();
		} catch (LexicalException e) {
			throw new SyntaxException("Eccezione di tipo LexicalException", e);
		}
		if (type.equals(tk.getType())) {
			try {
				return scanner.nextToken();
			} catch (LexicalException e) {
				throw new SyntaxException("Eccezione di tipo LexicalException", e);
			}
		}
		else throw new SyntaxException("Errore sintattico alla riga "+tk.getLine()+": aspettavo "+type+", invece ho ricevuto "+tk.getType());
	}
	
	private NodeProgram parsePrg() throws SyntaxException {
		Token tk = null;
		try {
			tk = scanner.peekToken();
		} catch (LexicalException e) {
			throw new SyntaxException("Eccezione di tipo LexicalException", e);
		}
		switch (tk.getType()) {
		case TYINT,TYFLOAT,ID,PRINT,EOF:
			ArrayList<NodeDecSt> decSts = parseDSs();
			match(TokenType.EOF);
			return new NodeProgram(decSts);
		default:
			throw new SyntaxException("Errore sintattico alla riga "+tk.getLine());
		}
	}

	private ArrayList<NodeDecSt> parseDSs() throws SyntaxException {
		Token tk = null;
		try {
			tk = scanner.peekToken();
		} catch (LexicalException e) {
			throw new SyntaxException("Eccezione di tipo LexicalException", e);
		}
		switch (tk.getType()) {
		case TYINT,TYFLOAT:
			NodeDecl nodeDecl = parseDcl();
			ArrayList<NodeDecSt> list1 = parseDSs();
			list1.add(0, nodeDecl);
			return list1;
		case ID,PRINT:
			NodeStm nodeStm = parseStm();
			ArrayList<NodeDecSt> list2 = parseDSs();
			list2.add(0, nodeStm);
			return list2;
		case EOF:
			return new ArrayList<NodeDecSt>();
		default:
			throw new SyntaxException("Errore sintattico alla riga "+tk.getLine());
		}
	}
	
	private NodeDecl parseDcl() throws SyntaxException {
		Token tk = null;
		try {
			tk = scanner.peekToken();
		} catch (LexicalException e) {
			throw new SyntaxException("Eccezione di tipo LexicalException", e);
		}
		switch (tk.getType()) {
		case TYFLOAT:
			match(TokenType.TYFLOAT);
			Token id1 = match(TokenType.ID);
			match(TokenType.SEMI);
			return new NodeDecl(new NodeId(id1.getVal()), LangType.FLOAT);
		case TYINT:
			match(TokenType.TYINT);
			Token id2 = match(TokenType.ID);
			match(TokenType.SEMI);
			return new NodeDecl(new NodeId(id2.getVal()), LangType.INT);
		default:
			throw new SyntaxException("Errore sintattico alla riga "+tk.getLine());
		}		
	}
	
	private NodeStm parseStm() throws SyntaxException {
		Token tk = null;
		try {
			tk = scanner.peekToken();
		} catch (LexicalException e) {
			throw new SyntaxException("Eccezione di tipo LexicalException", e);
		}
		switch (tk.getType()) {
		case ID:
			match(TokenType.ID);
			match(TokenType.ASSIGN);
			parseExp();
			match(TokenType.SEMI);
			return null; //TODO da fare
		case PRINT:
			match(TokenType.PRINT);
			Token id = match(TokenType.ID);
			match(TokenType.SEMI);
			return new NodePrint(new NodeId(id.getVal()));
		default:
			return null;
			//throw new SyntaxException("Errore sintattico alla riga "+tk.getLine());
		}
	}
	
	private NodeExpr parseExp() throws SyntaxException {
		Token tk = null;
		try {
			tk = scanner.peekToken();
		} catch (LexicalException e) {
			throw new SyntaxException("Eccezione di tipo LexicalException", e);
		}
		switch (tk.getType()) {
		case INT,FLOAT,ID:
			parseTr();
			parseExpP();
			return null;
		default:
			throw new SyntaxException("Errore sintattico alla riga "+tk.getLine());
		}
	}
	
	private NodeExpr parseExpP() throws SyntaxException {
		Token tk = null;
		try {
			tk = scanner.peekToken();
		} catch (LexicalException e) {
			throw new SyntaxException("Eccezione di tipo LexicalException", e);
		}
		switch (tk.getType()) {
		case PLUS:
			match(TokenType.PLUS);
			parseTr();
			parseExpP();
			return null;
		case MINUS:
			match(TokenType.MINUS);
			parseTr();
			parseExpP();
			return null;
		case SEMI:
			return null;
		default:
			throw new SyntaxException("Errore sintattico alla riga "+tk.getLine());
		}
	}
	
	private NodeExpr parseTr() throws SyntaxException {
		Token tk = null;
		try {
			tk = scanner.peekToken();
		} catch (LexicalException e) {
			throw new SyntaxException("Eccezione di tipo LexicalException", e);
		}
		switch (tk.getType()) {
		case INT,FLOAT,ID:
			parseVal();
			parseTrP();
			return null;
		default:
			throw new SyntaxException("Errore sintattico alla riga "+tk.getLine());
		}
	}
	
	private NodeExpr parseTrP() throws SyntaxException {
		Token tk = null;
		try {
			tk = scanner.peekToken();
		} catch (LexicalException e) {
			throw new SyntaxException("Eccezione di tipo LexicalException", e);
		}
		switch (tk.getType()) {
		case TIMES:
			match(TokenType.TIMES);
			parseVal();
			parseTrP();
			return null;
		case DIV:
			match(TokenType.DIV);
			parseVal();
			parseTrP();
			return null;
		case PLUS,MINUS,SEMI:
			return null;
		default:
			throw new SyntaxException("Errore sintattico alla riga "+tk.getLine());
		}
	}
	
	private NodeExpr parseVal() throws SyntaxException {
		Token tk = null;
		try {
			tk = scanner.peekToken();
		} catch (LexicalException e) {
			throw new SyntaxException("Eccezione di tipo LexicalException", e);
		}
		switch (tk.getType()) {
		case INT:
			match(TokenType.INT);
			return null;
		case FLOAT:
			match(TokenType.FLOAT);
			return null;
		case ID:
			match(TokenType.ID);
			return null;
		default:
			throw new SyntaxException("Errore sintattico alla riga "+tk.getLine());
		}
	}
	

}
