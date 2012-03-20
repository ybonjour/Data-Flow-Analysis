package ch.yvu.dfa.tests;

import java.util.Set;

import junit.framework.Assert;

import org.junit.Test;

import ch.yvu.dfa.expressions.Expression;
import ch.yvu.dfa.expressions.Number;
import ch.yvu.dfa.expressions.Operation;
import ch.yvu.dfa.expressions.Variable;

public class ExpressionNodeTest {

	@Test
	public void test_containsVariable_variable(){
		//Arrange
		Variable a = new Variable("a");
		
		//Act
		boolean contains = a.containsVariable(a);
		
		//Assert
		Assert.assertTrue(contains);
	}
	
	@Test
	public void test_containsVariable_different_variables_same_mane(){
		//Arrange
		Variable a1 = new Variable("a");
		Variable a2 = new Variable("a");
		
		//Act
		boolean contains = a1.containsVariable(a2);
		
		//Assert
		Assert.assertTrue(contains);
	}
	
	@Test
	public void test_containsVariable_false(){
		//Arrange
		Variable a = new Variable("a");
		
		//Act
		boolean contains = a.containsVariable(new Variable("b"));
		
		//Assert
		Assert.assertFalse(contains);
	}
	
	@Test
	public void test_containsVariable_operation_lhs(){
		//Arrange
		Expression node = new Operation(new Variable("a"), new Number(0), null);
		
		//Act
		boolean contains = node.containsVariable(new Variable("a"));
		
		//Assert
		Assert.assertTrue(contains);
	}
	
	@Test
	public void test_containsVariable_operation_rhs(){
		//Arrange
		Expression node = new Operation(new Number(0), new Variable("a"), null);
		
		//Act
		boolean contains = node.containsVariable(new Variable("a"));
		
		//Assert
		Assert.assertTrue(contains);
	}
	
	@Test
	public void test_containsVariable_operation_false(){
		//Arrange
		Expression node = new Operation(new Variable("b"), new Variable("a"), null);
		
		//Act
		boolean contains = node.containsVariable(new Variable("c"));
		
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
