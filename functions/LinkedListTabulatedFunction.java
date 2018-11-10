package functions;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
//import java.sql.SQLOutput;

public class LinkedListTabulatedFunction implements TabulatedFunction {
    private class FunctionNode {
        public FunctionNode next, prev;
        public FunctionPoint val;

        FunctionNode() {
            this.next = this.prev = null;
            val = null;
        }

        FunctionNode(FunctionPoint point) {
            this.val = point;
            this.next = this.prev = null;
        }

        FunctionNode(FunctionNode prev, FunctionPoint val, FunctionNode next) {
            this.prev = prev;
            this.val = val;
            this.next = next;
            prev.next = this;
            next.prev = this;
        }
    }

    private FunctionNode head;
    private int size = 0;

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

    private void checkIndex(int index) throws FunctionPointIndexOutOfBoundsException {  //проверка а выходы за границы массива
        if (index < 0) {
            throw new FunctionPointIndexOutOfBoundsException("Index less than 0");
        }
        if (index >= size) {
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

    //вспомогательные методы

    /**
     * nearest point on left of point
     */
    private int findNearestPoint(double x) {
        FunctionNode cur;
        int i;
        if (x - this.getLeftDomainBorder() < this.getRightDomainBorder() - x) {

            for (i = 0, cur = this.head.next; cur.val.getX() < x; cur = cur.next, ++i)
                ;                                                                       //идем по списку вперед пока значение по иксу больше нужного значения
            if (cur.val.getX() - x < Double.MIN_VALUE) {
                return i;
            } else {
                --i;
            }
        } else {
            i = size - 1;
            if (!(getRightDomainBorder() - x < Double.MIN_VALUE)) {             //если это не правая граница
                for (cur = this.head.prev; x < cur.val.getX(); cur = cur.prev, --i)
                    ;                                                                               //идем по списку назад пока значение по иксу меньше нужного значения
            }
        }
        return i;
    }


    //конструкторы
    public LinkedListTabulatedFunction(double leftX, double rightX, int pointsCount) throws IllegalArgumentException {
        checkArgumentsOfConstructor(leftX, rightX, pointsCount);
        double step = pointsCount > 1 ? ((rightX - leftX) / (pointsCount - 1)) : 0;

        for (int i = 0; i < pointsCount; ++i) {
            FunctionNode cur = addNodeToTail();
            cur.val.setX(leftX + i * step);
        }
    }

    public LinkedListTabulatedFunction(double leftX, double rightX, double[] values) throws IllegalArgumentException {
        checkArgumentsOfConstructor(leftX, rightX, values.length);
        double step = values.length > 1 ? ((rightX - leftX) / (values.length - 1)) : 0;

        for (int i = 0; i < values.length; ++i) {
            FunctionNode cur = addNodeToTail();
            cur.val.setX(leftX + i * step);
            cur.val.setY(values[i]);
        }
    }

    public LinkedListTabulatedFunction() {
        this.head = null;
    }

    public LinkedListTabulatedFunction(FunctionPoint points[]) throws IllegalArgumentException {
        checkArgumentsOfConstructor(points[0].getX(), points[points.length - 1].getX(), points.length);
        for (int i = 0; i < points.length; ++i) {                   //проверка на отсортированность по Х входного массива и запись в лист
            if (i > 0 && !(points[i - 1].getX() < points[i].getX())) {
                this.head = new FunctionNode();
                throw new IllegalArgumentException("Not sorted array on X");
            }
            FunctionNode cur = this.addNodeToTail();
            cur.val = new FunctionPoint(points[i]);
        }

    }


    //Методы

    /**
     * index from 0 to n-1
     */
    public FunctionNode getNodeByIndex(int index) throws FunctionPointIndexOutOfBoundsException {
        checkIndex(index);
        FunctionNode cur;
        if (index < size - 1 - index) { //смотрим как ближе дойти
            cur = this.head.next;
            for (int i = 0; i < index; ++i) {   //идем через next вперед
                cur = cur.next;
            }
        } else {
            cur = this.head.prev;
            for (int i = size - 1; i > index; --i) {  //идем через prev вперед
                cur = cur.prev;
            }
        }
        return cur;

    }

    public FunctionNode addNodeToTail() {
        if (size == 0) {
            FunctionNode nextPrevHead = new FunctionNode(new FunctionPoint((this.size > 0 ? this.getRightDomainBorder() + 1 : 0), 0));
            this.head = new FunctionNode(nextPrevHead, null, nextPrevHead); //зацикливание списка на голове
        } else {
            this.head.prev.next = new FunctionNode(this.head.prev, new FunctionPoint(this.getRightDomainBorder() + 1, 0), this.head); //к последнему целяем новый элемент, который ссылается на голову
        }
        ++this.size;
        return this.head.prev;
    }

    /**
     * index from 0 to n-1
     */
    public FunctionNode addNodeByIndex(int index) throws FunctionPointIndexOutOfBoundsException {
        checkIndex(index);
        FunctionNode onIndex = getNodeByIndex(index);

        FunctionNode prev = onIndex.prev;
        double x_average = 0;
        if (index == 0) { //поддержка отсортированности по х
            x_average = this.getLeftDomainBorder() - 1;             //для случая левой границы (голова не имеет value)
        } else if (index == this.size - 1) {
            x_average = this.getRightDomainBorder() + 1;            //для правой границы (голова не имеет value)
        } else {
            x_average = (prev.val.getX() + onIndex.val.getX()) / 2;    //когда нужно добавить между двумя точками
        }

        FunctionNode newNode = new FunctionNode(prev, new FunctionPoint(x_average, 0), onIndex);
        return new FunctionNode(newNode.val);
    }

    FunctionNode deleteNodeByIndex(int index) throws FunctionPointIndexOutOfBoundsException, IllegalStateException {
        checkIndex(index);
        if (this.size < 3) {
            throw new IllegalStateException("In function of less than 3 points");
        }
        FunctionNode onIndex = getNodeByIndex(index);
        onIndex.prev.next = onIndex.next;               //элемент перед индексом ссылается на элемент после индекса
        onIndex.next.prev = onIndex.prev;               // элемент после индекса ссылается на элемент перед индексом
        onIndex.prev = null;
        onIndex.prev = null;
        --size;
        return onIndex;
    }

    //методы геттеры

    static final public FunctionPoint getFunctionPoint(FunctionNode node) {
        return new FunctionPoint(node.val);
    }

    public double getLeftDomainBorder() {
        return this.head.next.val.getX();
    }

    public double getRightDomainBorder() {
        return this.head.prev.val.getX();
    }

    public double getFunctionValue(double x) throws InappropriateFunctionPointException {
        checkBorder(x, this.getLeftDomainBorder(), this.getRightDomainBorder());//check for output from the field definition    //проверка на выход из области определения

        int index = findNearestPoint(x);
        FunctionNode nearestNode = this.getNodeByIndex(index);
        if (Math.abs(nearestNode.val.getX() - x) < Double.MIN_VALUE) {
            return nearestNode.val.getY();
        }

        double x1 = nearestNode.val.getX();     //значения точек
        double y1 = nearestNode.val.getY();
        double x2 = nearestNode.next.val.getX();
        double y2 = nearestNode.next.val.getY();

        double ans = y1 + ((x - x1) * (y2 - y1)) / (x2 - x1);

        return ans;
    }

    public int getPointCount() {
        return this.size;
    }

    public FunctionPoint getPoint(int index) throws FunctionPointIndexOutOfBoundsException {
        checkIndex(index);
        return new FunctionPoint(this.getNodeByIndex(index).val);
    }

    public double getPointY(int index) throws FunctionPointIndexOutOfBoundsException {
        checkIndex(index);
        return this.getNodeByIndex(index).val.getY();
    }

    public double getPointX(int index) throws FunctionPointIndexOutOfBoundsException {
        checkIndex(index);
        return this.getNodeByIndex(index).val.getX();
    }

    //методы сеттеры

    public void setPointX(int index, double x) throws InappropriateFunctionPointException, FunctionPointIndexOutOfBoundsException {
        checkIndex(index);
        FunctionNode node = this.getNodeByIndex(index);
        checkBorder(x, node.prev.val.getX(), node.val.getX());
        node.val.setX(x);
    }

    public void setPoint(int index, FunctionPoint point) throws InappropriateFunctionPointException, FunctionPointIndexOutOfBoundsException {//задание новой точки
        checkIndex(index);
        FunctionNode node = this.getNodeByIndex(index);
        checkBorder(point.getX(), node.prev.val.getX(), node.val.getX());
        node.val = new FunctionPoint(point);
    }

    public void setPointY(int index, double y) throws FunctionPointIndexOutOfBoundsException {
        checkIndex(index);
        FunctionNode node = this.getNodeByIndex(index);
        node.val.setY(y);
    }

    public void addPoint(FunctionPoint point) throws InappropriateFunctionPointException {

        FunctionNode node;
        double x = point.getX();
        if (x - this.getLeftDomainBorder() < this.getRightDomainBorder() - x) {
            node = this.head.next;
            for (; node.val.getX() < x; node = node.next)
                ;                                                                       //идем по списку вперед пока значение по иксу больше нужного значения
        } else {
            node = this.head.prev;

            for (; x < node.val.getX(); node = node.prev)
                ;                                                                               //идем по списку назад пока значение по иксу меньше нужного значения

        }
        if (Math.abs(node.val.getX() - x) < Double.MIN_VALUE) {
            throw new InappropriateFunctionPointException("A list have this point");
        }
        if (node.val.getX() > x) {
            node = node.prev;
        }
        new FunctionNode(node, point, node.next);
        ++size;
    }

    public void deletePoint(int index) throws FunctionPointIndexOutOfBoundsException, IllegalStateException {
        this.deleteNodeByIndex(index);
    }

    //печать Функции

    public void printTabulatedFunction() {
        int i = 1;
        FunctionNode cur = this.head.next;
        while (i <= this.size) {
            FunctionNode forY = cur;                                    //переменая для хождения по значению функции
            int p = i + 9 < this.size ? i + 9 : this.size, j = i;       //проверка на выход за границы массива

            System.out.print("" + i + '-' + p + '\n' + " x:  ");
            for (; i <= p; ++i, cur = cur.next) {                   //по Х
                System.out.print(cur.val.getX() + " ");
            }

            System.out.print("\n y:  ");

            for (; j <= p; ++j, forY = forY.next) {                //по У
                System.out.print(forY.val.getY() + " ");
            }

            System.out.println();
        }
    }

    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeInt(this.size);
        for (FunctionNode cur = this.head.next; cur != this.head; cur = cur.next) {
            out.writeObject(cur.val);
        }
    }

    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        int size = in.readInt();
        for (int i = 0; i < size; ++i) {
            this.addNodeToTail().val = (FunctionPoint) in.readObject();
        }
    }

    @Override
    public String toString() {
        String str = "{ ";
        FunctionNode cur = this.head;
        for (int i = 0; i < this.size; ++i, cur = cur.next) {
            str += cur.next.val.toString();
            if (cur.next.next != head) {
                str += ", ";
            }
        }
        return (str += " }");
    }

    public boolean equals(Object obj) {
        if (this.hashCode() == obj.hashCode()) {
            if (this.toString().equals(obj.toString())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hashCode = this.size;
        int power = 1;
        FunctionNode cur = this.head;
        for (int i = 0; i < this.size; ++i, power *= 17, cur = cur.next) {
            hashCode += power * cur.next.val.hashCode();
        }
        return hashCode;
    }

    public Object clone() {
        LinkedListTabulatedFunction clone = new LinkedListTabulatedFunction();
        clone.size = this.size;     //задаем размер
        clone.head = new FunctionNode();    //создаем голову
        FunctionNode cloneCur = clone.head; //переменная бегунок по клону
        cloneCur.val = null;                //иниициализируем значение головы
        FunctionNode thisCur = this.head;   //переменная бегунок по текущему объекту
        for (int i = 0; i < this.size; ++i, cloneCur = cloneCur.next, thisCur = thisCur.next) { //идем по двум спискам
            cloneCur.next = new FunctionNode();                             //создаем следующую за текущей точку
            cloneCur.next.val = (FunctionPoint) thisCur.next.val.clone();   //клонирование значения
            cloneCur.next.prev = cloneCur;                                  // говорим, что перед следующей точкой эта
        }
        cloneCur.next = clone.head;                                 //замыкаем список на голове
        return clone;
    }
}
