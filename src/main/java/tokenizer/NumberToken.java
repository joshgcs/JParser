package tokenizer;

public class NumberToken extends Token{
    private final Double value;

    NumberToken(String lexeme, Double value) {
        super(TokenType.NUMBER, lexeme);
        this.value = value;
    }

    @Override
    public <T> T getValue() {
        return (T) this.value;
    }
}
