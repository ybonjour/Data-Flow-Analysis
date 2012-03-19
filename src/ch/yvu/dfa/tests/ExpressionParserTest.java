package ch.yvu.dfa.tests;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import ch.yvu.dfa.expressions.statements.Expression;
import ch.yvu.dfa.expressions.statements.Number;
import ch.yvu.dfa.expressions.statements.Operation;
import ch.yvu.dfa.expressions.statements.Variable;
import ch.yvu.dfa.parser.FormatException;
import ch.yvu.dfa.parser.expression.ExpressionParser;

public class ExpressionParserTest {

	private ExpressionParser parser;
	
	@Before
	public void setUp(){
		this.parser = new ExpressionParser();
	}
	
	@Test
	public void test_parse_simple_expression_variable(){
		//Arrange
		String input = "a";
		
		//Act
		Expression node;
		try{
			 node = parser.parse(input);
		}catch(FormatException e){
			throw new RuntimeException(e);
		}
		
		//Assert
		Assert.assertNotNull(node);
		Assert.assertTrue(node instanceof Variable);
		Assert.assertEquals("a", node.getExpression());
	}
	
	@Test
	public void test_parse_simple_expression_variable_multiple_chars(){
		//Arrange
		String input = "aVariable";
		
		//Act
		Expression node;
		try{
			 node = parser.parse(input);
		}catch(FormatException e){
			throw new RuntimeException(e);
		}
		
		//Assert
		Assert.assertNotNull(node);
		Assert.assertTrue(node instanceof Variable);
		Assert.assertEquals("aVariable", node.getExpression());
	}
	
	@Test
	public void test_parse_simple_expression_number(){
		//Arrange
		String input = "0";
		
		//Act
		Expression node;
		try{
			 node = parser.parse(input);
		}catch(FormatException e){
			throw new RuntimeException(e);
		}
		
		//Assert
		Assert.assertNotNull(node);
		Assert.assertTrue(node instanceof Number);
		Assert.assertEquals("0", node.getExpression());
	}
	
	@Test
	public void test_parse_composed_expression_add_two_variables(){
		//Arrange
		String input = "a + b";
		
		//Act
		Expression node;
		try{
			 node = parser.parse(input);
		}catch(FormatException e){
			throw new RuntimeException(e);
		}
		
		//Assert
		Assert.assertNotNull(node);
		Assert.assertTrue(node instanceof Operation);
		Assert.assertEquals("a + b", node.getExpression());
	}
	
	@Test
	public void test_parse_composed_expression_add_variable_number(){
		//Arrange
		String input = "a + 10";
		
		//Act
		Expression node;
		try{
			 node = parser.parse(input);
		}catch(FormatException e){
			throw new RuntimeException(e);
		}
		
		//Assert
		Assert.assertNotNull(node);
		Assert.assertTrue(node instanceof Operation);
		Assert.assertEquals("a + 10", node.getExpression());
	}
	
	@Test
	public void test_parse_composed_expression_add_number_variable(){
		//Arrange
		String input = "10 + a";
		
		//Act
		Expression node;
		try{
			 node = parser.parse(input);
		}catch(FormatException e){
			throw new RuntimeException(e);
		}
		
		//Assert
		Assert.assertNotNull(node);
		Assert.assertTrue(node instanceof Operation);
		Assert.assertEquals("10 + a", node.getExpression());
	}
	
	@Test
	public void test_parse_composed_expression_compare_two_chars(){
		//Arrange
		String input = "10 <= a";
		
		//Act
		Expression node;
		try{
			 node = parser.parse(input);
		}catch(FormatException e){
			throw new RuntimeException(e);
		}
		
		//Assert
		Assert.assertNotNull(node);
		Assert.assertTrue(node instanceof Operation);
		Assert.assertEquals("10 <= a", node.getExpression());
	}
}
