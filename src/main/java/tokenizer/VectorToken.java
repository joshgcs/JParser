package tokenizer;

public class VectorToken extends Token {

    VectorToken(String lexeme) {
        super((lexeme.equals("[") ? TokenType.BEGVEC : TokenType.ENDVEC), lexeme);
    }

    @Override
    public <T> T getValue() {
        return (T) this.lexeme;
    }
}
