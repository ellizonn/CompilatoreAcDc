package ast;

public class NodePrint extends NodeStm {
	
	private NodeId id = null;
	
	public NodePrint(NodeId id) {
		this.id = id;
	}
	
	public NodeId getId() {
		return this.id;
	}
	
	@Override
	public String toString() {
		return "<Print:"+getId()+">";
	}

}
