package ast;

import visitor.IVisitor;
import symbolTable.Attributes;

public class NodeId extends NodeAST {
	
	private String name = null;
	private Attributes definition = null;
	
	public NodeId(String name) {
		this.name = name;
	}
	
	public String getName() {
		return this.name;
	}
	
	public Attributes getDefinition() {
		return definition;
	}

	public void setDefinition(Attributes definition) {
		this.definition = definition;
	}

	@Override
	public void accept(IVisitor visitor) {
		visitor.visit(this);
	}
	
	@Override
	public String toString() {
		return "(Id:"+getName()+")";
	}

}
