package expression;

import java.util.Map;

public class Const implements Operand {
    private final long value;

    public Const(long value) {
        this.value = value;
    }

    public Const(int value) {
        this.value = value;
    }

    @Override
    public int evaluate(int x) {
        return (int) value;
    }

    @Override
    public int evaluate(int x, int y, int z) {
        return (int) value;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }

    @Override
    public String toMiniString() {
        return toString();
    }

    @Override
    public void toMiniString(StringBuilder result) {
        result.append(toMiniString());
    }

    public String getOperator() {
        return "";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Const other = (Const) obj;
        return value == other.value;
    }

    public int getPriority() {
        return -7;
    }

    @Override
    public long evaluateL(Map<String, Long> variables) {
        return value;
    }

    public int getValue() {
        return (int) value;
    }
}