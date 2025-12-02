package nodes;


import java.util.List;

/**
 * AST node representing a function call / invocation in an expression.
 *
 * <p>Stores the function identifier and a list of argument expression nodes.
 * This node type is used by the parser to represent calls such as
 * {@code f(x, 2)} and by evaluators to resolve and evaluate the call.</p>
 */
public class FunctionCallNode extends ExpressionNode {

    /**
     * Name of the function being called (identifier).
     */
    private String name;

    /**
     * Ordered list of argument expression nodes supplied to the call.
     */
    private List<ExpressionNode> args;

    /**
     * Create a FunctionCallNode with the given function name and arguments.
     *
     * @param name function identifier
     * @param args list of argument expression nodes (may be empty)
     */
    public FunctionCallNode(String name, List<ExpressionNode> args) {
        this.name = name;
        this.args = args;
    }

    /**
     * Return the function name (identifier) for this call.
     *
     * @return function name
     */
    public String getName() {
        return name;
    }

    /**
     * Return the list of argument expression nodes.
     *
     * @return list of {@link ExpressionNode} arguments
     */
    public List<ExpressionNode> getArgs() {
        return args;
    }

    /**
     * Node type identifier for visitors or evaluators.
     *
     * @return {@link NodeType#FUNCTION_CALL}
     */
    @Override
    public NodeType getNodeType() {
        return NodeType.FUNCTION_CALL;
    }

    /**
     * Return a value representative for this node.
     *
     * <p>For a function call node the value is the function name (identifier).
     * Evaluators will typically use {@link #getName()} and {@link #getArgs()}
     * to perform resolution and execution of the call.</p>
     *
     * @return the function name
     */
    @Override
    public Object getValue() {
        return this.name;
    }
}
