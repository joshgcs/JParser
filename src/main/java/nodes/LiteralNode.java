package nodes;

import tokenizer.Token;

public class LiteralNode extends ExpressionNode{
    private double value;

    public LiteralNode(double value) {
        this.value = value;
    }

    @Override
    public Object getValue() {
        return value;
    }

    @Override
    public NodeType getNodeType() {
        return NodeType.NUMBER;
    }
}
