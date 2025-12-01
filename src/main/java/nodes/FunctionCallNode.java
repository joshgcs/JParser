package nodes;


import java.util.List;

public class FunctionCallNode extends ExpressionNode {

    private String name;
    private List<ExpressionNode> args;

    public FunctionCallNode(String name, List<ExpressionNode> args) {
        this.name = name;
        this.args = args;
    }

    public String getName() {
        return name;
    }

    public List<ExpressionNode> getArgs() {
        return args;
    }

    @Override
    public NodeType getNodeType() {
        return NodeType.FUNCTION_CALL;
    }

    @Override
    public Object getValue() {
        return this.name;
    }
}
