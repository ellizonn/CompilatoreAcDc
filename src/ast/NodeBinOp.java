package ast;

import visitor.IVisitor;

public class NodeBinOp extends NodeExpr {
	
	private LangOper op = null;
	private NodeExpr left = null;
	private NodeExpr right = null;
	
	public NodeBinOp(LangOper op, NodeExpr left, NodeExpr right) {
		this.op = op;
		this.left = left;
		this.right = right;
	}
	
	public LangOper getOp() {
		return this.op;
	}
	
	public NodeExpr getLeft() {
		return this.left;
	}
	
	public NodeExpr getRight() {
		return this.right;
	}
	
	public void setLeft(NodeExpr expr) {
		this.left = expr;
	}
	
	public void setRight(NodeExpr expr) {
		this.right = expr;
	}
	
	@Override
	public void accept(IVisitor visitor) {
		visitor.visit(this);
	}
	
	@Override
	public String toString() {
		return "(BinOp:"+getOp()+","+getLeft()+","+getRight()+")";
	}

}
