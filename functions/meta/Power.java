package functions.meta;

import functions.Function;
import functions.InappropriateFunctionPointException;

public class Power implements Function{        //возводить в степень числа или в степень функции
    private Function fun;
    private double pow;
    public Power(Function func, double val){
        this.fun = func;
        this.pow = val;
    }

    public double getLeftDomainBorder() {
        return this.fun.getLeftDomainBorder();
    }

    public double getRightDomainBorder() {
        return this.fun.getRightDomainBorder();
    }

    public double getFunctionValue(double x) throws InappropriateFunctionPointException{
        return Math.pow(this.fun.getFunctionValue(x),this.pow);
    }
}
