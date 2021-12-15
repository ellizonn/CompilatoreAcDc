package ast;

import java.util.ArrayList;

public class NodeProgram extends NodeAST {
	
	ArrayList<NodeDecSt> decSts;
	
	public NodeProgram(ArrayList<NodeDecSt> decSts) {
		this.decSts = decSts;
	}
	
	public ArrayList<NodeDecSt> getDecSts() {
		return this.decSts;
	}
	
	@Override
	public String toString() {
		return "Prg: <decSts="+decSts.toString()+">";
	}

}