package functions.meta;

import functions.Function;
import functions.InappropriateFunctionPointException;

public class Scale implements Function {
    private Function func;
    private double k_x, k_y;
    public Scale(Function fun, double kForX, double kForY){
        this.func = fun;
        this.k_x = kForX;
        this.k_y = kForY;
    }

    public double getRightDomainBorder() {
        return k_x >= 0 ? k_x * this.func.getRightDomainBorder() : k_x * this.func.getLeftDomainBorder();
    }

    public double getLeftDomainBorder() {
        return k_x >= 0 ? k_x * this.func.getLeftDomainBorder() : k_x * this.func.getRightDomainBorder();
    }

    public double getFunctionValue(double x)throws InappropriateFunctionPointException{
        return k_y * this.func.getFunctionValue(k_x * x);
    }
}
