package parser;

import nodes.*;
import tokenizer.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Parser {

    private final List<Token> tokens;
    private int position = 0;

    public Parser(List<Token> tokens){
        this.tokens = tokens;
    }

    public ExpressionNode parseExpression() {
        return parseSign();
    }

    private ExpressionNode parseTerm()  {
        ExpressionNode left = parseFactor();

        while (match(Operator.PLUS) || match(Operator.MINUS)) {
            Token token = consume();
            ExpressionNode right = parseFactor();
            left = new BinaryNode(token, left, right);
        }

        return left;
    }

    private ExpressionNode parseFactor() {
        ExpressionNode left = parseExponent();

        while (match(Operator.DIV) || match(Operator.MULT)) {
            Token token = consume();
            ExpressionNode right = parseExponent();
            left = new BinaryNode(token, left, right);
        }

        return left;
    }

    private ExpressionNode parseExponent() {
        ExpressionNode left = parseUnary();

        while (match(Operator.EXP)) {
            Token token = consume();
            ExpressionNode right = parseUnary();
            left = new BinaryNode(token, left, right);
        }

        return left;
    }

    private ExpressionNode parseSign() {
        ExpressionNode left = parseTerm();

        while (match(Operator.GT) || match(Operator.GTE) || match(Operator.LTE) || match(Operator.LT) || match(Operator.PEQUAL) || match(Operator.NEQ) || match(Operator.EQUAL)) {
            Token token = consume();
            ExpressionNode right = parseTerm();
            left = new BinaryNode(token, left, right);
        }

        return left;
    }

    private ExpressionNode parsePrimary() {
        Token tok = current();
        switch (current().getType()) {
            case NUMBER -> {
                if (Objects.requireNonNull(peek()).getType().equals(TokenType.IDENTIFIER)) {
                    tokens.add(position + 1, new OperatorToken("*", Operator.MULT));
                }
                consume();
                return new LiteralNode(tok.getValue());
            } case IDENTIFIER -> {
                return parseIdentifierBasedExpression();
            } case LPAREN -> {
                consume();
                ExpressionNode node = parseExpression();
                expect(TokenType.RPAREN);
                consume();
                return node;
            } case BEGVEC -> {
                return parseMatrix();
            }
            case null, default -> throw new RuntimeException("Invalid token type passed " + tok.getType());
        }
    }

    private ExpressionNode parseUnary() {
        if (current() != null && current().getValue().equals(Operator.MINUS)) {
            consume();
            return new UnaryNode(UnaryNode.UnarySymbol.NEGATIVE, parseUnary());
        }
        return parsePrimary();
    }

    private ExpressionNode parseMatrix() {
        List<VectorNode> nodes = new ArrayList<>();

        while (current().getType().equals(TokenType.BEGVEC)) {
            nodes.add(parseVector());
        }

        return new MatrixNode(nodes);
    }

    private VectorNode parseVector() {
        consume();
        List<ExpressionNode> body = new ArrayList<>();

        while (!current().getType().equals(TokenType.ENDVEC)) {
            body.add(parseExpression());
        }
        consume();
        return new VectorNode(body);
    }

    private ExpressionNode parseFunctionCall(String name) {
        consume();
        List<ExpressionNode> args = new ArrayList<>();

        if (!current().getType().equals(TokenType.RPAREN)) {
            args.add(parseExpression());
            while (current().getType().equals(TokenType.COMMA)) {
                consume();
                args.add(parseExpression());
            }
        }
        consume();
        return new FunctionCallNode(name, args);
    }

    private ExpressionNode parseFunctionDefinition(String name) {
        consume();
        List<String> params = new ArrayList<>();
        if (!current().getType().equals(TokenType.RPAREN)) {
            while (true) {
                if (!current().getType().equals(TokenType.IDENTIFIER)) {
                    throw new RuntimeException("Expected parameter name in function definition");
                }
                params.add(current().getValue().toString());
                consume();
                if (current().getType().equals(TokenType.COMMA)) {
                    consume();
                } else {
                    break;
                }
            }
        }
        expect(TokenType.RPAREN);
        consume();
        if (!(current().getType().equals(TokenType.OPERATOR) && current().getValue().equals(Operator.EQUAL))) {
            throw new RuntimeException("Expected '=' after function parameters");
        }
        consume();

        ExpressionNode body = parseExpression();
        return new FunctionDefinitionNode(name, params, body);
    }

    private ExpressionNode parseIdentifierBasedExpression() {
        String name = current().getValue().toString();
        consume();

        if (current().getType().equals(TokenType.LPAREN)) {
            return parseFunctionBasedExpression(name);
        }

        return new VariableNode(name);
    }

    private ExpressionNode parseFunctionBasedExpression(String name) {
        if (isFunctionDefinition()) {
            return parseFunctionDefinition(name);
        } else {
            return parseFunctionCall(name);
        }
    }

    private boolean isFunctionDefinition() {
        int closing = findClosingParenIndex(position);
        if (closing >= 0 && closing + 1 < tokens.size()) {
            Token next = tokens.get(closing + 1);
            return next.getType().equals(TokenType.OPERATOR) && next.getValue().equals(Operator.EQUAL);
        }
        return false;
    }

    private int findClosingParenIndex(int pos) {
        int depth = 0;
        for (int i = pos; i < tokens.size(); i++) {
            Token t = tokens.get(i);
            if (t.getType().equals(TokenType.LPAREN)) {
                depth++;
            } else if (t.getType().equals(TokenType.RPAREN)) {
                depth --;
                if (depth == 0) return i;
            }
        }
        return -1;
    }

    private Token current() {
        return tokens.get(position);
    }

    private Token peek() {
        if (position + 1 < tokens.size()) {
            return tokens.get(position + 1);
        }
        return null;
    }

    private Token consume() {
        if (position < tokens.size()) {
            Token toReturn = current();
            position++;
            return toReturn;
        }
        return null;
    }

    private boolean match(Operator operator) {
        if (position < tokens.size()) {
            return current().getValue().equals(operator);
        }
        return false;
    }

    private void expect(TokenType type) {
        if (!current().getType().equals(type)) {
            throw new RuntimeException("Error while parsing: token does not match expected type of " + type);
        }
    }
}
