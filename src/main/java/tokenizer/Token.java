package tokenizer;

public abstract class Token {
    protected TokenType type;
    protected String lexeme;

    Token(TokenType type, String lexeme) {
        this.type = type;
        this.lexeme = lexeme;
    }

    public abstract <T extends Object> T getValue();

    public TokenType getType() {
        return this.type;
    }
}
