package ch.yvu.dfa.controlflowgraph;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import ch.yvu.dfa.expressions.statements.Expression;
import ch.yvu.dfa.expressions.statements.Variable;

public class ControlflowGraph {

	private Map<Integer, Node> nodes;
	
	public ControlflowGraph(){
		this.nodes = new HashMap<Integer, Node>();
	}
	
	public Node getNode(int id){
		return this.nodes.get(id);
	}
	
	public AssignmentNode createAssignmentNode(int id, Variable lhs, Expression rhs) throws Exception{
		if(this.nodes.containsKey(id)) throw new Exception("Node already exists");
		
		AssignmentNode node = new AssignmentNode(id, lhs, rhs);
		this.nodes.put(id, node);
		
		return node;		
	}
	
	public ConditionalNode createConditionalNode(int id, Expression expression) throws Exception {
		if(this.nodes.containsKey(id)) throw new Exception("Node already exists");
		
		ConditionalNode node = new ConditionalNode(id, expression);
		this.nodes.put(id, node);
		return node;
	}
	
	public void addEdge(int fromId, int toId) throws Exception{
		if(!this.nodes.containsKey(fromId) || !this.nodes.containsKey(toId)) throw new Exception(fromId + " or " + toId + " does not exist");
		
		this.nodes.get(fromId).addChild(this.nodes.get(toId));
	}
	
	public int numChildren(Node node){
		return node.numChildren();
	}
	
	public Set<Node> getNodes(){
		Set<Node> nodeSet = new HashSet<Node>();
		for(Node node : this.nodes.values()){
			nodeSet.add(node);
		}
		return nodeSet;
	}
	
	public Set<Node> getParents(Node node){
		Set<Node> parents = new HashSet<Node>();
		for(Node paretnCandidate : this.nodes.values()){
			if(paretnCandidate.hasChild(node)){
				parents.add(paretnCandidate);
			}
		}
		return parents;
	}

	public int getMaxId() {
		int maxId = -1;
		for(int key : this.nodes.keySet()){
			if(key > maxId){
				maxId = key;
			}
		}
		
		return maxId;
	}
}
