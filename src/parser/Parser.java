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
			Token id1 = match(TokenType.ID);
			match(TokenType.ASSIGN);
			NodeExpr exp = parseExp();
			match(TokenType.SEMI);
			return new NodeAssign(new NodeId(id1.getVal()), exp);
		case PRINT:
			match(TokenType.PRINT);
			Token id2 = match(TokenType.ID);
			match(TokenType.SEMI);
			return new NodePrint(new NodeId(id2.getVal()));
		default:
			//return null;
			throw new SyntaxException("Errore sintattico alla riga "+tk.getLine());
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
			NodeExpr tr = parseTr();
			NodeExpr exp = parseExpP(tr);
			return exp;
		default:
			throw new SyntaxException("Errore sintattico alla riga "+tk.getLine());
		}
	}
	
	private NodeExpr parseExpP(NodeExpr leftOp) throws SyntaxException {
		Token tk = null;
		try {
			tk = scanner.peekToken();
		} catch (LexicalException e) {
			throw new SyntaxException("Eccezione di tipo LexicalException", e);
		}
		switch (tk.getType()) {
		case PLUS:
			match(TokenType.PLUS);
			NodeExpr trP = parseTr();
			NodeExpr expP = parseExpP(new NodeBinOp(LangOper.PLUS, leftOp, trP));
			return expP;
		case MINUS:
			match(TokenType.MINUS);
			NodeExpr trM = parseTr();
			NodeExpr expM = parseExpP(new NodeBinOp(LangOper.MINUS, leftOp, trM));
			return expM;
		case SEMI:
			return leftOp;
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
			NodeExpr val = parseVal();
			NodeExpr trp = parseTrP(val);
			return trp;
		default:
			throw new SyntaxException("Errore sintattico alla riga "+tk.getLine());
		}
	}
	
	private NodeExpr parseTrP(NodeExpr leftOp) throws SyntaxException {
		Token tk = null;
		try {
			tk = scanner.peekToken();
		} catch (LexicalException e) {
			throw new SyntaxException("Eccezione di tipo LexicalException", e);
		}
		switch (tk.getType()) {
		case TIMES:
			match(TokenType.TIMES);
			NodeExpr val1 = parseVal();
			NodeExpr trp1 = parseTrP(new NodeBinOp(LangOper.TIMES, leftOp, val1));
			return trp1;
		case DIV:
			match(TokenType.DIV);
			NodeExpr val2 = parseVal();
			NodeExpr trp2 = parseTrP(new NodeBinOp(LangOper.DIV, leftOp, val2));
			return trp2;
		case PLUS,MINUS,SEMI:
			return leftOp;
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
			return new NodeCost(tk.getVal(), LangType.INT);
			/* return null; */ //cost
		case FLOAT:
			match(TokenType.FLOAT);
			return new NodeCost(tk.getVal(), LangType.FLOAT);
			/* return null; */ //cost
		case ID:
			match(TokenType.ID);
			return new NodeDeref(new NodeId(tk.getVal()));
			/* return null; */  //deref
		default:
			throw new SyntaxException("Errore sintattico alla riga "+tk.getLine());
		}
	}
	

}
