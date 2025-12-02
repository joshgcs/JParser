package nodes;

import java.util.List;

/**
 * AST node representing a matrix literal (two-dimensional collection) in an expression.
 *
 * <p>Stores an ordered list of row vectors. Each row is represented by a {@link VectorNode}.
 * Parsers create this node for matrix literals and evaluators can retrieve the rows via
 * {@link #getVectorNodeList()} or {@link #getValue()}.</p>
 */
public class MatrixNode extends ExpressionNode {
    /**
     * Ordered list of row vectors that make up the matrix.
     */
    private List<VectorNode> vectorNodeList;

    /**
     * Construct a MatrixNode with the supplied list of row vectors.
     *
     * @param vectorNodeList list of {@link VectorNode} representing matrix rows (may be empty)
     */
    public MatrixNode(List<VectorNode> vectorNodeList) {
        this.vectorNodeList = vectorNodeList;
    }

    /**
     * Return the list of row vectors for this matrix.
     *
     * @return list of {@link VectorNode} rows
     */
    public List<VectorNode> getVectorNodeList() {
        return vectorNodeList;
    }

    /**
     * Node type identifier used by visitors or evaluators.
     *
     * @return {@link NodeType#MATRIX}
     */
    @Override
    public NodeType getNodeType() {
        return NodeType.MATRIX;
    }

    /**
     * Return the raw value associated with this node.
     *
     * <p>For a MatrixNode this is the underlying list of row vectors. Consumers may
     * cast the returned {@link Object} to {@code List<VectorNode>} when needed.</p>
     *
     * @return the matrix contents as an {@link Object}
     */
    @Override
    public Object getValue() {
        return vectorNodeList;
    }
}
