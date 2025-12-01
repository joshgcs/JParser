package evaluator;

import misc.EvalContext;
import misc.FunctionDefinition;
import misc.MathObject;
import nodes.*;

public class Evaluator {
    public MathObject evaluate(ExpressionNode node, EvalContext context) {
        if (node instanceof LiteralNode lit) {
            return new MathObject((double) lit.getValue());
        } else if (node instanceof VariableNode var) {
            if (!context.variables.containsKey(var.getName())) {
                return new MathObject(var.getName());
            }

            return new MathObject(context.variables.get(var.getName()));
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
                    childContext.variables.put(functionDefinition.getParameters().get(i), evaluate(funcCall.getArgs().get(i), context).getValue());
                }
                val += evaluate(functionDefinition.getBody(), childContext).getValue();
            } else if (context.containsNativeFunction(fname)) {
                double[] args = new double[funcCall.getArgs().size()];
                for (int i = 0; i < funcCall.getArgs().size(); i++) {
                    args[i] = evaluate(funcCall.getArgs().get(i), context).getValue();
                }
                return new MathObject(context.callNativeFunction(fname, args));
            } else {
                throw new RuntimeException("Unable to locate function " + funcCall.getName() + " in context");
            }
            return new MathObject(val);
        } else if (node instanceof UnaryNode un) {
            MathObject value = evaluate(un.getChild(), context);
            if (value.getName() == null) {
                return switch (un.getSymbol()) {
                    case NEGATIVE -> new MathObject(-1 * value.getValue());
                    case POSITIVE -> value;
                };
            } else {
                return switch (un.getSymbol()) {
                    case NEGATIVE -> new MathObject("-" + value.getName());
                    case POSITIVE -> value;
                };
            }
        } else if (node instanceof BinaryNode bin) {
            MathObject leftObj = evaluate(bin.getLeftChild(), context);
            MathObject rightObj = evaluate(bin.getRightChild(), context);

            if (leftObj.getName() != null || rightObj.getName() != null) {
                String op = operatorToString(bin.getOperator());
                String sym =  leftObj + " " + op + " " + rightObj;
                return new MathObject(sym);
            }

            double left = leftObj.getValue();
            double right = rightObj.getValue();

            return switch (bin.getOperator()) {
                case PLUS -> new MathObject(left + right);
                case MINUS -> new MathObject(left - right);
                case MULT -> new MathObject(left * right);
                case DIV -> new MathObject(left / right);
                case GT -> new MathObject((left > right ? 1 : 0));
                case LT -> new MathObject((left < right ? 1 : 0));
                case GTE -> new MathObject((left >= right ? 1 : 0));
                case LTE -> new MathObject((left <= right ? 1 : 0));
                case NEQ -> new MathObject((left != right ? 1 : 0));
                case FILLER -> new MathObject(0.0);
                case EQUAL -> new MathObject((left == right ? 1 : 0));
                case PEQUAL -> new MathObject(left + (left + right));
                case EXP -> new MathObject(evalExponent(left, right));
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

    private String operatorToString(Enum<?> op) {
        // Provide readable operator symbols for symbolic output.
        // Using Enum<?> because Operator enum is available at runtime; map common cases.
        String name = op.name();
        return switch (name) {
            case "PLUS" -> "+";
            case "MINUS" -> "-";
            case "MULT" -> "*";
            case "DIV" -> "/";
            case "EXP" -> "^";
            case "GT" -> ">";
            case "LT" -> "<";
            case "GTE" -> ">=";
            case "LTE" -> "<=";
            case "NEQ" -> "!=";
            case "EQUAL" -> "==";
            case "PEQUAL" -> "+=";
            default -> name;
        };
    }
}
