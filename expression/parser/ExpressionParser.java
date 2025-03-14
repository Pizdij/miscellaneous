package expression.parser;

import expression.*;

public class ExpressionParser implements TripleParser {

    @Override
    public TripleExpression parse(String expression) {
        return (new ParserAlso(new StringSource(expression)).parse());
    }

    private static class ParserAlso extends BaseParser {
        private ParserAlso(CharSource source) {
            super(source);
        }

        private TripleExpression parse() {
            return PriorityLow();
        }

        private Operand PriorityLow() {
            Operand pr1 = PriorityMid();
            SkipWhitespace();
            while (test('-') || test('+')) {
                if (take('+')) {
                    pr1 = new Add(pr1, PriorityMid());
                } else if (take('-')) {
                    pr1 = new Subtract(pr1, PriorityMid());
                } else {
                    throw new RuntimeException("error");
                }
            }
            SkipWhitespace();
            return pr1;
        }

        private Operand PriorityMid() {
            SkipWhitespace();
            Operand pr2 = PriorityHigh();
            while (test('/') || test('*')) {
                if (take('/')) {
                    pr2 = new Divide(pr2, PriorityHigh());
                } else {
                    take();
                    pr2 = new Multiply(pr2, PriorityHigh());
                }
                SkipWhitespace();
            }
            return pr2;
        }

        private Operand PriorityHigh() {
            Operand prun = PriorityUnary();
            SkipWhitespace();
            while (test("//") || test("**")) {
                if (test("//")) {
                    take();
                    take();
                    prun = new Logarithm(prun, PriorityUnary());
                } else if (test("**")) {
                    take();
                    take();
                    prun = new Power(prun, PriorityUnary());
                }
                SkipWhitespace();
            }
            return prun;
        }


        private Operand PriorityUnary() { // :NOTE: Style
            SkipWhitespace();
            Operand ans;
            if (test('-')) {
                take();
                if (between('0', '9')) {
                    ans = priorityConst(true);
                } else if (between('a', 'z') || between('A', 'Z')) {
                    ans = new UnaryMinus(priorityVariable());
                } else if (take('(')) {
                    ans = new UnaryMinus(PriorityLow());
                    take();
                } else if (Character.isWhitespace(ch) || ch == '-') {
                    ans = new UnaryMinus(PriorityUnary());
                } else {
                    throw new RuntimeException("error");
                }
            } else {
//                take();
                if (between('0', '9')) {
                    ans = priorityConst(false);
                } else if (between('a', 'z') || between('A', 'Z')) {
                    ans = priorityVariable();
                } else if (take('(')) {
                    ans = PriorityLow();
                    take();
                } else {
                    throw new RuntimeException("error");
                }
            }
            SkipWhitespace();
            while (test('²')) {
                take();
                ans = new Square(ans);
            }
            SkipWhitespace();
            return ans;
        }

        private Operand priorityConst(boolean flag) {
            StringBuilder sb = new StringBuilder();
            if (flag) {
                sb.append("-");
            }
            while (between('0', '9')) {
                sb.append(take());
            }
            SkipWhitespace();
            return new Const(Integer.parseInt(sb.toString()));
        }

        private Operand priorityVariable() {
            StringBuilder sb = new StringBuilder();
            while (between('a', 'z') || between('A', 'Z')) {
                sb.append(take());
            }
            SkipWhitespace();
            return new Variable(sb.toString());
        }
    }
}
