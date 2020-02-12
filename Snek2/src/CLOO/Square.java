package CLOO;
import java.awt.Color;

public class Square {
    //object that holds info for the cooordinates and color of a square
    private int xPos;
	private int yPos;

    private Color color; 
    public Square(int x, int y, boolean snake){
		xPos = x;
        yPos = y;
        if (snake) {
            color = Color.GREEN;
        } else {
            color = Color.RED;
        }
    }
    public int getX(){
   	return xPos;
    }
    public int getY(){
   	return yPos;
    }
    public Color getColor(){
   	return color;
    }

    public boolean equals(Object thing){
        Square other = (Square)thing;
        if(this.xPos == other.xPos && this.yPos == other.yPos){
            return true;
        }
        return false;
    } 
}