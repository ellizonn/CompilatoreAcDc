package ast;

import visitor.IVisitor;

public class NodeDeref extends NodeExpr {
	
	private NodeId id = null;
	
	public NodeDeref(NodeId id) {
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
		return "(Deref:"+getId()+")";
	}

}
