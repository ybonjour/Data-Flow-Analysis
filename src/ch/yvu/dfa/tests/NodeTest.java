package ch.yvu.dfa.tests;

import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;

import ch.yvu.dfa.controlflowgraph.ConditionalNode;
import ch.yvu.dfa.controlflowgraph.Node;
import ch.yvu.dfa.expressions.statements.*;

public class NodeTest {

	private Node node;
	
	@Before
	public void setUp(){
			this.node = new ConditionalNode(1, new Operation(new Variable("a"), new Variable("b"), "=="));
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void test_addChild_null(){
		this.node.addChild(null);
	}
	
	@Test
	public void test_addChild_non_existing_child(){
		//Arrange
		ConditionalNode child = new ConditionalNode(2, new Operation(new Variable("a"), new Variable("b"), "=="));
		
		//Act
		this.node.addChild(child);
		
		//Assert
		Assert.assertEquals(1, node.numChildren());
	}
	
	@Test
	public void test_addChild_existing_child(){
		//Arrange
		ConditionalNode child = new ConditionalNode(2,new Operation(new Variable("a"), new Variable("b"), "=="));
		this.node.addChild(child);
		
		//Act
		this.node.addChild(child);
		
		//Assert
		Assert.assertEquals(1, this.node.numChildren());
	}
}
