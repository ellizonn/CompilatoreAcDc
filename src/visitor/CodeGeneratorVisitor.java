package visitor;

import java.util.ArrayList;
import java.util.HashMap;

import ast.LangOper;
import ast.NodeAssign;
import ast.NodeBinOp;
import ast.NodeConvert;
import ast.NodeCost;
import ast.NodeDecSt;
import ast.NodeDecl;
import ast.NodeDeref;
import ast.NodeId;
import ast.NodePrint;
import ast.NodeProgram;
import symbolTable.SymbolTable;

public class CodeGeneratorVisitor implements IVisitor {

	private StringBuilder log; //stringa mutabile
	private StringBuilder codice; //stringa mutabile
	private static ArrayList<Character> registro;
	private HashMap<LangOper, Character> operazioni;
	
	//Aggiungere un contenitore per registri (caratteri)
	//Aggiungere newRegister() che genera un nuovo registro
	
	private void inizializzaOperazioniMap() {
		operazioni = new HashMap<LangOper, Character>();
		operazioni.put(LangOper.PLUS, '+');
		operazioni.put(LangOper.MINUS, '-');
		operazioni.put(LangOper.TIMES, '*');
		operazioni.put(LangOper.DIV, '/');
	}
	
	private static char newRegister() {
		return registro.remove(0);
	}
	
	public CodeGeneratorVisitor() {
		registro = new ArrayList<Character>();
		for(int i = 65; i <= 122; i++) {	// ASCII 65 = A, 122 = z
			registro.add((char) i);
		}
		codice = new StringBuilder();
		inizializzaOperazioniMap();
	}
	
	public StringBuilder getLog() {
		return this.log;
	}
	
	public StringBuilder getCodice() {
		return this.codice;
	}
	
	@Override
	public void visit(NodeProgram node) {
		for (NodeDecSt decSt : node) {
			decSt.accept(this);
		}
	}

	@Override
	public void visit(NodeId node) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(NodeDecl node) {
		node.getId().getDefinition().setRegistro(newRegister());
		SymbolTable.enter(node.getId().getName(), node.getId().getDefinition());
	}

	@Override
	public void visit(NodeBinOp node) {
		node.getLeft().accept(this);
		node.getRight().accept(this);
		if(node.getLeft() instanceof NodeConvert || node.getRight() instanceof NodeConvert)
			codice.append(" 5 k");	
		codice.append(" " + operazioni.get(node.getOp()));
	}

	@Override
	public void visit(NodePrint node) {
		char nomeRegistro = SymbolTable.lookup(node.getId().getName()).getRegistro();
		codice.append(" l" + nomeRegistro + " p" + " P");
	}

	@Override
	public void visit(NodeAssign node) {
		node.getExpr().accept(this);
		char nomeRegistro = SymbolTable.lookup(node.getId().getName()).getRegistro();
		codice.append(" s" + nomeRegistro + " 0 k");
	}

	@Override
	public void visit(NodeCost node) {
		codice.append(" " + node.getValue());
	}

	@Override
	public void visit(NodeDeref node) {
		char nomeRegistroDeref = node.getId().getDefinition().getRegistro();	// getDefinition() e' un attributo, quindi posso applicarvi direttamente getRegistro() - No SymbolTable
		codice.append(" l" + nomeRegistroDeref);
	}

	@Override
	public void visit(NodeConvert node) {
		node.getExpr().accept(this);
	}

}
