import evaluator.Evaluator;
import misc.EvalContext;
import parser.Parser;
import tokenizer.Tokenizer;

public abstract class JParser {
    public static EvalContext CONTEXT = new EvalContext();
    public static Evaluator EVALUATOR = new Evaluator();
    public static Parser PARSER;


    public static Double evaluate(String expression) {
        if (expression.trim().isEmpty()) {
            return 0.0;
        }
        PARSER = new Parser(new Tokenizer(expression).tokenize());
        return EVALUATOR.evaluate(PARSER.parseExpression(), CONTEXT);
    }

    public static void createFunction(String expression) {
        new Function(expression);
    }
}
