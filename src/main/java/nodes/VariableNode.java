package nodes;

/**
 * AST node representing a variable reference in an expression.
 *
 * <p>Stores the variable identifier (name). Evaluators and visitors can use
 * {@link #getName()} to obtain the identifier, and {@link #getValue()} to
 * retrieve a representative value (the name).</p>
 */
public class VariableNode extends ExpressionNode{
    /**
     * The variable identifier (name).
     */
    private String name;

    /**
     * Create a VariableNode for the given identifier.
     *
     * @param name the variable name
     */
    public VariableNode(String name) {
        this.name = name;
    }

    /**
     * Get the variable identifier for this node.
     *
     * @return the variable name
     */
    public String getName() {
        return name;
    }

    /**
     * Node type identifier used by visitors or evaluators.
     *
     * @return {@link NodeType#VARIABLE}
     */
    @Override
    public NodeType getNodeType() {
        return NodeType.VARIABLE;
    }

    /**
     * Return a representative value for this node.
     *
     * <p>For a variable node this is the variable name (identifier). Evaluators
     * typically resolve this name to a value in an environment or symbol table.</p>
     *
     * @return the variable name
     */
    @Override
    public Object getValue() {
        return this.name;
    }
}
