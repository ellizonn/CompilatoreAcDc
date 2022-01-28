package visitor;

import java.util.ArrayList;

import ast.*;
import symbolTable.SymbolTable;

public class CodeGeneratorVisitor implements IVisitor {

	private StringBuilder log; //stringa mutabile
	private StringBuilder codice; //stringa mutabile
	private static ArrayList<Character> registro;
	
	private static char newRegister() {
		return registro.remove(0);
	}
	
	public CodeGeneratorVisitor() {
		registro = new ArrayList<Character>();
		for(int i = 65; i <= 122; i++) {	// ASCII 65 = A, 122 = z
			registro.add((char) i);
		}
		codice = new StringBuilder();
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
		// Non definibile
	}

	@Override
	public void visit(NodeDecl node) {
		node.getId().getDefinition().setRegistro(newRegister());
	}

	@Override
	public void visit(NodeBinOp node) {
		node.getLeft().accept(this);
		node.getRight().accept(this);
		if (node.getOp().equals(LangOper.PLUS)) {
			codice.append("+ ");
		}
		else if (node.getOp().equals(LangOper.MINUS)) {
			codice.append("- ");
		}
		else if (node.getOp().equals(LangOper.TIMES)) {
			codice.append("* ");
		}
		else if (node.getOp().equals(LangOper.DIV)) {
			codice.append("/ ");
		}
	}

	@Override
	public void visit(NodePrint node) {
		codice.append("l" + SymbolTable.lookup(node.getId().getName()).getRegistro() + " p P ");
	}

	@Override
	public void visit(NodeAssign node) {
		node.getExpr().accept(this);
		codice.append("s" + SymbolTable.lookup(node.getId().getName()).getRegistro() + " 0 k ");
	}

	@Override
	public void visit(NodeCost node) {
		codice.append(node.getValue() + " ");
	}

	@Override
	public void visit(NodeDeref node) {
		codice.append("l " + node.getId().getDefinition().getRegistro());
	}

	@Override
	public void visit(NodeConvert node) {
		node.getExpr().accept(this);
		codice.append("5 k ");
	}

}
