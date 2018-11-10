package functions.basic;

import functions.Function;
import functions.InappropriateFunctionPointException;

public abstract class TrigonometricFunction implements Function {

    public double getLeftDomainBorder() {
        return Double.NEGATIVE_INFINITY;
    }

    public double getRightDomainBorder() {
        return Double.POSITIVE_INFINITY;
    }

    public double getFunctionValue(double x) throws InappropriateFunctionPointException {
        if (true) {
            throw new InappropriateFunctionPointException("Unknown Trigonometric Function");
        }
        return 0;
    }
}
