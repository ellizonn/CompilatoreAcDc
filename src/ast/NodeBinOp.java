package ast;

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
	
	@Override
	public String toString() {
		return "BinOp: <"+getOp()+", "+getLeft()+", "+getRight()+">";
	}

}
