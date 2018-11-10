package functions;

import java.io.*;

public class FunctionPoint implements Serializable, Externalizable {

    private double x, y;

    public FunctionPoint() {
        x = 0;
        y = 0;
    }


    public FunctionPoint(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public FunctionPoint(FunctionPoint point) {
        x = point.x;
        y = point.y;
    }

    public double getY() {
        return y;
    }

    public double getX() {
        return x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void printPoint() {
        System.out.println("x: " + this.x + " y: " + this.y);
    }

    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeObject(this.x);
        out.writeObject(this.y);
    }

    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        this.x = (double) in.readObject();
        this.y = (double) in.readObject();
    }

    @Override
    public String toString() {
        String ans = "(" + Double.toString(this.x) +"; " + Double.toString(this.y) + ')';
        return ans;
    }

    @Override
    public boolean equals(Object obj) {
        if(this.hashCode() == obj.hashCode()){
            if(this.toString().equals( obj.toString()))
                return true;
        }
        return false;
    }

    @Override
    public int hashCode() {
        long longX = Double.doubleToLongBits(this.x);
        long longY = Double.doubleToLongBits(this.y);
        return (int)( longX^(longX>>>32 ) + 31*((int)( longY^(longY>>>32 )) ));
    }

    public Object clone(){
        return new FunctionPoint(this);
    }
}
