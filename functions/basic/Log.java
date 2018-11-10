package functions.basic;

import functions.Function;
import functions.InappropriateFunctionPointException;

public class Log implements Function {
    private double base;

    public Log(double a) throws InappropriateFunctionPointException {
        if(Math.abs(1 - a) < Double.MIN_VALUE)
            throw new InappropriateFunctionPointException("Base of log is 1");
        this.base = a;
    }

    public double getRightDomainBorder() {
        return Double.POSITIVE_INFINITY;
    }

    public double getLeftDomainBorder() {
        return Double.MIN_VALUE;
    }

    public double getFunctionValue(double x) throws InappropriateFunctionPointException {
        if (x < Double.MIN_VALUE) {
            throw new InappropriateFunctionPointException("value out of scope");
        }
        return Math.log(x) /Math.log(base) ;
    }
}
