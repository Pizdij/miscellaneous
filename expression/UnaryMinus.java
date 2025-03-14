package expression;

import java.util.Map;

public class UnaryMinus extends UnaryOperation  {

    public UnaryMinus(Operand next) {
        super(next);
    }

    @Override
    public String getOperator() {
        return "_";
    }

    @Override
    public int getPriority() {
        return -10;
    }

    @Override
    protected int apply(int x) {
        return -x;
    }

    @Override
    public long evaluateL(Map<String, Long> variables) {
        return apply((int) next.evaluateL(variables));
    }

    @Override
    public void toMiniString(StringBuilder result) {
        result.append("-");
        if (next instanceof BinaryOperation) {
            result.append('(');
            next.toMiniString(result);
            result.append(")");
        } else {
            result.append(" ");
            next.toMiniString(result);
        }
    }

    @Override
    public String toMiniString() {
        StringBuilder result = new StringBuilder();
        toMiniString(result);
        return result.toString();
    }

    @Override
    public String toString() {
        StringBuilder ans = new StringBuilder();
        ans.append("-(");
        ans.append(next.toString());
        ans.append(")");
        return ans.toString();
    }

}