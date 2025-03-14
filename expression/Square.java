package expression;

import java.util.Map;

public class Square extends UnaryOperation {

    public Square(Operand next) {
        super(next);
    }

    @Override
    public String getOperator() {
        return "²";
    }

    @Override
    public int getPriority() {
        return -9;
    }

    @Override
    protected int apply(int x) {
        return x * x;
    }

    @Override
    public long evaluateL(Map<String, Long> variables) {
        return apply((int) next.evaluateL(variables));
    }

    @Override
    public void toMiniString(StringBuilder result) {
        if (next instanceof BinaryOperation ||
                (next instanceof UnaryOperation && next.getPriority() < this.getPriority())) {
            result.append('(');
            next.toMiniString(result);
            result.append(')');
        } else {
            next.toMiniString(result);
        }
        result.append("²");
    }


    @Override
    public String toMiniString() {
        StringBuilder result = new StringBuilder();
        toMiniString(result);
        return result.toString();
    }

    @Override
    public String toString() {
        return "(" + next.toString() + ")²";
    }
}
