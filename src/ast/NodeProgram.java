package ast;

import java.util.ArrayList;

public class NodeProgram extends NodeAST {
	
	private ArrayList<NodeDecSt> decSts = null;
	
	public NodeProgram(ArrayList<NodeDecSt> decSts) {
		this.decSts = decSts;
	}
	
	public ArrayList<NodeDecSt> getDecSts() {
		return this.decSts;
	}
	
	@Override
	public String toString() {
		return "Prg: "+getDecSts();
	}

}
