package nodes;

public abstract class ExpressionNode {

    public enum NodeType {
        NUMBER,
        VARIABLE,
        FUNCTION_DEF,
        FUNCTION_CALL,
        VECTOR,
        MATRIX,
        BINARY,
        UNARY
    }

    public abstract NodeType getNodeType();
    public abstract Object getValue();
}
