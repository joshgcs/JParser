import evaluator.Evaluator;
import misc.EvalContext;
import misc.Matrix;
import misc.MathObject;
import parser.Parser;
import tokenizer.Tokenizer;

public abstract class JParser {
    public static EvalContext CONTEXT = new EvalContext();
    public static Evaluator EVALUATOR = new Evaluator();
    public static Parser PARSER;
    private static boolean degrees;

    public static MathObject evaluate(String expression) {
        if (expression.trim().isEmpty()) {
            return new MathObject(0.0);
        }
        PARSER = new Parser(new Tokenizer(expression).tokenize());
        MathObject mathObject = EVALUATOR.evaluate(PARSER.parseExpression(), CONTEXT);
//        if (Math.abs(value) <= 1e-5) {
//            return new Rational(0.0);
//        }
        return mathObject;
    }

    public static void createFunction(String expression) {
        new Function(expression);
    }

    public static void changeDegrees(boolean degrees) {
        JParser.degrees = degrees;
    }

    public static Matrix rowReduce(Matrix matrix) {
        return Matrix.rowReduce(matrix);
    }

    public static Matrix echelonForm(Matrix matrix) {
        return Matrix.reduceToEchelon(matrix);
    }
}
