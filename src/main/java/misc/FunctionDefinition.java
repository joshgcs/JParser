package misc;

import nodes.ExpressionNode;

import java.util.List;

public class FunctionDefinition {
    private String expression;
    private String name;
    private List<String> params;
    private ExpressionNode body;

    public FunctionDefinition(String name, List<String> params, ExpressionNode body) {
        this.name = name;
        this.params = params;
        this.body = body;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

    public String getExpression() {
        return expression;
    }

    public ExpressionNode getBody() {
        return body;
    }

    public String getName() {
        return name;
    }

    public List<String> getParameters() {
        return params;
    }
}
