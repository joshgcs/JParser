package nodes;

import tokenizer.Token;

public class UnaryNode extends ExpressionNode{

    private ExpressionNode child;
    private UnarySymbol symbol;

    public enum UnarySymbol {
        POSITIVE,
        NEGATIVE
    }

    public UnaryNode(UnarySymbol symbol, ExpressionNode child) {
        this.child = child;
        this.symbol = symbol;
    }

    public ExpressionNode getChild() {
        return child;
    }

    public UnarySymbol getSymbol() {
        return this.symbol;
    }

    @Override
    public NodeType getNodeType() {
        return NodeType.UNARY;
    }

    @Override
    public Object getValue() {
        return this.symbol;
    }
}
