package ch.yvu.dfa.parser.expression;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ch.yvu.dfa.expressions.Expression;
import ch.yvu.dfa.expressions.Number;
import ch.yvu.dfa.expressions.Operation;
import ch.yvu.dfa.expressions.Variable;
import ch.yvu.dfa.parser.FormatException;

public class ExpressionParser {
	
	public Expression parse(String input) throws FormatException{
		String trimmedInput = input.replaceAll("\\s","");
		//Don't consider brackets
		trimmedInput = removeBrackets(trimmedInput);
		
		Pattern composedExpressionPattern = Pattern.compile("([a-zA-Z0-9]+)([^a-zA-Z0-9]{1,2})(.+)");
		Matcher composedExpressionMatcher = composedExpressionPattern.matcher(trimmedInput);
		
		Pattern simpleExpressionPattern = Pattern.compile("([a-zA-Z0-9]+)");
		Matcher simpleExpressionMatcher = simpleExpressionPattern.matcher(trimmedInput);	
		
		if(composedExpressionMatcher.find()){
			String lhs = composedExpressionMatcher.group(1);
			
			Expression lhsExpression = parse(lhs);
			
			String operation = composedExpressionMatcher.group(2);
			
			String rhs = composedExpressionMatcher.group(3);
			Expression rhsExpression = parse(rhs);
			
			return new Operation(lhsExpression, rhsExpression, operation);
		} else if(simpleExpressionMatcher.find()){
			return createLeafExpression(simpleExpressionMatcher.group(1));
		} else {
			throw new FormatException();
		}
	}
	
	private String removeBrackets(String trimmedInput) {
		trimmedInput = trimmedInput.replaceAll("\\(", "");
		trimmedInput = trimmedInput.replaceAll("\\)", "");
		return trimmedInput;
	}

	private Expression createLeafExpression(String expression) throws FormatException{
		if(isVariable(expression)){
			return new Variable(expression);
		} else if(isNumber(expression)){
			return new Number(Integer.parseInt(expression));
		} else {
			throw new FormatException();
		}
	}
	
	private boolean isVariable(String input){
		return input.matches("[a-zA-z]+");
	}
	
	private boolean isNumber(String input){
		return input.matches("[0-9]+");
	}
}
