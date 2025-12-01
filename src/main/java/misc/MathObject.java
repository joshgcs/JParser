package misc;

public class MathObject {
    private String name;
    private Double value;

    public MathObject(String name) {
        this.name = name;
    }

    public MathObject(double value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public double getValue() {
        return (this.value != null ? this.value : 0.0);
    }

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
