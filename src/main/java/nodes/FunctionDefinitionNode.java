package nodes;

import java.util.List;

public class FunctionDefinitionNode extends ExpressionNode {

    private String name;
    private List<String> params;
    private ExpressionNode body;

    public FunctionDefinitionNode(String name, List<String> params, ExpressionNode body) {
        this.name = name;
        this.params = params;
        this.body = body;
    }

    public ExpressionNode getBody() {
        return body;
    }

    public List<String> getParams() {
        return params;
    }

    @Override
    public NodeType getNodeType() {
        return NodeType.FUNCTION_DEF;
    }

    @Override
    public Object getValue() {
        return name;
    }
}
