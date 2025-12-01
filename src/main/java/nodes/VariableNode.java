package nodes;

import tokenizer.Token;

public class VariableNode extends ExpressionNode{
    private String name;

    public VariableNode(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public NodeType getNodeType() {
        return NodeType.VARIABLE;
    }

    @Override
    public Object getValue() {
        return this.name;
    }
}
