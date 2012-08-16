package ch.yvu.dfa.analysis;

import java.util.Set;

import ch.yvu.dfa.controlflowgraph.ConditionalNode;
import ch.yvu.dfa.expressions.Expression;
import ch.yvu.dfa.expressions.NumberTop;
import ch.yvu.dfa.expressions.Variable;
import ch.yvu.dfa.expressions.StateExpression;

public class PreciseConstantPropagationStrategy extends ConstantPropagationStrategy {
//	private Set<Variable> internalConditionalVariables;
//	
//	public PreciseConstantPropagationStrategy(){
//		this.internalConditionalVariables = new HashSet<Variable>();
//	}
	
	@Override
	public void applyStatement(ConditionalNode node, Set<Expression> incoming,
			Set<Expression> outgoing) {
		
		//for constant propagation only one Expression can be tracked (state)
		if(incoming.size() != 1) throw new RuntimeException("More than one expression");
		
		StateExpression incomingStateExpression = (StateExpression) incoming.toArray()[0]; 
		State state = new State(incomingStateExpression);
		
		StateExpression outgoingStateExpresssion;
		NumberTop nt = node.getExpression().evaluate(state);
		if(nt != null){
			Variable conditionalVariable = new Variable("b" + node.getId());
			state.setVariable(conditionalVariable, nt);
			outgoingStateExpresssion = state.createStateExpression();
		} else {
			outgoingStateExpresssion = StateExpression.invalidSateExpression();
		}
		
		outgoing.clear();
		outgoing.add(outgoingStateExpresssion);
	}

}
