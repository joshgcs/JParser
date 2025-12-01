package tokenizer;

public class IdentifierToken extends Token {

    IdentifierToken(String lexeme) {
        super(TokenType.IDENTIFIER, lexeme);
    }

    @Override
    public <T> T getValue() {
        return (T) this.lexeme;
    }
}
