package misc;

import nodes.ExpressionNode;
import nodes.FunctionDefinitionNode;
import parser.Parser;
import tokenizer.Tokenizer;

import java.util.*;
import java.util.function.Function;

public class EvalContext {
    public Map<String, Double> variables = new HashMap<>()
    {{
        put("e", 2.718281828459045235360287471352);
        put("pi", 3.1415926535897932384626433);
    }};

    public List<String> nativeVariables = new ArrayList<>();
    public Map<String, FunctionDefinition> functions = new HashMap<>();
    public Map<String, Function<double[], Double>> nativeFunctions = new HashMap<>() {{
        put("cos", args -> Math.cos(args[0]));
        put("sin", args -> Math.sin(args[0]));
        put("tan", args -> Math.tan(args[0]));
        put("tanh", args -> Math.tanh(args[0]));
        put("sinh", args -> Math.sinh(args[0]));
        put("cosh", args -> Math.cosh(args[0]));
        put("asin", args -> Math.asin(args[0]));
        put("acos", args -> Math.acos(args[0]));
        put("atan", args -> Math.atan(args[0]));
        put("cbrt", args -> Math.cbrt(args[0]));
        put("sqrt", args -> Math.sqrt(args[0]));
        put("abs", args -> Math.abs(args[0]));
        put("ln", args -> Math.log(args[0]));
        put("log", args -> Math.log10(args[0]));
        put("int", args -> EvalContext.integral(args[0], args[1]));
    }};
    public EvalContext parent;

    public EvalContext() {}

    public EvalContext(EvalContext parent) {
        this.parent = parent;
        for (FunctionDefinition functionDefinition : parent.functions.values()) {
            this.functions.put(functionDefinition.getName(), functionDefinition);
        }
        this.nativeFunctions.putAll(parent.nativeFunctions);
    }

    public FunctionDefinition addFunction(String func) {
        Tokenizer tokenizer = new Tokenizer(func);
        Parser parser = new Parser(tokenizer.tokenize());
        ExpressionNode root = parser.parseExpression();
        if (root instanceof FunctionDefinitionNode functionDefinitionNode) {
            if (functions.containsKey((String) functionDefinitionNode.getValue())) {
                throw new RuntimeException("Function " + functionDefinitionNode.getValue() + " already exists in context");
            }
            FunctionDefinition def = defineFunction(functionDefinitionNode);
            def.setExpression(func);
            functions.put(def.getName(), def);
            return def;
        }
        throw new RuntimeException("Unable to parse function " + func);
    }

    private FunctionDefinition defineFunction(FunctionDefinitionNode def) {
        return new FunctionDefinition((String) def.getValue(), def.getParams(), def.getBody());
    }

    public FunctionDefinition lookupFunction(String name) {
        return functions.get(name);
    }

    public boolean containsFunction(String name) {
        return functions.containsKey(name);
    }

    public boolean containsNativeFunction(String name) {
        return nativeFunctions.containsKey(name);
    }

    public Double callNativeFunction(String name, double[] args) {
        Function<double[], Double> f = nativeFunctions.get(name);

        return f.apply(args);
    }

    public static double integral(double upper, double lower) {
        return 0.0;
    }
}