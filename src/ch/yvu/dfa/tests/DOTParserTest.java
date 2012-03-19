package ch.yvu.dfa.tests;

import junit.framework.Assert;

import org.junit.Test;

import ch.yvu.dfa.controlflowgraph.AssignmentNode;
import ch.yvu.dfa.controlflowgraph.ConditionalNode;
import ch.yvu.dfa.controlflowgraph.ControlflowGraph;
import ch.yvu.dfa.parser.FormatException;
import ch.yvu.dfa.parser.dot.SimpleDOTParser;
import ch.yvu.dfa.parser.expression.ExpressionParser;



public class DOTParserTest {
	
	@Test
	public void test_parse_empty(){
		//Arrange
		String input = "digraph Test{}";
		SimpleDOTParser parser = new SimpleDOTParser(input, new ExpressionParser());
		
		//Act
		ControlflowGraph graph;
		try{
			graph = parser.parse();
		}catch(FormatException e){
			throw new RuntimeException(e);
		}
		
		//Assert
		Assert.assertEquals(0, graph.getNodes().size());
	}
	
	@Test
	public void test_parse_assignment_node(){
		//Arrange
		String input = "digraph Test {1[label=\"x:=a+b\"]}";
		SimpleDOTParser parser = new SimpleDOTParser(input, new ExpressionParser());
		
		//Act
		ControlflowGraph graph;
		try{
			graph = parser.parse();
		}catch(FormatException e){
			throw new RuntimeException(e);
		}
		
		//Assert
		Assert.assertEquals(1, graph.getNodes().size());
		Assert.assertNotNull(graph.getNode(1));
		Assert.assertTrue(graph.getNode(1) instanceof AssignmentNode);
		AssignmentNode node = (AssignmentNode) graph.getNode(1);
		Assert.assertEquals("x", node.getLhs().getExpression());
		Assert.assertEquals("a + b", node.getRhs().getExpression());
	}
	
	@Test
	public void test_parse_assignment_node_with_numbering(){
		//Arrange
		String input = "digraph Test {1[label=\"1: x:=a+b\"]}";
		SimpleDOTParser parser = new SimpleDOTParser(input, new ExpressionParser());
		
		//Act
		ControlflowGraph graph;
		try{
			graph = parser.parse();
		}catch(FormatException e){
			throw new RuntimeException(e);
		}
		
		//Assert
		Assert.assertEquals(1, graph.getNodes().size());
		Assert.assertNotNull(graph.getNode(1));
		Assert.assertTrue(graph.getNode(1) instanceof AssignmentNode);
		AssignmentNode node = (AssignmentNode) graph.getNode(1);
		Assert.assertEquals("x", node.getLhs().getExpression());
		Assert.assertEquals("a + b", node.getRhs().getExpression());
	}
	
	@Test
	public void test_parse_conditional_node(){
		//Arrange
		String input = "digraph Test {1[label=\"x<a+b\"]}";
		SimpleDOTParser parser = new SimpleDOTParser(input, new ExpressionParser());
		
		//Act
		ControlflowGraph graph;
		try{
			graph = parser.parse();
		}catch(FormatException e){
			throw new RuntimeException(e);
		}
		
		//Assert
		Assert.assertEquals(1, graph.getNodes().size());
		Assert.assertNotNull(graph.getNode(1));
		Assert.assertTrue(graph.getNode(1) instanceof ConditionalNode);
		ConditionalNode node = (ConditionalNode) graph.getNode(1);
		Assert.assertEquals("x < a + b", node.getExpression().getExpression());
	}
	
	@Test
	public void test_parse_conditional_node_with_numbering(){
		//Arrange
		String input = "digraph Test {1[label=\"1: x<a+b\"]}";
		SimpleDOTParser parser = new SimpleDOTParser(input, new ExpressionParser());
		
		//Act
		ControlflowGraph graph;
		try{
			graph = parser.parse();
		}catch(FormatException e){
			throw new RuntimeException(e);
		}
		
		//Assert
		Assert.assertEquals(1, graph.getNodes().size());
		Assert.assertNotNull(graph.getNode(1));
		Assert.assertTrue(graph.getNode(1) instanceof ConditionalNode);
		ConditionalNode node = (ConditionalNode) graph.getNode(1);
		Assert.assertEquals("x < a + b", node.getExpression().getExpression());
	}
	
	@Test
	public void test_parse_nodes_without_label(){
		//Arrange
		String input = "digraph Test{1->2;}";
		SimpleDOTParser parser = new SimpleDOTParser(input, new ExpressionParser());
		
		//Act
		try{
			parser.parse();
		}catch(FormatException e){
			//Success
			return;
		}
		Assert.fail();
	}
	
	@Test
	public void test_parse_sequence(){
		//Arrange
		String input = "digraph Test {1[label=\"x:=a+b\"];2[label=\"y:=a*b\"];1->2;}";
		SimpleDOTParser parser = new SimpleDOTParser(input, new ExpressionParser());
		
		//Act
		ControlflowGraph graph;
		try{
			graph = parser.parse();
		}catch(FormatException e){
			throw new RuntimeException(e);
		}
		
		//Assert
		Assert.assertEquals(2, graph.getNodes().size());
		Assert.assertNotNull(graph.getNode(1));
		Assert.assertNotNull(graph.getNode(2));
		Assert.assertEquals(1, graph.getNode(1).numChildren());
		Assert.assertTrue(graph.getNode(1).hasChild(graph.getNode(2)));
	}
	
	@Test
	public void test_parse_branch(){
		//Arrange
		String input = "digraph Test {1[label=\"x<a+b\"];2[label=\"y:=a*b\"];3[label=\"y:=a+b\"];4[label=\"y:=2*y\"];1->2;2->4;1->3;3->4;}";
		SimpleDOTParser parser = new SimpleDOTParser(input, new ExpressionParser());
		
		//Act
		ControlflowGraph graph;
		try{
			graph = parser.parse();
		}catch(FormatException e){
			throw new RuntimeException(e);
		}
		
		//Assert
		Assert.assertEquals(4, graph.getNodes().size());
		
		Assert.assertNotNull(graph.getNode(1));
		Assert.assertNotNull(graph.getNode(2));
		Assert.assertNotNull(graph.getNode(3));
		Assert.assertNotNull(graph.getNode(4));
		
		Assert.assertEquals(2, graph.getNode(1).numChildren());
		Assert.assertTrue(graph.getNode(1).hasChild(graph.getNode(2)));
		Assert.assertTrue(graph.getNode(1).hasChild(graph.getNode(3)));
		
		Assert.assertEquals(1, graph.getNode(2).numChildren());
		Assert.assertTrue(graph.getNode(2).hasChild(graph.getNode(4)));
		
		Assert.assertEquals(1, graph.getNode(3).numChildren());
		Assert.assertTrue(graph.getNode(3).hasChild(graph.getNode(4)));
	}
	
	@Test
	public void test_parse_loop(){
		//Arrange
		String input = "digraph Test {1[label=\"x<a+b\"];2[label=\"y:=a*b\"];3[label=\"y:=a+b\"];1->2;1->3;2->1;}";
		SimpleDOTParser parser = new SimpleDOTParser(input, new ExpressionParser());
		
		//Act
		ControlflowGraph graph;
		try{
			graph = parser.parse();
		}catch(FormatException e){
			throw new RuntimeException(e);
		}
		
		//Assert
		Assert.assertEquals(3, graph.getNodes().size());
		
		Assert.assertNotNull(graph.getNode(1));
		Assert.assertNotNull(graph.getNode(2));
		Assert.assertNotNull(graph.getNode(3));
		
		Assert.assertEquals(2, graph.getNode(1).numChildren());
		Assert.assertTrue(graph.getNode(1).hasChild(graph.getNode(2)));
		Assert.assertTrue(graph.getNode(1).hasChild(graph.getNode(3)));
		
		Assert.assertEquals(1, graph.getNode(2).numChildren());
		Assert.assertTrue(graph.getNode(2).hasChild(graph.getNode(1)));
	}
	
}
