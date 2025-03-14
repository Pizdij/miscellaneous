//package expression;
//
//import java.util.Map;
//
//public class Logarithm extends BinaryOperation {
//    public Logarithm(Operand first, Operand second) {
//        super(first, second);
//    }
//
//    @Override
//    public int getPriority() {
//        return -1;
//    }
//
//    @Override
//    protected int apply(int a, int b) {
//
//        if (a == 0 && b >= 1) {
//            return Integer.MIN_VALUE;
//        }
//
//        if (a <= 1 || b < 1) {
//            return 0;
//        }
//
//        if (b == 1) {
//            return Integer.MAX_VALUE;
//        }
//
//        int result = 0;
//        long current = 1;
//        while (current <= a) {
//            current *= b;
//            if (current <= a) {
//                result++;
//            }
//        }
//        return result;
//    }
//
//    @Override
//    public long evaluateL(Map<String, Long> variables) {
//        long a = first.evaluateL(variables);
//        long b = second.evaluateL(variables);
//
//        if (a == 0 && b >= 1) {
//            return Long.MIN_VALUE;
//        }
//
//        if (a <= 1 || b < 1) {
//            return 0;
//        }
//
//        if (b == 1) {
//            return Long.MAX_VALUE;
//        }
//
//        long result = 0;
//        long current = 1;
//        while (current <= a) {
//            current *= b;
//            if (current <= a) {
//                result++;
//            }
//        }
//        return result;
//    }
//
//    @Override
//    public String getOperator() {
//        return "//";
//    }
//}




package expression;

import java.util.Map;

import static java.lang.Math.pow;

public class Logarithm extends BinaryOperation {
    public Logarithm(Operand first, Operand second) {
        super(first, second);
    }

    @Override
    public int getPriority() {
        return -1;
    }

    @Override
    protected int apply(int a, int b) {

        if (a == 0 && b >= 1) {
            return Integer.MIN_VALUE;
        }

        if (a <= 1 || b < 1) {
            return 0;
        }

        if (b == 1) {
            return Integer.MAX_VALUE;
        }

        int r = 1;
        while ((int) pow(b, r) < a) {
            r *= 2;
        }

        int l = r / 2 - 1, mid;
        r++;
        while (r - l > 1) {
            mid = (r + l) / 2;
            if ((long) pow(b, mid) > a) {
                r = mid;
            } else {
                l = mid;
            }
        }
        return l;
    }

    @Override
    public long evaluateL(Map<String, Long> variables) {
        long a = first.evaluateL(variables);
        long b = second.evaluateL(variables);

        if (a == 0 && b >= 1) {
            return Long.MIN_VALUE;
        }

        if (a <= 1 || b < 1) {
            return 0;
        }

        if (b == 1) {
            return Long.MAX_VALUE;
        }

        long result = 0;
        long current = 1;
        while (current <= a) {
            current *= b;
            if (current <= a) {
                result++;
            }
        }
        return result;
    }

    @Override
    public String getOperator() {
        return "//";
    }
}
