package ch.yvu.dfa.tests;

import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;

import ch.yvu.dfa.controlflowgraph.ConditionalNode;
import ch.yvu.dfa.controlflowgraph.Node;

public class NodeTest {

	private Node node;
	
	@Before
	public void setUp(){
		this.node = new ConditionalNode(1, "a==b", null);;
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void test_addChild_null(){
		this.node.addChild(null);
	}
	
	@Test
	public void test_addChild_non_existing_child(){
		//Arrange
		ConditionalNode child = new ConditionalNode(2, "a==b", null);
		
		//Act
		this.node.addChild(child);
		
		//Assert
		Assert.assertEquals(1, node.numChildren());
	}
	
	@Test
	public void test_addChild_existing_child(){
		//Arrange
		ConditionalNode child = new ConditionalNode(2, "a==b", null);
		this.node.addChild(child);
		
		//Act
		this.node.addChild(child);
		
		//Assert
		Assert.assertEquals(1, this.node.numChildren());
	}
}
