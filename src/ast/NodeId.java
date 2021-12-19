package ast;

public class NodeId extends NodeAST {
	
	String name;
	
	public NodeId(String name) {
		this.name = name;
	}
	
	public String getName() {
		return this.name;
	}
	
	@Override
	public String toString() {
		return "Id: "+this.name;
	}

}
