package expression;

import java.util.Objects;

public abstract class UnaryOperation implements Operand {
    public final Operand next;

    public UnaryOperation(Operand next) {
        this.next = next;
    }

    public abstract int getPriority();

    protected abstract int apply(int a);

    @Override
    public int evaluate(int x) {
        return apply(next.evaluate(x));
    }

    @Override
    public int evaluate(int x, int y, int z) {
        return apply(next.evaluate(x, y, z));
    }

    @Override
    public int hashCode() {
        return Objects.hash(next, getOperator());
    }


    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        UnaryOperation other = (UnaryOperation) obj;
        return next.equals(other.next);
    }

}
