import functions.*;
import functions.basic.Log;

import java.io.*;
import java.lang.reflect.Array;


public class MainClass {
    public static void main(String[] args) throws InappropriateFunctionPointException, FunctionPointIndexOutOfBoundsException, IllegalArgumentException, IOException, ClassNotFoundException {

        LinkedListTabulatedFunction line1 = new LinkedListTabulatedFunction(1, 10, 10);
        ArrayTabulatedFunction line2 = new ArrayTabulatedFunction(1, 10, 10);
        ArrayTabulatedFunction lineClone = (ArrayTabulatedFunction) line2.clone();
        LinkedListTabulatedFunction func = (LinkedListTabulatedFunction) TabulatedFunctions.tabulate(new Log(Math.E), 1, 10, 5);
        LinkedListTabulatedFunction funcClone = (LinkedListTabulatedFunction) func.clone();

        System.out.println("line1.toString() " + line1.toString());
        System.out.println("line2.toString() " + line2.toString());
        System.out.println("lineClone.toString() " + lineClone.toString());
        System.out.println("func.toString() " + func.toString());
        System.out.println("funcClone.toString() " + funcClone.toString() +'\n');

        System.out.println("line1.hashCode() " + line1.hashCode() + '\n' + "line2.hashCode() " + line2.hashCode());
        System.out.println("func.hashCode() " + func.hashCode());

        System.out.println("line2.equals(line1) " + line2.equals(line1));
        System.out.println("line1.equals(line2) " + line1.equals(line2));
        System.out.println("func.equals(line1) " + func.equals(line1) + '\n');

        func.setPointY(1, func.getPointY(1) + 0.000_000_000_000_001);
        System.out.println("func.toString() " + func.toString());
        System.out.println("funcClone.equals(func) " + funcClone.equals(func));
        System.out.println("func.hashCode() " + func.hashCode());
    }
}
