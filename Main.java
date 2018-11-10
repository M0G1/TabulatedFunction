import functions.*;

public class Main {
    public static void main(String[] args) {
        try {
            //FunctionPoint test tast 2
            System.out.println("FunctionPoint test task 2");
            FunctionPoint p = new FunctionPoint();
            p.printPoint();
            FunctionPoint p2 = new FunctionPoint(2, 3);
            p2.printPoint();
            p2.setX(p2.getY());
            p2.setY(2);
            p2.printPoint();
            FunctionPoint p3 = new FunctionPoint(p2);
            p3.printPoint();

            //ArrayTabulatedFunction test task 3
            System.out.println("\nArrayTabulatedFunction test task 3");
            ArrayTabulatedFunction fu1 = new ArrayTabulatedFunction(1, 4, 5);
            fu1.printTabulatedFunction();
            double arr[] = {1, 4, 9, 16, 2, 46, 5, 54, 44, 76, 11};
            ArrayTabulatedFunction fu2 = new ArrayTabulatedFunction(1, 11, arr);
            fu2.printTabulatedFunction();

            //task 4
            System.out.println("\ntask4");
            System.out.println("fu2.getLeftDomainBorder() " + fu2.getLeftDomainBorder() + '\n' + "fu1.getRightDomainBorder()" + fu1.getRightDomainBorder());
            System.out.println("fu2.getFunctionValue(double x = 2.5) " + fu2.getFunctionValue(2.5) + " \nfu2.getFunctionValue(double x = -1) " + fu2.getFunctionValue(-1));
            System.out.println("fu2.getFunctionValue(double x = 12) " + fu2.getFunctionValue(12));
            System.out.println("x =1.1 " + fu2.getFunctionValue(1.1) + "x =1.2 " + fu2.getFunctionValue(1.2));

            //task 5
            System.out.println("\ntask5");
            System.out.println("fu2.getPointsCount() " + fu2.getPointCount());
            System.out.println("fu2.getPoint(int index = 6) ");
            fu2.getPoint(6).printPoint();
            //setPoint
            fu2.printTabulatedFunction();
            System.out.println("fu.2setPoint(int index = 2, FunctionPoint point = (2.5 , 6.25)");
            fu2.setPoint(2, new FunctionPoint(2.5, 6.25));
            fu2.printTabulatedFunction();

            System.out.println("getPointX(int index = 9)" + fu2.getPointX(9));
            System.out.println("getPointX(int index = 9)" + fu2.getPointY(9));

            fu2.setPointX(4, 4.7);
            fu2.setPointY(4, -1);
            System.out.println("fu2.setPointX (4,4.7)/Y(4,-1)");
            System.out.println("fu2.getPointX/Y(4)" + fu2.getPointX(4) + ' ' + fu2.getPointY(4));

            //task 6
            System.out.println("\ntask6");

            fu1.printTabulatedFunction();
            System.out.println("fu1.addPoint(new FunctionPoint(3.9,5))");
            fu1.addPoint(new FunctionPoint(3.9, 5));
            fu1.printTabulatedFunction();
            System.out.println();

            fu2.printTabulatedFunction();
            System.out.println("fu2.deletePoint(10)");
            fu2.deletePoint(10);
            ;
            fu2.printTabulatedFunction();
            System.out.println();

            System.out.println("fu2.addPoint(new FunctionPoint(-1,1))");
            fu2.addPoint(new FunctionPoint(-1, 1));
            fu2.printTabulatedFunction();
            System.out.println();

            System.out.println("fu2.addPoint(new FunctionPoint(11,121))");
            fu2.addPoint(new FunctionPoint(11, 121));
            fu2.printTabulatedFunction();
            System.out.println();

            System.out.println("fu2.addPoint(new FunctionPoint(11,122))");
            fu2.addPoint(new FunctionPoint(11, 122));
            fu2.printTabulatedFunction();
            System.out.println();

            fu2.printTabulatedFunction();
            System.out.println("fu2.deletePoint(10)");
            fu2.deletePoint(10);
            fu2.printTabulatedFunction();
            System.out.println();

            System.out.println("fu2.addPoint(new FunctionPoint(-1,1))");
            fu2.addPoint(new FunctionPoint(9, 122));
            fu2.printTabulatedFunction();
            System.out.println();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}
