package ast;

import visitor.IVisitor;

public class NodePrint extends NodeStm {
	
	private NodeId id = null;
	
	public NodePrint(NodeId id) {
		this.id = id;
	}
	
	public NodeId getId() {
		return this.id;
	}
	
	@Override
	public void accept(IVisitor visitor) {
		visitor.visit(this);
	}
	
	@Override
	public String toString() {
		return "(Print:"+getId()+")";
	}

}
