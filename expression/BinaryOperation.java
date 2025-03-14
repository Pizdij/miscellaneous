package expression;

import java.util.Objects;

public abstract class BinaryOperation implements Operand {
    public final Operand first;
    public final Operand second;

    public BinaryOperation(Operand first, Operand second) {
        this.first = first;
        this.second = second;
    }

    public abstract int getPriority();

    // protected?
    protected abstract int apply(int a, int b);

    @Override
    public int evaluate(int x) {
        return apply(first.evaluate(x), second.evaluate(x));
    }

    @Override
    public int evaluate(int x, int y, int z) {
        return apply(first.evaluate(x, y, z), second.evaluate(x, y, z));
    }

    @Override
    public String toString() {
        return "(" + first.toString() + " " + getOperator() + " " + second.toString() + ")";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        BinaryOperation other = (BinaryOperation) obj;
        return first.equals(other.first) && second.equals(other.second);
    }

    @Override
    public int hashCode() {
        return Objects.hash(first, second, getOperator());
    }

    private boolean needBrackets(int priorityCurrent, int priorityOperand, boolean isRight) {
        if (isRight) {
            return priorityCurrent < priorityOperand ||
                    (priorityCurrent == priorityOperand && (!isLeftAssociative() || requiresRightBrackets()));
        } else {
            return priorityCurrent < priorityOperand;
        }
    }

    private boolean requiresRightBrackets() {
        String currentOperator = getOperator();
        String rightOperator = second.getOperator();

        if (rightOperator == null) {
            return false;
        }

        return (currentOperator.equals("*") && rightOperator.equals("/")) ||
                (currentOperator.equals("/") && rightOperator.equals("*"));
    }

    private boolean isLeftAssociative() {
        String operator = getOperator();
        return operator.equals("+") || operator.equals("*");
    }

    public void toMiniString(StringBuilder result) {
        int priorityLeft, priorityRight, priorityCurrent;
        priorityLeft = first.getPriority();
        priorityRight = second.getPriority();
        priorityCurrent = getPriority();
        if (needBrackets(priorityCurrent, priorityLeft, false)) {
            result.append("(");
            first.toMiniString(result);
            result.append(") ");
        } else {
            first.toMiniString(result);
            result.append(" ");
        }

        result.append(getOperator());
        result.append(" ");

        if (needBrackets(priorityCurrent, priorityRight, true)) {
            result.append("(");
            second.toMiniString(result);
            result.append(")");
        } else {
            second.toMiniString(result);
        }
    }

    @Override
    public String toMiniString() {
        StringBuilder result = new StringBuilder();
        toMiniString(result);
        return result.toString();
    }
}
