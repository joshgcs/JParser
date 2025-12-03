// java
package nodes;

import tokenizer.Token;

/**
 * AST node representing a unary operation applied to a single child expression.
 *
 * <p>Examples include unary plus and unary minus (e.g. <code>+x</code>, <code>-x</code>).
 * The node stores the specific unary symbol and the operand expression.</p>
 */
public class UnaryNode extends ExpressionNode{

    /**
     * Operand expression the unary operator is applied to.
     */
    private ExpressionNode child;

    /**
     * The unary operator symbol for this node.
     */
    private UnarySymbol symbol;

    /**
     * Enumeration of supported unary operator symbols.
     *
     * POSITIVE corresponds to unary plus, NEGATIVE to unary minus.
     */
    public enum UnarySymbol {
        /**
         * Unary plus (no-op for numeric values).
         */
        POSITIVE,
        /**
         * Unary negation (negates numeric value).
         */
        NEGATIVE;
    }

    /**
     * Create a UnaryNode with the given symbol and operand.
     *
     * @param symbol the unary operator symbol (POSITIVE or NEGATIVE)
     * @param child  the operand expression node
     */
    public UnaryNode(UnarySymbol symbol, ExpressionNode child) {
        this.child = child;
        this.symbol = symbol;
    }

    /**
     * Return the operand expression for this unary node.
     *
     * @return the child {@link ExpressionNode}
     */
    public ExpressionNode getChild() {
        return child;
    }

    /**
     * Return the unary symbol of this node.
     *
     * @return the {@link UnarySymbol} representing the operator
     */
    public UnarySymbol getSymbol() {
        return this.symbol;
    }

    /**
     * Node type identifier used by visitors or evaluators.
     *
     * @return {@link NodeType#UNARY}
     */
    @Override
    public NodeType getNodeType() {
        return NodeType.UNARY;
    }

    /**
     * Return a representative value for this node.
     *
     * <p>For a unary node this returns the operator symbol. Evaluators will
     * typically combine the symbol with the child node when computing a result.</p>
     *
     * @return the {@link UnarySymbol} for this node
     */
    @Override
    public Object getValue() {
        return this.symbol;
    }
}
