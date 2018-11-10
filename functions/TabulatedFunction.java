package functions;

import java.io.Externalizable;
import java.io.Serializable;

public interface TabulatedFunction extends Function , Serializable, Externalizable {
    int getPointCount();

    FunctionPoint getPoint(int index) throws FunctionPointIndexOutOfBoundsException;

    double getPointY(int index) throws FunctionPointIndexOutOfBoundsException;

    double getPointX(int index) throws FunctionPointIndexOutOfBoundsException;

    void setPointX(int index, double x) throws InappropriateFunctionPointException, FunctionPointIndexOutOfBoundsException;

    void setPoint(int index, FunctionPoint point) throws InappropriateFunctionPointException, FunctionPointIndexOutOfBoundsException;

    void setPointY(int index, double y) throws FunctionPointIndexOutOfBoundsException;

    void addPoint(FunctionPoint point) throws InappropriateFunctionPointException;

    void deletePoint(int index) throws FunctionPointIndexOutOfBoundsException, IllegalStateException;

    Object clone();
}
