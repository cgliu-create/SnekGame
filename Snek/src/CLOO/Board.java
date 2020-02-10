//Christopher Liu
package CLOO;

import java.awt.Graphics;
import java.util.ArrayList;
import java.awt.Color;
import java.awt.Canvas;

public class Board extends Canvas{
   // this draws rectangles based off the points it is given
   private ArrayList<Point> data; // snake
   private ArrayList<Point> food; // food

   public Board(ArrayList<Point> points, ArrayList<Point> yum)   
   {
      setSize(400, 400);
      setBackground(Color.BLACK);
      setVisible(true);
      this.data = points;
      this.food = yum;
   }

   public void paint( Graphics window )
   {
      Graphit(window);
   }

   public void Graphit( Graphics window )
   {
      for (Point f : food) {
         window.setColor(Color.RED);
         window.fillRect(f.getXcord(), f.getYcord(), 20, 20);
      }
      for (Point p : data) {
         window.setColor(Color.GREEN);
         window.fillRect(p.getXcord(), p.getYcord(), 20, 20);
         window.setColor(Color.BLACK);
         window.drawRect(p.getXcord(), p.getYcord(), 20, 20);
      }
   }
}