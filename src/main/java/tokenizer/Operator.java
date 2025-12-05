package tokenizer;

import java.util.ArrayList;
import java.util.List;

public enum Operator {
    PLUS("+"),
    MINUS("-"),
    MULT("*"),
    DIV("/"),
    GT(">"),
    LT("<"),
    GTE(">="),
    LTE("<="),
    NEQ("!="),
    EQUAL("="),
    PEQUAL("+="),
    EXP("^");


    private final String asString;
    Operator(String asString) {
        this.asString = asString;
    }

    public static List<String> getAsStringList() {
        List<String> toReturn = new ArrayList<>();
        for (Operator op : Operator.values()) {
            toReturn.add(op.asString);
        }
        return toReturn;
    }

    public static String getFromOperator(Operator operator) {
        return operator.asString;
    }

    public static Operator getAsString(String op) {
        for (int i = 0; i < values().length; i++) {
            if (Operator.values()[i].asString.equals(op)) {
                return Operator.values()[i];
            }
        }
        return null;
    }
}
