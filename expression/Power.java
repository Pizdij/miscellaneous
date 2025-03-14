package expression;

import java.util.Map;

public class Power extends BinaryOperation {
    public Power(Operand first, Operand second) {
        super(first, second);
    }

    @Override
    public int getPriority() {
        return -1;
    }

    @Override
    protected int apply(int a, int b) {
        if (b < 0) {
            return 1;
        }
        int result = 1;
        while (b > 0) {
            if (b % 2 == 1) {
                result *= a;
            }
            a *= a;
            b /= 2;
        }
        return result;
    }


    @Override
    public long evaluateL(Map<String, Long> variables) {
        long base = first.evaluateL(variables);
        long exponent = second.evaluateL(variables);

        if (exponent < 0) {
            if (base == 1) {
                return 1;
            } else if (base == -1) {
                return (exponent % 2 == 0) ? 1 : -1;
            } else {
                return 0;
            }
        }
        long result = 1;
        for (int i = 0; i < exponent; i++) {
            result *= base;
        }
        return result;
    }

    @Override
    public String getOperator() {
        return "**";
    }
}
