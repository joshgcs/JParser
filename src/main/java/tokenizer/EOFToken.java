package tokenizer;

public class EOFToken extends Token {
    EOFToken() {
        super(TokenType.EOF, "");
    }

    @Override
    public <T> T getValue() {
        return (T) "EOF";
    }
}
