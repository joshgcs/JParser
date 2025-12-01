package misc;

import nodes.ExpressionNode;
import nodes.LiteralNode;
import nodes.MatrixNode;
import nodes.VectorNode;
import parser.Parser;
import tokenizer.Tokenizer;

import java.util.ArrayList;
import java.util.List;

public class Matrix {
    private List<Vector> columns = new ArrayList<>();

    public Matrix(List<Vector> columns) {
        int colSize = columns.getFirst().getSize();
        for (Vector vector : columns) {
            if (vector.getSize() > colSize) {
                throw new RuntimeException("Invalid matrix size");
            }
        }
        this.columns = columns;
    }

    public Matrix(String matrix) {
        Parser parser = new Parser(new Tokenizer(matrix).tokenize());
        this.columns = createMatrix((MatrixNode) parser.parseExpression());
    }

    public Matrix() {}

    public List<Vector> getColumns() {
        return columns;
    }

    public Double getValue(int row, int col) {
        return columns.get(col).getBody().get(row);
    }

    public void setValue(int row, int col, double value) {
        try {
            columns.get(col).setValue(row, value);
        } catch (IndexOutOfBoundsException e) {
            System.err.println("Position out of bounds");
        }
    }

    public void addVector(Vector vector) {
        columns.add(vector);
    }

    public void removeVector(Vector vector) {
        columns.remove(vector);
    }

    public int getRowSize() {
        return columns.getFirst().getSize();
    }

    public int getColSize() {
        return columns.size();
    }

    public Vector getLeftNonZero() {
        for (Vector vector : columns) {
            if (!vector.isZero()) {
                return vector;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < this.getRowSize(); i++) {
            str.append("[");
            for (int j = 0; j < this.getColSize(); j++) {
                str.append(getValue(i, j));
                str.append(" ");
            }
            str.append("]");
            str.append("\n");
        }
        return str.toString();
    }

    public static Matrix reduceToEchelon(Matrix matrix) {
        int colIndex = 0;

        int rowSize = matrix.getRowSize();
        int colSize = matrix.getColSize();

        int rowToSwap;
        int pivotPos;

        loop:
        for (int rowIndex = 0; rowIndex < rowSize; rowIndex++) {

            rowToSwap = rowIndex;
            if (colIndex > colSize - 1) {
                break;
            }
            while (isZero(matrix.getValue(rowToSwap, colIndex))) {
                rowToSwap++;
                if (rowSize == rowToSwap) {
                    rowToSwap = rowIndex;
                    colIndex++;
                    if (colIndex == colSize) {
                        break loop;
                    }
                }
            }

            rowSwap(matrix, rowIndex, rowToSwap);
            pivotPos = getPivotColumn(matrix, rowIndex);
            int idx = rowIndex + 1;
            while (!isNonZeroColumn(matrix, pivotPos, rowIndex + 1)) {
                double scalar = matrix.getValue(idx, pivotPos) / matrix.getValue(rowIndex, pivotPos);
                rowAddScale(matrix, rowIndex, idx, -1 * scalar);
                idx++;
            }
            colIndex++;
        }

        return matrix;
    }
    public static Matrix rowReduce(Matrix matrix) {
        int columnIndex = 0;
        int cursor;

        int rowSize = matrix.getRowSize();
        int colSize = matrix.getColSize();

        loop:
        for (int rowIndex = 0; rowIndex < rowSize; rowIndex++) {
            if (colSize <= columnIndex) {
                break loop;
            }
            cursor = rowIndex;
            while (isZero(matrix.getValue(cursor, columnIndex))) {
                cursor++;
                if (rowSize == cursor) {
                    cursor = rowIndex;
                    columnIndex++;
                    if (colSize == columnIndex) {
                        break loop;
                    }
                }
            }
            rowSwap(matrix, cursor, rowIndex);
            if (!isZero(matrix.getValue(rowIndex, columnIndex))) {
                rowScale(matrix, rowIndex, (1/matrix.getValue(rowIndex, columnIndex)));
            }
            for (cursor = 0; cursor < rowSize; cursor++) {
                if (cursor != rowIndex) {
                    rowAddScale(matrix, rowIndex, cursor, ((-1) * matrix.getValue(cursor, columnIndex)));
                }
            }columnIndex++;
        }
        return matrix;
    }

    private static void rowSwap(Matrix matrix, int rowIndex, int rowIndex2) {
        int numCols = matrix.getColSize();

        double hold;

        for (int k = 0; k < numCols; k++) {
            hold = matrix.getValue(rowIndex2, k);
            matrix.setValue(rowIndex2, k, matrix.getValue(rowIndex, k));
            matrix.setValue(rowIndex, k, hold);
        }
    }

    private static Matrix rowAdd(Matrix matrix, int rowIndex, int rowIndex2) {
        int numCols = matrix.getColSize();

        double hold;

        for (int k = 0; k < numCols; k++) {
            hold = matrix.getValue(rowIndex2, k) + matrix.getValue(rowIndex, k);

            matrix.setValue(rowIndex, k, hold);
        }
        return matrix;
    }

    private static void rowScale(Matrix matrix, int rowIndex, double scalar) {
        int numCols = matrix.getColSize();

        for (int k = 0; k < numCols; k++) {;
            matrix.setValue(rowIndex, k, matrix.getValue(rowIndex, k) * scalar);
        }
    }

    private static void rowAddScale(Matrix matrix, int rowIndex, int rowIndex2, double scalar) {
        int numCols = matrix.getColSize();

        double hold;

        for (int k = 0; k < numCols; k++) {
            hold = matrix.getValue(rowIndex2, k) + matrix.getValue(rowIndex, k) * scalar;
            matrix.setValue(rowIndex2, k, hold);
        }

    }


    private static boolean isZero(double val) {
        return Math.abs(val)<0.00001;
    }

    private static boolean isEchelon(Matrix matrix) {
        boolean seenZeroRow = false;
        int currentPivot = 0;
        int previousPivot = -1;
        for (int row = 0; row < matrix.getRowSize(); row++) {
            boolean nonZero = isNonZeroRow(matrix, row);
            if (!nonZero) {
                if (!seenZeroRow) {
                    seenZeroRow = true;
                    continue;
                }
            }
            if (nonZero && seenZeroRow) {
                return false;
            }

            if (nonZero) {
                currentPivot = getPivotColumn(matrix, row);
            }
            if (currentPivot <= previousPivot) {
                return false;
            }

            previousPivot = currentPivot;

            if (!isNonZeroColumn(matrix, currentPivot, row)) {
                return false;
            }
        }
        return true;
    }

    private static boolean isNonZeroRow(Matrix matrix, int row) {
        for (int col = 0; col < matrix.getColSize(); col++) {
            if (!isZero(matrix.getValue(row, col))) {
                return true;
            }
        }
        return false;
    }

    private static boolean isNonZeroColumn(Matrix matrix, int col, int startingRow) {
        if (col > matrix.getColSize() || col < 0) {
            return false;
        }
        for (int row = startingRow; row < matrix.getRowSize(); row++) {
            if (!isZero(matrix.getValue(row, col))) {
                return false;
            }
        }
        return true;
    }

    private static int getPivotColumn(Matrix matrix, int row) {
        if (!isNonZeroRow(matrix, row)) {
            return -1;
        }

        for (int col = 0; col < matrix.getColSize(); col++) {
            if (!isZero(matrix.getValue(row, col))) {
                return col;
            }
        }
        return -1;
    }

    private static List<Vector> createMatrix(MatrixNode matrixNode) {
        List<Vector> cols = new ArrayList<>();
        for (VectorNode vec : matrixNode.getVectorNodeList()) {
            List<Double> body = new ArrayList<>();
            for (ExpressionNode node : vec.getBody()) {
                if (node instanceof LiteralNode lit) {
                    body.add((Double) lit.getValue());
                }
            }
            cols.add(new Vector(body));
        }
        int colSize = cols.getFirst().getSize();
        for (Vector vector : cols) {
            if (vector.getSize() != colSize) {
                throw new RuntimeException("Invalid matrix size");
            }
        }
        return cols;
    }
}
