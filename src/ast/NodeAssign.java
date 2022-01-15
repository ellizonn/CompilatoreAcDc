package ast;

import visitor.IVisitor;

public class NodeAssign extends NodeStm {
	
	private NodeId id = null;
	private NodeExpr expr = null;
	
	public NodeAssign(NodeId id, NodeExpr expr) {
		this.id = id;
		this.expr = expr;
	}
	
	public NodeId getId() {
		return this.id;
	}
	
	public NodeExpr getExpr() {
		return this.expr;
	}
	
	public void setExpr(NodeExpr expr) {
		this.expr = expr;
	}
	
	@Override
	public void accept(IVisitor visitor) {
		visitor.visit(this);
	}
	
	@Override
	public String toString() {
		return "(Assign:"+getId()+","+getExpr()+")";
	}

}
