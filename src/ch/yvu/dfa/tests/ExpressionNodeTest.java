package ch.yvu.dfa.tests;

import java.util.Set;

import junit.framework.Assert;

import org.junit.Test;

import ch.yvu.dfa.expressions.statements.Expression;
import ch.yvu.dfa.expressions.statements.Number;
import ch.yvu.dfa.expressions.statements.Operation;
import ch.yvu.dfa.expressions.statements.Variable;

public class ExpressionNodeTest {

	@Test
	public void test_containsVariable_variable(){
		//Arrange
		Expression node = new Variable("a");
		
		//Act
		boolean contains = node.containsVariable("a");
		
		//Assert
		Assert.assertTrue(contains);
	}
	
	@Test
	public void test_containsVariable_false(){
		//Arrange
		Expression node = new Variable("a");
		
		//Act
		boolean contains = node.containsVariable("b");
		
		//Assert
		Assert.assertFalse(contains);
	}
	
	@Test
	public void test_containsVariable_numer(){
		//Arrange
		Expression node = new Number(0);
		
		//Act
		boolean contains = node.containsVariable("0");
		
		//Assert
		Assert.assertFalse(contains);
	}
	
	@Test
	public void test_containsVariable_operation_lhs(){
		//Arrange
		Expression node = new Operation(new Variable("a"), new Number(0), null);
		
		//Act
		boolean contains = node.containsVariable("a");
		
		//Assert
		Assert.assertTrue(contains);
	}
	
	@Test
	public void test_containsVariable_operation_rhs(){
		//Arrange
		Expression node = new Operation(new Number(0), new Variable("a"), null);
		
		//Act
		boolean contains = node.containsVariable("a");
		
		//Assert
		Assert.assertTrue(contains);
	}
	
	@Test
	public void test_containsVariable_operation_false(){
		//Arrange
		Expression node = new Operation(new Variable("b"), new Variable("a"), null);
		
		//Act
		boolean contains = node.containsVariable("c");
		
		//Assert
		Assert.assertFalse(contains);
	}
	
	@Test
	public void test_getFreeVariables_variable(){
		//Arrange
		Variable variable = new Variable("a");
		
		//Act
		Set<Variable> fv = variable.getFreeVariables();
		
		//Assert
		Assert.assertEquals(1, fv.size());
		Assert.assertTrue(fv.contains(variable));
	}
	
	@Test
	public void test_getFreeVariables_number(){
		//Arrange
		Expression node = new Number(0);
		
		//Act
		Set<Variable> fv = node.getFreeVariables();
		
		//Assert
		Assert.assertEquals(0, fv.size());
	}
	
	@Test
	public void test_getFreeVariables_operation_lhs(){
		//Arrange
		Variable a = new Variable("a");
		Expression node = new Operation(a, new Number(0), null);
		
		//Act
		Set<Variable> fv = node.getFreeVariables();
		
		//Assert
		Assert.assertEquals(1, fv.size());
		Assert.assertTrue(fv.contains(a));
	}
	
	@Test
	public void test_getFreeVariables_operation_rhs(){
		//Arrange
		Variable a = new Variable("a");
		Expression node = new Operation(new Number(0), a, null);
		
		//Act
		Set<Variable> fv = node.getFreeVariables();
		
		//Assert
		Assert.assertEquals(1, fv.size());
		Assert.assertTrue(fv.contains(a));
	}
	
	@Test
	public void test_getFreeVariables_operation_none(){
		//Arrange
		Expression node = new Operation(new Number(0), new Number(1), null);
		
		//Act
		Set<Variable> fv = node.getFreeVariables();
		
		//Assert
		Assert.assertEquals(0, fv.size());
	}
	
	@Test
	public void test_getFreeVariables_operation_both(){
		//Arrange
		Variable a = new Variable("a");
		Variable b = new Variable("b");
		Expression node = new Operation(a, b, null);
		
		//Act
		Set<Variable> fv = node.getFreeVariables();
		
		//Assert
		Assert.assertEquals(2, fv.size());
		Assert.assertTrue(fv.contains(a));
		Assert.assertTrue(fv.contains(b));
	}
	
	
}
