package tokenizer;

public class CommaToken extends Token{

    public CommaToken() {
        super(TokenType.COMMA, ",");
    }

    @Override
    public <T> T getValue() {
        return (T) ",";
    }
}
