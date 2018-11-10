package functions.meta;

import functions.Function;
import functions.InappropriateFunctionPointException;

public class Sum implements Function {
    private Function func1, func2;
    private double leftDomain , rightDomain;

    public Sum(Function fu1,Function fu2){
        this.func1 = fu1;
        this.func2 = fu2;
        this.leftDomain = Math.max(this.func1.getLeftDomainBorder(),this.func2.getLeftDomainBorder());
        this.rightDomain = Math.min(this.func1.getRightDomainBorder(),this.func2.getRightDomainBorder());
        if(this.leftDomain > this.rightDomain){
            this.rightDomain = this.leftDomain = Double.NaN;
        }
    }

    public double getLeftDomainBorder() {
        return leftDomain;}

    public double getRightDomainBorder() {
        return rightDomain; }

    public double getFunctionValue(double x) throws  InappropriateFunctionPointException {
        return this.func1.getFunctionValue(x) + this.func2.getFunctionValue(x);
    }
}
