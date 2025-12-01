package tokenizer;

public class OperatorToken extends Token {
    private final Operator operator;

    public OperatorToken(String lexeme, Operator operator) {
        super(TokenType.OPERATOR, lexeme);
        this.operator = operator;
    }

    @Override
    public <T> T getValue() {
        return (T) this.operator;
    }
}
