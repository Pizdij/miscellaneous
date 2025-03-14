package expression;

public interface Operand extends Expression, ToMiniString, LongMapExpression, TripleExpression {
    String getOperator();
    int getPriority();
    void toMiniString(StringBuilder result);
}
