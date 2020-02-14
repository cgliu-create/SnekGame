package CLOO;
import java.awt.Color;

public class Square {
    //object that holds info for the cooordinates and color of a square
    private int xPos;
    private int yPos;
    private Color type;

    public Square(int x, int y, Color c){
		xPos = x;
        yPos = y;
        type = c;
    }
    public int getX(){
   	return xPos;
    }
    public int getY(){
   	return yPos;
    }
    public Color getColor(){
   	return type;
    }
    public void setColor(Color c){
         type = c;
     }

    public boolean equals(Object thing){
        Square other = (Square)thing;
        if(this.xPos == other.xPos && this.yPos == other.yPos){
            return true;
        }
        return false;
    } 
}