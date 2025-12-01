package misc;

import java.util.ArrayList;
import java.util.List;

public class Vector {
    private List<Double> body = new ArrayList<>();

    public Vector(List<Double> body) {
        this.body = body;
    }

    public void setValue(int pos, double value) {
        this.body.set(pos, value);
    }

    public void addValue(double value) {
        this.body.add(value);
    }

    public int getSize() {
        return body.size();
    }

    public List<Double> getBody() {
        return body;
    }

    public boolean isZero() {
        for (Double doub : body) {
            if (doub != 0.0) {
                return false;
            }
        }
        return true;
    }
}
