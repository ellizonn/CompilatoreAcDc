package ast;

import java.util.ArrayList;
import java.util.Iterator;

import visitor.IVisitor;

public class NodeProgram extends NodeAST implements Iterable<NodeDecSt> {
	
	private ArrayList<NodeDecSt> decSts = null;
	
	public NodeProgram(ArrayList<NodeDecSt> decSts) {
		this.decSts = decSts;
	}
	
	public ArrayList<NodeDecSt> getDecSts() {
		return this.decSts;
	}
	
	@Override
	public void accept(IVisitor visitor) {
		visitor.visit(this);
	}
	
	@Override
	public String toString() {
		return "Prg: "+getDecSts();
	}

	@Override
	public Iterator<NodeDecSt> iterator() {
		return this.decSts.iterator();
	}

}
