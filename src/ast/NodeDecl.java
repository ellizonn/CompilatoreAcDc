package ast;

public class NodeDecl extends NodeDecSt {
	
	NodeId id = null;
	LangType type = null;
	
	public NodeDecl(NodeId id, LangType type) {
		this.id = id;
		this.type = type;
	}
	
	public NodeId getId() {
		return this.id;
	}
	
	public LangType getType() {
		return this.type;
	}
	
	@Override
	public String toString() {
		/*String outString = "Decl: <Id="+id+",Type="+type;
		return outString;*/
		return "Decl: <"+this.id+", "+this.type+">";
	}

}
