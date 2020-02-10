//Christopher Liu
package CLOO;

import java.util.ArrayList;

public class Point{
    // this stores the location and data for a square
    private int Xcord;
    private int Ycord;
    public Point(){
        this.Xcord = 0;
        this.Ycord = 0;
    }
    public Point(int x, int y){
        this.Xcord = x;
        this.Ycord = y;
    }
    public int getXcord(){
        return this.Xcord;
    }
    public int getYcord(){
        return this.Ycord;
    }
    public boolean equals(Object thing){
        Point other = (Point)thing;
        if(this.Xcord == other.Xcord && this.Ycord == other.Ycord){
            return true;
        }
        return false;
    }   
}