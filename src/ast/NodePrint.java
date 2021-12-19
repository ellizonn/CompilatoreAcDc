package ast;

public class NodePrint extends NodeStm {
	
	NodeId id;
	
	public NodePrint(NodeId id) {
		this.id = id;
	}
	
	public NodeId getId() {
		return this.id;
	}
	
	@Override
	public String toString() {
		return "Print: <"+this.id.toString()+">";
	}

}
