package ast;

public class NodeCost extends NodeExpr {
	
	private String value = null;
	private LangType type = null;
	
	public NodeCost(String value, LangType type) {
		this.value = value;
		this.type = type;
	}
	
	public String getValue() {
		return this.value;
	}
	
	public LangType getType() {
		return this.type;
	}
	
	@Override
	public String toString() {
		return "Cost: <"+this.value+", "+this.type+">";
	}
	
	

}
