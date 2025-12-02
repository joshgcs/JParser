package tokenizer;

/**
 * Abstract base class for tokens produced by the tokenizer.
 *
 * <p>Each Token carries a {@link TokenType} identifying the kind of token and a
 * {@code lexeme} containing the raw text from the input. Concrete subclasses
 * should provide a typed value via {@link #getValue()} (for example a numeric
 * value for number tokens or a string for identifier tokens).</p>
 */
public abstract class Token {
    /**
     * The type of this token.
     */
    protected TokenType type;

    /**
     * The raw lexeme (text) matched for this token.
     */
    protected String lexeme;

    /**
     * Construct a token with the given type and lexeme.
     *
     * @param type   the {@link TokenType} for this token
     * @param lexeme the raw text matched for this token
     */
    Token(TokenType type, String lexeme) {
        this.type = type;
        this.lexeme = lexeme;
    }

    /**
     * Return the typed value represented by this token.
     *
     * <p>Concrete token subclasses should return an appropriate value type
     * (for example {@link Double} for numeric tokens). The generic return
     * allows callers to cast to the expected type.</p>
     *
     * @param <T> the expected value type
     * @return the token value as type {@code T}
     */
    public abstract <T extends Object> T getValue();

    /**
     * Return the token type.
     *
     * @return the {@link TokenType} of this token
     */
    public TokenType getType() {
        return this.type;
    }
}
