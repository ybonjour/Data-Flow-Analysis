package ch.yvu.dfa.controlflowgraph;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import ch.yvu.dfa.analysis.AnalysisStrategy;
import ch.yvu.dfa.expressions.Expression;

public abstract class Node implements Comparable<Node> {
	private List<Node> children;
	private int id;
	
	public Node(int id){
		this.children = new ArrayList<Node>();
		this.id = id;
	}
	
	public void addChild(Node n){
		if(n == null) throw new IllegalArgumentException();
		if(this.children.contains(n)) return;
		
		this.children.add(n);
	}
	
	public boolean hasChild(Node node){
		return this.children.contains(node);
	}
	
	public int numChildren(){
		return this.children.size();
	}
	
	public Iterator<Node> childrenIterator(){
		return this.children.iterator();
	}
	
	public int getId(){
		return id;
	}
	
	public String toString(){
		return  this.id + ": " + getStatement();
	}
	
	@Override
	public int compareTo(Node node) {
		return this.getId() - node.getId();
	}
	
	public abstract Expression getExpression();
	public abstract String getStatement();
	public abstract void applyStatement(Set<Expression> incoming, Set<Expression> outgoing, AnalysisStrategy strategy);
}