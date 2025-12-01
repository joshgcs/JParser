package nodes;

import tokenizer.Operator;
import tokenizer.Token;

public class BinaryNode extends ExpressionNode {

    private final ExpressionNode leftChild;
    private final ExpressionNode rightChild;
    private final Token token;

    public BinaryNode(Token token, ExpressionNode leftChild, ExpressionNode rightChild) {
        this.token = token;
        this.leftChild = leftChild;
        this.rightChild = rightChild;
    }

    public ExpressionNode getRightChild() {
        return rightChild;
    }

    public ExpressionNode getLeftChild() {
        return leftChild;
    }

    public Operator getOperator() {
        return token.getValue();
    }

    @Override
    public NodeType getNodeType() {
        return NodeType.BINARY;
    }

    @Override
    public Object getValue() {
        return this.token.getValue();
    }
}
