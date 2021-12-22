package ast;

public class NodeId extends NodeAST {
	
	private String name = null;
	
	public NodeId(String name) {
		this.name = name;
	}
	
	public String getName() {
		return this.name;
	}
	
	@Override
	public String toString() {
		return "(Id:"+getName()+")";
	}

}
