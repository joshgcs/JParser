package literals;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents either a named variable or a numeric constant used in mathematical
 * expressions.
 *
 * <p>A MathObject can hold a variable name (for example "x") or a numeric
 * value. If a value is queried when no numeric value is set, {@link #getValue()}
 * returns 0.0. The {@link #toString()} method prefers the variable name if set,
 * otherwise returns the numeric value as a string, and falls back to "0.0".</p>
 */
public class MathObject {
    /**
     * The variable name represented by this object, e.g. "x".
     * When non-null this object represents a variable rather than a concrete value.
     */
    private String name;

    /**
     * The numeric value represented by this object.
     * When non-null this object represents a concrete numeric constant.
     */
    private BigDecimal value;

    /**
     * Constructs a MathObject that represents a variable with the given name.
     *
     * @param name the variable name (may be null, but then object is effectively empty)
     */
    public MathObject(String name) {
        this.name = name;
    }

    public MathObject(Double value) {
        this.value = BigDecimal.valueOf(value);
    }

    public MathObject(int value) {
        this.value = BigDecimal.valueOf(value);
    }

    /**
     * Constructs a MathObject that represents a numeric constant.
     *
     * @param value the numeric value to store
     */
    public MathObject(BigDecimal value) {
        this.value = value;
    }

    public boolean isCharacter() {
        return this.name != null;
    }

    /**
     * Returns the variable name stored in this object.
     *
     * @return the variable name, or {@code null} if this object holds a numeric value
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the numeric value stored in this object.
     *
     * <p>If no numeric value is set (i.e. {@link #value} is {@code null}), this
     * method returns {@code 0.0} as a safe default.</p>
     *
     * @return the stored numeric value, or {@code 0.0} when none is set
     */
    public BigDecimal getValue() {
        return (this.value != null ? this.value : null);
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public void setValue(MathObject object) {
        this.name = object.getName();
    }

    public void setName(String name) {
        this.name = name;
    }

    public MathObject combine(MathObject object) {
        this.setName(this + object.toString());
        return this;
    }

    public MathObject combine(MathObject object, String string) {
        this.setName(this + string + object.toString());
        return this;
    }

    public static MathObject combine(MathObject object1, MathObject object2) {
        MathObject newObject = object1.combine(object2);
        return newObject;
    }

    public static MathObject combine(MathObject object1, MathObject object2, String string) {
        MathObject object = object1.combine(object2, string);
        return object;
    }

    public MathObject operation(MathObject object, String operator) {
        if (this.getName() != null) {
            if (object.getName() != null && !this.getName().isEmpty()) {
                this.name = this.name + (object.getName().isEmpty() ? "" : operator + object.getName());
            } else if (!this.getName().isEmpty() && object.getValue() != null){
                this.name = this.name + operator + object.getValue();
            } else if (object.getName() != null && this.getName().isEmpty()){
                if (operator.equals("-")) {
                    this.name = "-" + object.getName();
                } else {
                    this.name = object.getName();
                }
            } else {
                this.name = "";
            }
        } else if (this.getValue() != null) {
            BigDecimal newVal = this.value;
            if (object.getValue() != null) {
                if (operator.equals("-")) {
                    newVal = this.value.subtract(object.getValue());
                } else if (operator.equals("+")) {
                    newVal = this.value.add(object.getValue());
                } else if (operator.equals("*")) {
                    newVal = this.value.multiply(object.getValue());
                }
                this.value = newVal;
            } else {
                if (this.name != null && this.name.isEmpty()) {
                    this.name = object.getValue().toString();
                } else {
                    this.name = this.value.toString() + operator + (object.getValue());
                }
            }
        }
        return this;
    }

    public void addParenthesis() {
        if (this.name == null) {
            this.name = this.value.toString();
        }
        this.name = "(" + this.name + ")";
    }

    /**
     * Returns a string representation of this MathObject.
     *
     * <p>If a variable name is present it is returned. Otherwise the numeric value
     * is returned as a string. If neither is present the string {@code "0.0"} is returned.</p>
     *
     * @return a human-readable representation of the object
     */
    @Override
    public String toString() {
        if (this.name != null) {
            return this.name;
        } else if (this.value != null){
            return String.valueOf(this.value);
        } else {
            return "0.0";
        }
    }
}
