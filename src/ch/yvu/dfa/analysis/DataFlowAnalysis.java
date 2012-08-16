package ch.yvu.dfa.analysis;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import ch.yvu.dfa.controlflowgraph.ControlflowGraph;
import ch.yvu.dfa.controlflowgraph.Node;
import ch.yvu.dfa.expressions.Expression;

import org.apache.commons.lang3.StringEscapeUtils;

public class DataFlowAnalysis {
	private Map<Node, Set<Expression>> incoming;
	private Map<Node, Set<Expression>> outgoing;
	private ControlflowGraph graph;
	private AnalysisStrategy strategy;
	
	
	public DataFlowAnalysis(ControlflowGraph graph, AnalysisStrategy strategy){
		this.incoming = new HashMap<Node, Set<Expression>>();
		this.outgoing = new HashMap<Node, Set<Expression>>();
		this.graph = graph;
		this.strategy = strategy;
	}
	
	private void initializeAllOutgoing(){
		Set<Expression> initSet = this.strategy.getInitializationOutgoing(this.graph);
		for(Node node : graph.getNodes()){
			this.outgoing.put(node, new HashSet<Expression>(initSet));
		}
	}
		
	public String analyse(){
		this.outgoing.clear();
		this.incoming.clear();
		
		initializeAllOutgoing();
		
		int initId;
		try
		{
			initId = this.strategy.getIdOfInitialNode(this.graph);
		}catch(Exception e){
			throw new RuntimeException(e);
		}
		
		Set<Node> changed = new HashSet<Node>(graph.getNodes());
		Node initialNode = graph.getNode(initId);
		Set<Expression> initialSetIncoming = this.strategy.getInitializationIncoming(this.graph);
		this.incoming.put(initialNode, initialSetIncoming);
		this.strategy.applyStatement(initialNode, this.incoming.get(initialNode), this.outgoing.get(initialNode));
		changed.remove(initialNode);
		
		while(!changed.isEmpty()){
			Node currentNode = getAnyNode(changed);
			assert(currentNode != null);
			changed.remove(currentNode);
			
			calculateIncoming(currentNode);
			
			Set<Expression> oldOutgoing = new HashSet<Expression>(this.outgoing.get(currentNode));
			this.strategy.applyStatement(currentNode, this.incoming.get(currentNode), this.outgoing.get(currentNode));

			if(!this.outgoing.get(currentNode).equals(oldOutgoing)){
				changed.addAll(this.strategy.getNodesToRecalculate(currentNode, this.graph));
			}
		}
		
		return getExpressionsHTML();
	}
	
	private void calculateIncoming(Node currentNode){
		if(this.incoming.get(currentNode) == null){
			this.incoming.put(currentNode, new HashSet<Expression>());
		}
		this.strategy.calculateIncoming(currentNode, this.incoming.get(currentNode), this.outgoing, this.graph);
	}
	
	private String setToString(Set<Expression> set){
		String output = "{";
		boolean first = true;
		for(Expression expr : set){
			if(!first){
				output += ", ";
			}
			
			output += expr.getExpression();
			
			first = false;
		}
		
		return output + "}";
	}
	
	private String getExpressionsHTML(){
		SortedSet<Node> sortedNodes = new TreeSet<Node>();
		sortedNodes.addAll(this.graph.getNodes());
			
		String output = "<table>";
		
		for(Node node : sortedNodes){
			output += "<tr><td>" + node.getId() + "</td><td>" + StringEscapeUtils.escapeHtml4(node.getStatement()) + "</td><td>";
			output += StringEscapeUtils.escapeHtml4(setToString(this.strategy.selectEntry(this.outgoing.get(node), this.incoming.get(node))));
			output += "</td><td>";
			output += StringEscapeUtils.escapeHtml4(setToString(this.strategy.selectExit(this.outgoing.get(node), this.incoming.get(node))));
			output += "</td></tr>";
		}
		
		output += "</table>";
		return output;
	}
	
	private static Node getAnyNode(Set<Node> nodes){
		Iterator<Node> it = nodes.iterator();
		return it.hasNext() ? it.next() : null;
	}
}