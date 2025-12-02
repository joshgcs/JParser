package nodes;

import tokenizer.Operator;
import tokenizer.Token;

/**
 * AST node representing a binary operation (e.g. addition, multiplication).
 *
 * <p>Holds the operator token and references to the left and right operand
 * subtrees.</p>
 */
public class BinaryNode extends ExpressionNode {

    /**
     * Left operand of the binary operation.
     */
    private final ExpressionNode leftChild;

    /**
     * Right operand of the binary operation.
     */
    private final ExpressionNode rightChild;

    /**
     * Token that contains the operator for this binary node.
     */
    private final Token token;

    /**
     * Create a BinaryNode with the given operator token and operand nodes.
     *
     * @param token the operator token (e.g. {@code +}, {@code *})
     * @param leftChild left operand subtree
     * @param rightChild right operand subtree
     */
    public BinaryNode(Token token, ExpressionNode leftChild, ExpressionNode rightChild) {
        this.token = token;
        this.leftChild = leftChild;
        this.rightChild = rightChild;
    }

    /**
     * Return the right operand node.
     *
     * @return right {@link ExpressionNode}
     */
    public ExpressionNode getRightChild() {
        return rightChild;
    }

    /**
     * Return the left operand node.
     *
     * @return left {@link ExpressionNode}
     */
    public ExpressionNode getLeftChild() {
        return leftChild;
    }

    /**
     * Return the operator represented by this node.
     *
     * @return {@link Operator} of the underlying token
     */
    public Operator getOperator() {
        return token.getValue();
    }

    /**
     * Node type identifier for visitors or evaluators.
     *
     * @return {@link NodeType#BINARY}
     */
    @Override
    public NodeType getNodeType() {
        return NodeType.BINARY;
    }

    /**
     * Return the raw value associated with this node.
     *
     * <p>For a BinaryNode this is the operator value stored in the token.</p>
     *
     * @return operator value (as an {@link Object})
     */
    @Override
    public Object getValue() {
        return this.token.getValue();
    }
}
