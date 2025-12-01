package tokenizer;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class Tokenizer {

    private final String expression;
    private int position = 0;
    private final List<String> operators = Operator.getAsStringList();

    public Tokenizer(String expression) {
        this.expression = expression;
    }

    public List<Token> tokenize() {
        List<Token> tokens = new ArrayList<>();
        Character currentChar;
        boolean dotSeen;
        StringBuilder sb = new StringBuilder();
        while (current() != null && position < expression.length()) {
            currentChar = current();
            if (currentChar != null) {
                if (Character.isDigit(currentChar)) {
                    dotSeen = false;
                    while (currentChar != null && (Character.isDigit(currentChar) || currentChar.equals('.'))) {
                        if (currentChar.equals('.')) {
                            if (dotSeen) {
                                break;
                            } else {
                                dotSeen = true;
                            }
                        }
                        sb.append(currentChar);
                        if (peek() != null && (Character.isDigit(peek()) || match('.'))) {
                            currentChar = advance();
                        } else {
                            break;
                        }
                    }
                    tokens.add(new NumberToken(sb.toString(), Double.parseDouble(sb.toString())));
                } else if (operators.contains(currentChar.toString())) {
                    if (peek() != null && operators.contains(currentChar.toString() + peek())) {
                        sb.append(currentChar).append(peek());
                        tokens.add(new OperatorToken(sb.toString(), Operator.getAsString(sb.toString())));
                        advance();
                    } else {
                        sb.append(currentChar);
                        tokens.add(new OperatorToken(sb.toString(), Operator.getAsString(sb.toString())));
                    }
                } else if (Character.isLetter(currentChar)) {
                    while (currentChar != null && Character.isLetter(currentChar) && currentChar != '(' && currentChar != ')') {
                        sb.append(currentChar);
                        if (peek() != null && (match('(') || match(')') || !Character.isLetter(peek()))) {
                            break;
                        } else {
                            currentChar = advance();
                        }
                    }
                    tokens.add(new IdentifierToken(sb.toString()));
                } else if (currentChar.equals(')') || currentChar.equals('(')) {
                    sb.append(currentChar);
                    tokens.add(new ParenthesisToken(sb.toString()));
                } else if (currentChar.equals(',')) {
                    tokens.add(new CommaToken());
                } else if (currentChar.equals('[') || currentChar.equals(']')) {
                    sb.append(currentChar);
                    tokens.add(new VectorToken(sb.toString()));
                }
                advance();
                sb.setLength(0);
            }
        }
        tokens.add(new EOFToken());
        return tokens;
    }

    private Character current() {
        return (position < expression.length() ? expression.charAt(position) : null);
    }

    private Character peek() {
        if (position + 1 < expression.length()) {
            return expression.charAt(position + 1);
        }
        return null;
    }

    private Character advance() {
        position++;
        if (position < expression.length()) {
            return expression.charAt(position);
        }
        return null;
    }

    private boolean match(Character c) {
        return Objects.equals(peek(), c);
    }

    private String lookAhead(int amount) {
        if (position + amount < expression.length()) {
            return expression.substring(position, position + amount);
        }
        return expression.substring(position + 1);
    }
}
