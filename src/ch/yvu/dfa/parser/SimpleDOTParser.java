package ch.yvu.dfa.parser;

import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ch.yvu.dfa.controlflowgraph.ControlflowGraph;

public class SimpleDOTParser {

	private final static String ASSIGNMENT_OPERATOR = ":=";
	
	private String input;	
	private ControlflowGraph graph;
	
	public SimpleDOTParser(String input){
		this.input = input;
		this.graph = new ControlflowGraph();
	}
	
	public ControlflowGraph parse() throws FormatException{
		String trimmedInput = this.input.replaceAll("\\s","");
		String edgeInput = extractEdgeInput(trimmedInput);
		
		StringTokenizer tokens = new StringTokenizer(edgeInput, ";");
		Pattern edgePattern = Pattern.compile("([0-9]+)->([0-9]+)");
		Pattern nodePattern = Pattern.compile("([0-9]+)\\[label=\"([0-9]+:)?([^\"]+)\"\\]");
		while(tokens.hasMoreElements()){
			String token = tokens.nextToken();
			Matcher edgeMatcher = edgePattern.matcher(token);
			Matcher nodeMatcher = nodePattern.matcher(token);
			if(nodeMatcher.find()){
				int nodeId = Integer.parseInt(nodeMatcher.group(1));
				String expression = nodeMatcher.group(3);
				createNode(nodeId, expression);
			} else if(edgeMatcher.find()){
				int nodeFromId = Integer.parseInt(edgeMatcher.group(1));
				int nodeToId = Integer.parseInt(edgeMatcher.group(2));
				createEdge(nodeFromId, nodeToId);
			} else {
				//If format not recognize don't consider it for
				//controlflow graph but don't throw an execption
				//because only a subset of DOT language is supported
				//(unrecognized tokens can still be valid DOT)
			}
		}
		return this.graph;
	}
	
	private void createNode(int id, String expression) throws FormatException {
		if(expression.contains(ASSIGNMENT_OPERATOR)){
			String[] tokens = expression.split(ASSIGNMENT_OPERATOR);
			if(tokens.length != 2) throw new FormatException();
			try{
				Set<String> freeVariablesLhs = getFreeVariables(tokens[0]);
				Set<String> freeVariablesRhs = getFreeVariables(tokens[1]);
				this.graph.createAssignmentNode(id, tokens[0], tokens[1], freeVariablesLhs, freeVariablesRhs);
			} catch(Exception e){
				throw new FormatException(e);
			}
		} else {
			try{
				Set<String> freeVariables = getFreeVariables(expression);
				this.graph.createConditionalNode(id, expression, freeVariables);
			} catch(Exception e){
				throw new FormatException(e);
			}
		}
	}

	private void createEdge(int nodeFromId, int nodeToId) throws FormatException{
		try {
			this.graph.addEdge(nodeFromId, nodeToId);
		} catch (Exception e) {
			throw new FormatException(e);
		}
	}
	
	/**
	 * Extract <node> part from 
	 * digraphgraphName{<node>}
	 */
	private static String extractEdgeInput(String input) throws FormatException{
		Pattern blockPattern = Pattern.compile("[^\\{]*\\{([^\\}]*)\\}");
		Matcher blockMatcher = blockPattern.matcher(input);
		
		if(!blockMatcher.find()) throw new FormatException();
		
		return blockMatcher.group(1);
	}
	
	private Set<String> getFreeVariables(String input){
		Set<String> freeVariables = new HashSet<String>(); 
		Pattern fvPattern = Pattern.compile("(([a-zA-Z])([^a-zA-Z]+)?)");
		Matcher matcher = fvPattern.matcher(input);
		while(matcher.find()){
			freeVariables.add(matcher.group(2));
		}
		
		return freeVariables;
	}
	
}
