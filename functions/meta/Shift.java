package functions.meta;

import functions.Function;
import functions.InappropriateFunctionPointException;

public class Shift implements Function {
    private Function func;
    private double shiftX, shiftY;

    public Shift(Function fun, double shiftOnX, double shiftOnY){
        this.func = fun;
        this.shiftX = shiftOnX;
        this.shiftY = shiftOnY;
    }

    public double getRightDomainBorder() {
        return this.func.getRightDomainBorder() + this.shiftX;
    }

    public double getLeftDomainBorder() {
        return this.func.getLeftDomainBorder() + this.shiftX;
    }

    public double getFunctionValue(double x)throws InappropriateFunctionPointException {
        return this.func.getFunctionValue(x+this.shiftX) + this.shiftY;
    }
}
