package visitor;

import ast.*;
import symbolTable.*;

public class TypeCheckingVisitor implements IVisitor {
	
	private StringBuilder log; //stringa mutabile
	
	public TypeCheckingVisitor() {
		this.log = new StringBuilder();
	}

	public StringBuilder getLog() {
		return this.log;
	}
	
	public void setLog(StringBuilder log) {
		this.log = log;
	}
	
	@Override
	public void visit(NodeProgram node) {
		boolean flag = false;
		for (NodeDecSt decSt : node) {
			decSt.accept(this);
			if (decSt.getResType() != TypeDescriptor.VOID) {
				flag = true;
			}
		}
		if (flag == false) {
			node.setResType(TypeDescriptor.VOID);
		}
		else {
			node.setResType(TypeDescriptor.ERROR);
		}
	}

	@Override
	public void visit(NodeId node) {
		String name = node.getName();
		Attributes attribute = SymbolTable.lookup(name);
		if (attribute == null) {
			node.setResType(TypeDescriptor.ERROR);
			this.log.append("Variabile '"+name+"' non definita\n");
		}
		else {
			if(attribute.getType().equals(LangType.INT)) {
				node.setResType(TypeDescriptor.INT);
			}
			else if(attribute.getType().equals(LangType.FLOAT)) {
				node.setResType(TypeDescriptor.FLOAT);	
			}
			node.setDefinition(attribute);
		}
	}

	@Override
	public void visit(NodeDecl node) {
		NodeId id = node.getId();
		String name = id.getName();
		Attributes attribute = new Attributes(node.getType());
		if (!SymbolTable.enter(name, attribute)) {
			node.setResType(TypeDescriptor.ERROR);
			this.log.append("Variabile '"+name+"' gia' definita\n");
		}
		else {
			node.setResType(TypeDescriptor.VOID);
			id.setDefinition(attribute);
		}
	}
	
	@Override
	public void visit(NodePrint node) {
		node.getId().accept(this);
		if (node.getId().getResType().equals(TypeDescriptor.ERROR)) {
			node.setResType(TypeDescriptor.ERROR);
		}
		else {
			node.setResType(TypeDescriptor.VOID);
		}
	}
	
	@Override
	public void visit(NodeAssign node) {
		node.getId().accept(this);
		node.getExpr().accept(this);
		if (!compatibile(node.getId().getResType(), node.getExpr().getResType())) {
			node.setResType(TypeDescriptor.ERROR);
			if (node.getId().getResType().equals(TypeDescriptor.INT) && node.getExpr().getResType().equals(TypeDescriptor.FLOAT)) {
				this.log.append("Espressione: "+node.getExpr().toString()+" non compatibile con Id: "+node.getId().getName()+"\n");
			}
		}
		else {
			if (node.getId().getResType().equals(TypeDescriptor.FLOAT)){
				NodeExpr nodeExpr = this.converti(node.getExpr());
				node.setExpr(nodeExpr);
			}
			node.setResType(TypeDescriptor.VOID);
		}
	}

	@Override
	public void visit(NodeBinOp node) {
		node.getLeft().accept(this);
		node.getRight().accept(this);
		if (node.getLeft().getResType().equals(TypeDescriptor.ERROR) || node.getRight().getResType().equals(TypeDescriptor.ERROR)) {
			node.setResType(TypeDescriptor.ERROR);
		}
		else if (node.getLeft().getResType().equals(node.getRight().getResType())) {
			node.setResType(node.getLeft().getResType());
		}
		else {
			if (node.getLeft().getResType().equals(TypeDescriptor.INT)){
				NodeExpr nodeExpr = this.converti(node.getLeft());
				node.setLeft(nodeExpr);
			}
			else {
				NodeExpr nodeExpr = this.converti(node.getRight());
				node.setRight(nodeExpr);
			}
			node.setResType(TypeDescriptor.FLOAT);
		}
	}

	@Override
	public void visit(NodeCost node) {
		if (node.getType().equals(LangType.INT)) {
			node.setResType(TypeDescriptor.INT);
		}
		else {
			node.setResType(TypeDescriptor.FLOAT);
		}
	}

	@Override
	public void visit(NodeDeref node) {
		node.getId().accept(this);
		node.setResType(node.getId().getResType());
	}

	@Override
	public void visit(NodeConvert node) {
		if (!node.getResType().equals(TypeDescriptor.FLOAT)) {
			node.getExpr().accept(this);
			if (node.getExpr().getResType().equals(TypeDescriptor.ERROR)) {
				node.setResType(TypeDescriptor.ERROR);
			}
		}
	}
	
	private boolean compatibile(TypeDescriptor t1, TypeDescriptor t2) {
		if ((!t1.equals(TypeDescriptor.ERROR) && !t2.equals(TypeDescriptor.ERROR) && t1.equals(t2)) || (t1.equals(TypeDescriptor.FLOAT) && t2.equals(TypeDescriptor.INT))) {
			return true;
		}
		else return false;
	}
	
	private NodeExpr converti(NodeExpr node) {
		if (node.getResType().equals(TypeDescriptor.FLOAT)) {
			return node;
		}
		else {
			NodeConvert nodeConvert = new NodeConvert(node);
			nodeConvert.setResType(TypeDescriptor.FLOAT);
			return nodeConvert;
		}
	}

}
