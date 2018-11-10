package functions.meta;

import functions.Function;
import functions.InappropriateFunctionPointException;

public class Composition implements Function {
    private Function outerFunc;
    private Function innerFunc;
    public Composition(Function externalFunction, Function internalFunction){
        this.outerFunc =externalFunction;
        this.innerFunc =internalFunction;
    }
    public double getLeftDomainBorder(){
        return this.innerFunc.getLeftDomainBorder();
    }
    public double getRightDomainBorder(){
        return this.innerFunc.getRightDomainBorder();
    }
    public double getFunctionValue(double x)throws InappropriateFunctionPointException {
       return this.outerFunc.getFunctionValue( this.innerFunc.getFunctionValue(x) );
    }
}
