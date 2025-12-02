package nodes;

import java.util.List;

/**
 * AST node representing a one-dimensional vector (list) literal in an expression.
 *
 * <p>Stores an ordered list of element expressions. Parsers create this node for
 * vector literals (e.g. \[1, 2, x\]) and evaluators/visitors can retrieve the
 * elements with {@link #getBody()} or the raw value via {@link #getValue()}.</p>
 */
public class VectorNode extends ExpressionNode {
    /**
     * Ordered list of element expressions contained in the vector.
     */
    private List<ExpressionNode> body;

    /**
     * Construct a VectorNode with the provided element expressions.
     *
     * @param body list of {@link ExpressionNode} elements (may be empty)
     */
    public VectorNode(List<ExpressionNode> body) {
        this.body = body;
    }

    /**
     * Return the elements of this vector in their original order.
     *
     * @return list of element {@link ExpressionNode}s
     */
    public List<ExpressionNode> getBody() {
        return body;
    }

    /**
     * Node type identifier used by visitors or evaluators.
     *
     * @return {@link NodeType#VECTOR}
     */
    @Override
    public NodeType getNodeType() {
        return NodeType.VECTOR;
    }

    /**
     * Return the raw value associated with this node.
     *
     * <p>For a VectorNode this is the underlying list of element expression nodes.
     * Consumers may cast the returned {@link Object} to {@code List<ExpressionNode>}
     * when needed.</p>
     *
     * @return the vector contents as an {@link Object}
     */
    @Override
    public Object getValue() {
        return body;
    }
}
