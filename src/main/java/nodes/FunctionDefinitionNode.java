package nodes;

import java.util.List;

/**
 * AST node representing a user-defined function declaration.
 *
 * <p>Stores the function name, ordered parameter names, and the parsed body
 * expression. This node is produced by the parser when a function is defined
 * and is used by evaluators to register or evaluate the function body.</p>
 */
public class FunctionDefinitionNode extends ExpressionNode {

    /**
     * The function identifier (name).
     */
    private String name;

    /**
     * Ordered list of parameter names declared for the function.
     */
    private List<String> params;

    /**
     * The function body as an expression AST node.
     */
    private ExpressionNode body;

    /**
     * Create a FunctionDefinitionNode.
     *
     * @param name   the function name (identifier)
     * @param params ordered list of parameter names
     * @param body   parsed function body as an {@link ExpressionNode}
     */
    public FunctionDefinitionNode(String name, List<String> params, ExpressionNode body) {
        this.name = name;
        this.params = params;
        this.body = body;
    }

    /**
     * Get the parsed function body.
     *
     * @return the {@link ExpressionNode} representing the function body
     */
    public ExpressionNode getBody() {
        return body;
    }

    /**
     * Get the declared parameter names in order.
     *
     * @return list of parameter names
     */
    public List<String> getParams() {
        return params;
    }

    /**
     * Node type identifier for visitors or evaluators.
     *
     * @return {@link NodeType#FUNCTION_DEF}
     */
    @Override
    public NodeType getNodeType() {
        return NodeType.FUNCTION_DEF;
    }

    /**
     * Return a representative value for this node.
     *
     * <p>For a function definition node this is the function name.</p>
     *
     * @return the function name
     */
    @Override
    public Object getValue() {
        return name;
    }
}
