package misc;

import nodes.ExpressionNode;
import nodes.FunctionDefinitionNode;
import parser.Parser;
import tokenizer.Tokenizer;

import java.util.*;

public class EvalContext {
    public Map<String, Double> variables = new HashMap<>();
    public Map<String, FunctionDefinition> functions = new HashMap<>();
    public EvalContext parent;

    public EvalContext() {}

    public EvalContext(EvalContext parent) {
        this.parent = parent;
        for (FunctionDefinition functionDefinition : parent.functions.values()) {
            this.functions.put(functionDefinition.getName(), functionDefinition);
        }
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
}