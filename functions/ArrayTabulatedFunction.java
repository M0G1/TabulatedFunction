package functions;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

public class ArrayTabulatedFunction implements TabulatedFunction {
    private FunctionPoint[] points;
    private int countPoints = 0;

    // try
// Вспомогательные функции
    private int findNearestPoint(double x) {                   //find the closest point to the left     //поиск ближайшей точки слева
        int l = 0, r = this.countPoints - 1, m = (r - l) / 2;
        do {
            if (points[m].getX() > x) {
                r = m - 1;
            } else if (points[m].getX() < x) {
                l = m + 1;
            } else {
                return m;
            }
            m = (r + l) / 2;
        } while (r > l);
        return points[m].getX() > x ? m - 1 : m;
    }

    private void initializationArrOnX(double leftX, double rightX, int pointsCount) {   //a function for initialization array of point on coordinat X
        double step = pointsCount > 1 ? ((rightX - leftX) / (pointsCount - 1)) : 0;     //variable for calculating the next of the point X         //переменная для посчета след точки Х

        this.points[pointsCount - 1] = new FunctionPoint(rightX, 0);             //for exact match of the definition area                    //для точности совпадения области определения

        for (int i = 0; i < pointsCount /*- 1*/; ++i) {
            this.points[i] = new FunctionPoint(leftX + i * step, 0);            // assigning values to a point by X only                    //присваивание значений точке только по Х
        }
    }

    //Check Method


    private void checkArgumentsOfConstructor(double leftX, double rightX, int pointsCount) throws IllegalArgumentException {
        if (pointsCount < 2)
            throw new IllegalArgumentException("Size less than 2");
        if (leftX > rightX) {
            throw new IllegalArgumentException("Left border is greater than the right border");
        }
        if (rightX - leftX < Double.MIN_VALUE) {
            throw new IllegalArgumentException("Equal borders");
        }
    }

    private void checkIndex(int index) throws FunctionPointIndexOutOfBoundsException {  //проверка на выходы за границы массива
        if (index < 0) {
            throw new FunctionPointIndexOutOfBoundsException("Index less than 0");
        }
        if (index >= countPoints) {
            throw new FunctionPointIndexOutOfBoundsException("Index more than size of array");
        }
    }

    private void checkBorder(double x, double leftBorder, double rightBorder) throws InappropriateFunctionPointException {
        if (x < leftBorder) {
            throw new InappropriateFunctionPointException("the value of the point x is less than the value x of the left point");
        }
        if (x > rightBorder) {
            throw new InappropriateFunctionPointException("The value of the point x is greater than the value x of the right point");
        }

    }

    //Конструкторы
    public ArrayTabulatedFunction(double leftX, double rightX, int pointsCount) throws IllegalArgumentException {
        //if(leftX > rightX) //ask about it
        checkArgumentsOfConstructor(leftX, rightX, pointsCount);
        this.countPoints = pointsCount;
        this.points = new FunctionPoint[this.countPoints + this.countPoints / 2];          //create an array of points with the number of pointCount   //создание массива из точек количеством pointCount
        initializationArrOnX(leftX, rightX, pointsCount);

    }

    public ArrayTabulatedFunction() {
        this.points = null;
    }

    public ArrayTabulatedFunction(double leftX, double rightX, double[] values) throws IllegalArgumentException {

        checkArgumentsOfConstructor(leftX, rightX, values.length);
        this.countPoints = values.length;
        this.points = new FunctionPoint[this.countPoints + this.countPoints/2];
        initializationArrOnX(leftX, rightX, this.countPoints);

        for (int j = 0; j < this.countPoints; ++j) {
            this.points[j].setY(values[j]);                   // assigning values to a point by Y only                    //присваивание значений точке только по Y
        }
    }

    public ArrayTabulatedFunction(FunctionPoint pointArr[]) throws IllegalArgumentException {
        checkArgumentsOfConstructor(pointArr[0].getX(), pointArr[pointArr.length - 1].getX(), pointArr.length);
        this.points = new FunctionPoint[pointArr.length + pointArr.length/2];
        this.countPoints = pointArr.length;
        for (int i = 0; i < pointArr.length; ++i) {     //проверка на отсортированность по Х входного массива
            if (i > 0 && !(pointArr[i - 1].getX() < pointArr[i].getX())) {
                this.points = null;
                this.countPoints = 0;
                throw new IllegalArgumentException("Not sorted array on X");
            }
            points[i] = new FunctionPoint(pointArr[i]);
        }

    }

//Методы геттеры

    public double getLeftDomainBorder() {
        return this.points[0].getX();                    //return the value of the left boundary of the definition area  //возвращает значение левой границы области определения
    }

    public double getRightDomainBorder() {
        return this.points[this.countPoints - 1].getX();   //return the value of the right boundary of the definition area  //возвращает значение правой границы области определения
    }

    public double getFunctionValue(double x) throws InappropriateFunctionPointException {
        checkBorder(x, this.getLeftDomainBorder(), this.getRightDomainBorder());//check for output from the field definition    //проверка на выход из области определения

        int i = findNearestPoint(x);

        if (i == this.countPoints - 1) {                                //special case of index(end of array)   //специфльный случай индекса конец массива)
            return this.points[i].getY();
        }

        double x1 = this.points[i].getX();                              //for easier access             //для более простого доступа
        double y1 = this.points[i].getY();
        double x2 = this.points[i + 1].getX();
        double y2 = this.points[i + 1].getY();

        double ans = y1 + ((x - x1) * (y2 - y1)) / (x2 - x1);           //calculate the value of the function
        return ans;
    }

    public int getPointCount() {
        return this.countPoints;
    }

    /**
     * index from 0 to n - 1
     */
    public FunctionPoint getPoint(int index) throws FunctionPointIndexOutOfBoundsException {
        checkIndex(index);
        return new FunctionPoint(this.points[index]);
    }

    /**
     * index from 0 to n - 1
     */
    public double getPointY(int index) throws FunctionPointIndexOutOfBoundsException {
        checkIndex(index);
        return points[index].getY();
    }

    /**
     * index from 0 to n - 1r
     */
    public double getPointX(int index) throws FunctionPointIndexOutOfBoundsException {
        checkIndex(index);
        return points[index].getX();
    }

//методы сеттеры

    /**
     * index from 0 to n - 1
     */
    public void setPointX(int index, double x) throws InappropriateFunctionPointException, FunctionPointIndexOutOfBoundsException {
        checkIndex(index);
        checkBorder(x, this.points[index - 1].getX(), this.points[index].getX());
        points[index].setX(x);
    }

    /**
     * index from 0 to n - 1
     */
    public void setPoint(int index, FunctionPoint point) throws InappropriateFunctionPointException, FunctionPointIndexOutOfBoundsException {//задание новой точки
        checkIndex(index);
        checkBorder(point.getX(), this.points[index - 1].getX(), this.points[index + 1].getX());
        //the point is in the interval between adjacent points on the index   //точка находится в интервале меж соседних точек по индексу
        this.points[index] = new FunctionPoint(point);
    }

    /**
     * index from 0 to n - 1
     */
    public void setPointY(int index, double y) throws FunctionPointIndexOutOfBoundsException {
        checkIndex(index);
        points[index].setY(y);
    }

//Удаление добавление

    /**
     * index from 0 to n - 1
     */
    public void deletePoint(int index) throws FunctionPointIndexOutOfBoundsException, IllegalStateException {
        checkIndex(index);
        if (this.countPoints < 3) {
            throw new IllegalStateException("In function of less than 3 points");
        }
        System.arraycopy(this.points, index + 1, this.points, index, this.countPoints - 1 - index);
        --this.countPoints;
        this.points[this.countPoints] = null;
    }

    public void addPoint(FunctionPoint point) throws InappropriateFunctionPointException {
        if (this.points.length - countPoints > 0) {                         //если есть место в массиве
            if (point.getX() < this.getLeftDomainBorder()) {                //если точка за левой границей
                System.arraycopy(this.points, 0, this.points, 1, this.countPoints);  //сдвинуть массив на 1
                this.points[0] = point;                                                         //положить точку в начало
            } else if (this.getRightDomainBorder() < point.getX()) {        //если точка за правой границей
                this.points[this.countPoints] = point;                                //добавить точку
            } else {                                                            //точка лежит в пределах границ масссива
                int index = findNearestPoint(point.getX());                     //ищем ближайшую точку слева(её индекс)
                if (this.points[index].getX() - point.getX() <= Double.MIN_VALUE) {       //если эта точка совпадает с той, что есть в массиве
                    throw new InappropriateFunctionPointException("Еhe point with the given value x is already available");
                }
                System.arraycopy(this.points, index + 1, this.points, index + 2, this.countPoints - index);        //сдвигаем массив на 1 начиная после точки с индекса
                this.points[index + 1] = point;
            }
        } else {
            FunctionPoint newPoints[] = new FunctionPoint[this.countPoints + this.countPoints >>> 1];          //нет в массиве места НУЖНО БОЛЬШЕ МЕСТА!
            if (point.getX() < this.getLeftDomainBorder()) {                               //если точка за левой границей
                System.arraycopy(this.points, 0, newPoints, 1, this.countPoints);       //копировать массив в новый оставив в начале место
                newPoints[0] = point;                                                           //положить точку в начало
            } else if (this.getRightDomainBorder() < point.getX()) {                            //если точка за правой границей
                System.arraycopy(this.points, 0, newPoints, 0, this.countPoints);         //копируем массив
                newPoints[this.countPoints] = point;                                                //кладем точку в конец
            } else {
                int index = findNearestPoint(point.getX());                                 //ищем ближайшую точку слева(её индекс)
                if (this.points[index].getX() - point.getX() < Double.MIN_VALUE) {            //если эта точка совпадает с той, что есть в массиве
                    new InappropriateFunctionPointException("Еhe point with the given value x is already available");
                }
                System.arraycopy(this.points, 0, newPoints, 0, index + 1);      //копируем первую часть массива
                newPoints[index + 1] = point;                                                        //вставляем точку
                System.arraycopy(this.points, index + 1, newPoints, index + 2, this.countPoints - index - 1);     //копируем остаток
            }
            this.points = newPoints;
        }
        ++this.countPoints;
    }

    //печать в консоль
    public void printTabulatedFunction() {
        int i = 1;
        while (i <= this.countPoints) {
            int p = i + 9 < this.countPoints ? i + 9 : this.countPoints, j = i;         //если индекс не больше размера, то мы печатаем 10 нет до размера
            System.out.print("" + i + '-' + p + '\n' + " x:  ");
            for (; i <= p; ++i) {                                                       //печатаем по Х
                System.out.print(this.points[i - 1].getX() + " ");
            }

            System.out.print("\n y:  ");

            for (; j <= p; ++j) {                                                      //печатам по У
                System.out.print(this.points[j - 1].getY() + " ");
            }
            System.out.println();
        }
    }

    //сериализация десериализация

    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeInt(this.countPoints);
        for (int i = 0; i < this.countPoints; ++i) {
            out.writeObject(points[i]);
        }
    }

    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {//
        int size = in.readInt();
        this.points = new FunctionPoint[size + 10];
        for (int i = 0; i < size; ++i) {
            this.points[i] = (FunctionPoint) in.readObject();
        }
    }

    @Override
    public String toString() {
        String str = "{ ";
        for (int i = 0; i < this.countPoints; ++i) {
            str += points[i].toString();
            if (i != this.countPoints - 1)
                str += ", ";
        }
        return (str += " }");
    }

    @Override
    public int hashCode() {
        int hashCode = this.countPoints;
        int power = 1;
        for (int i = 0; i < this.countPoints; ++i, power *= 17) {
            hashCode += power * this.points[i].hashCode();
        }
        return hashCode;
    }

    @Override
    public boolean equals(Object obj) {
        if (this.hashCode() == obj.hashCode()) {
            if (this.toString().equals(obj.toString())) {
                return true;
            }
        }
        return false;
    }

    public Object clone() {
        ArrayTabulatedFunction clone = new ArrayTabulatedFunction();
        clone.points = new FunctionPoint[this.points.length];
        clone.countPoints = this.countPoints;
        for (int i = 0; i < this.countPoints; ++i) {
            clone.points[i] = (FunctionPoint) this.points[i].clone();
        }
        for (int i = this.countPoints; i < this.points.length; ++i) {
            clone.points[i] = null;
        }
        return clone;
    }

}
