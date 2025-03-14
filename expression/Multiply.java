package expression;

import java.util.Map;

public class Multiply extends BinaryOperation {

    public Multiply(Operand first, Operand second) {
        super(first, second);
    }

    @Override
    public int apply(int first, int second) {
        return first * second;
    }

    @Override
    public String getOperator() {
        return "*";
    }

    @Override
    public int getPriority() {
        return 1;
    }

    @Override
    public long evaluateL(Map<String, Long> variables) {
        return first.evaluateL(variables) * second.evaluateL(variables);
    }
}