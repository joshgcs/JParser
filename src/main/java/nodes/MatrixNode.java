package nodes;

import java.util.List;

public class MatrixNode extends ExpressionNode {
    private List<VectorNode> vectorNodeList;

    public MatrixNode(List<VectorNode> vectorNodeList) {
        this.vectorNodeList = vectorNodeList;
    }

    public List<VectorNode> getVectorNodeList() {
        return vectorNodeList;
    }

    @Override
    public NodeType getNodeType() {
        return NodeType.MATRIX;
    }

    @Override
    public Object getValue() {
        return vectorNodeList;
    }
}
