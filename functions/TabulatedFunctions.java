package functions;

import java.io.*;
import java.util.stream.Stream;

public abstract class TabulatedFunctions {

    //вспомогательные функции
    private static void checkArgumentsOfConstructor(double leftX, double rightX, int pointsCount) throws IllegalArgumentException {
        if (pointsCount < 2)
            throw new IllegalArgumentException("Size less than 2");
        if (leftX > rightX) {
            throw new IllegalArgumentException("Left border is greater than the right border");
        }
        if (rightX - leftX < Double.MIN_VALUE) {
            throw new IllegalArgumentException("Equal borders");
        }
    }

    private static void checkBorder(double x, double leftBorder, double rightBorder) throws InappropriateFunctionPointException {
        if (x < leftBorder) {
            throw new InappropriateFunctionPointException("the value of the point x is less than the value x of the left point");
        }
        if (x > rightBorder) {
            throw new InappropriateFunctionPointException("The value of the point x is greater than the value x of the right point");
        }
    }
    //по заданию

    public static TabulatedFunction tabulate(Function function, double leftX, double rightX, int pointsCount) throws IllegalArgumentException, InappropriateFunctionPointException {// получающий функцию и возвращающий её табулированный аналог на заданном отрезке с заданным количеством точек.
        checkArgumentsOfConstructor(leftX, rightX, pointsCount);
        checkBorder(leftX, function.getLeftDomainBorder(), function.getRightDomainBorder());
        checkBorder(rightX, function.getLeftDomainBorder(), function.getRightDomainBorder());

        double step = (rightX - leftX) / (pointsCount - 1);
        double funcVal[] = new double[pointsCount];
        for (int i = 0; i < pointsCount; ++i) {
            funcVal[i] = function.getFunctionValue(leftX + i * step);
        }

        return new LinkedListTabulatedFunction(leftX, rightX, funcVal);
    }

    public static void outputTabulatedFunction(TabulatedFunction function, OutputStream out) throws IOException {
        DataOutputStream output = new DataOutputStream(out);
        output.writeInt(function.getPointCount());
        for (int i = 0; i < function.getPointCount(); ++i) {
            FunctionPoint cur = function.getPoint(i);
            output.writeDouble(cur.getX());
            output.writeDouble(cur.getY());
        }
    }

    public static TabulatedFunction inputTabulatedFunction(InputStream in) throws IOException {
        DataInputStream input = new DataInputStream(in);        //обертка потока
        int countPoint = input.readInt();                       //количество точек
        FunctionPoint points[] = new FunctionPoint[countPoint];
        for (int i = 0; i < countPoint; ++i) {
            double x =input.readDouble();
            points[i] = new FunctionPoint(x, input.readDouble()); //чтение и запись точек
        }
        return new LinkedListTabulatedFunction(points);
    }

    public static void writeTabulatedFunction(TabulatedFunction function, Writer out) throws IOException {
        PrintWriter printWr = new PrintWriter(out);
        printWr.print(function.getPointCount());
        printWr.print('\n');
        for (int i = 0; i < function.getPointCount(); ++i) {
            FunctionPoint cur = function.getPoint(i);
            printWr.print(cur.getX());
            printWr.print(' ');
            printWr.print(cur.getY());
            printWr.print('\n');
        }
    }

    public static TabulatedFunction readTabulatedFunction(Reader in) throws IOException {
        StreamTokenizer streamIn = new StreamTokenizer(in);
        //streamIn.parseNumbers();
        streamIn.nextToken();
        FunctionPoint points[] = new FunctionPoint[(int) streamIn.nval];
        for (int i = 0; i < points.length; ++i) {
            streamIn.nextToken();
            points[i] = new FunctionPoint(streamIn.nval,0);
            streamIn.nextToken();
            points[i].setY(streamIn.nval);
        }
        return new LinkedListTabulatedFunction(points);
    }
}
