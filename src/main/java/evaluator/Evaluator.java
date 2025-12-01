package evaluator;

import misc.EvalContext;
import misc.FunctionDefinition;
import nodes.*;

import java.util.*;

public class Evaluator {
    public double evaluate(ExpressionNode node, EvalContext context) {
        if (node instanceof LiteralNode lit) {
            return (double) lit.getValue();
        } else if (node instanceof VariableNode var) {
            if (!context.variables.containsKey(var.getName())) {
                if (!context.nativeVariables.contains(var.getName())) {
                    throw new RuntimeException("Unknown variable: " + var.getName());
                } else {

                }
            }
            return context.variables.get(var.getName());
        } else if (node instanceof FunctionCallNode funcCall) {
            String fname = funcCall.getName();
            double val = 0.0;
            if (context.containsFunction(funcCall.getName())) {
                EvalContext childContext = new EvalContext(context);
                FunctionDefinition functionDefinition = context.lookupFunction(funcCall.getName());
                if (functionDefinition.getParameters().size() != funcCall.getArgs().size()) {
                    throw new RuntimeException("Invalid number of parameters in function " + functionDefinition.getName());
                }
                for (int i = 0; i < funcCall.getArgs().size(); i++) {
                    childContext.variables.put(functionDefinition.getParameters().get(i), evaluate(funcCall.getArgs().get(i), context));
                }
                val += evaluate(functionDefinition.getBody(), childContext);
            } else if (context.containsNativeFunction(fname)) {
                double[] args = new double[funcCall.getArgs().size()];
                for (int i = 0; i < funcCall.getArgs().size(); i++) {
                    args[i] = evaluate(funcCall.getArgs().get(i), context);
                }
                return context.callNativeFunction(fname, args);
            } else {
                throw new RuntimeException("Unable to locate function " + funcCall.getName() + " in context");
            }
            return val;
        } else if (node instanceof UnaryNode un) {
            double value = evaluate(un.getChild(), context);
            return switch (un.getSymbol()) {
                case NEGATIVE -> -value;
                case POSITIVE -> value;
            };
        } else if (node instanceof BinaryNode bin) {
            double left = evaluate(bin.getLeftChild(), context);
            double right = evaluate(bin.getRightChild(), context);
            return switch (bin.getOperator()) {
                case PLUS -> left + right;
                case MINUS -> left - right;
                case MULT -> left * right;
                case DIV -> left / right;
                case GT -> (left > right ? 1 : 0);
                case LT -> (left < right ? 1 : 0);
                case GTE -> (left >= right ? 1 : 0);
                case LTE -> (left <= right ? 1 : 0);
                case NEQ -> (left != right ? 1 : 0);
                case FILLER -> 0.0;
                case EQUAL -> (left == right ? 1 : 0);
                case PEQUAL -> (left + (left + right));
                case EXP -> evalExponent(left, right);
            };
        } else {
            throw new RuntimeException("Unknown node type " + node);
        }
    }

    private double evalExponent(double left, double right) {
        double val = left;
        for (int i = 1; i < right; i++) {
            val *= left;
        }
        return val;
    }
}
