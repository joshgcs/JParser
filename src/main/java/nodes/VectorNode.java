package nodes;

import java.util.List;

public class VectorNode extends ExpressionNode {
    private List<ExpressionNode> body;

    public VectorNode(List<ExpressionNode> body) {
        this.body = body;
    }

    public List<ExpressionNode> getBody() {
        return body;
    }

    @Override
    public NodeType getNodeType() {
        return NodeType.VECTOR;
    }

    @Override
    public Object getValue() {
        return body;
    }
}
