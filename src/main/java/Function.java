import misc.FunctionDefinition;

public class Function {
    private final FunctionDefinition functionDefinition;

    public Function(String expression) {
        functionDefinition = JParser.CONTEXT.addFunction(expression);
    }

    public String getExpression() {
        return functionDefinition.getExpression();
    }
}
