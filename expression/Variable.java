package expression;

import java.util.Map;
import java.util.Objects;

public class Variable implements Operand {
    private final String name;

    public Variable(String name) {
        this.name = name;
    }

    @Override
    public int evaluate(int x) {
        return x;
    }

    public int evaluate(int x, int y, int z) {
        return switch (name.charAt(name.length() - 1)) {
            case 'x' -> x;
            case 'y' -> y;
            case 'z' -> z;
            default -> throw new IllegalArgumentException("Unknown variable: " + name);
        };
    }


    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Variable other = (Variable) obj;
        return name.equals(other.name);
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
    public int getPriority() {
        return -7;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name);
    }

    @Override
    public long evaluateL(Map<String, Long> variables) {
        return variables.getOrDefault(name, 0L);
    }
}