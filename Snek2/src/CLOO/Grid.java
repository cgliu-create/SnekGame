//Christopher Liu
package CLOO;

import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Color;

public class Grid extends JPanel implements Runnable
{
   // a dataset of the squares that compose the snake
   private ArrayList<Square> snake = new ArrayList<Square>();
   // a dataset of the squares that compose food
   private ArrayList<Square> food = new ArrayList<Square>();
   // sets the size of the window
   private static final int WIDTH = 400;
   private static final int HEIGHT = 400;
   // sets the size of the squares
   private static final int squareWIDTH = 20;
   private static final int squareHEIGHT = 20;
   // booleans that determine the direction the snake "moves"
   private boolean movingUp = false;
   private boolean movingDown = false;
   private boolean movingLeft = false;
   private boolean movingRight = true;
   // constructor that creates a black panel, default snake and food, begins animation
	public Grid()
	{
      setVisible(true);
      setBackground(Color.BLACK);
      snake.add(new Square(0,0,true));
      food.add(new Square(60,60,false));
		new Thread(this).start();
	}
	
	public void update(Graphics window)
	{
		paint(window);	
	}
   // draws a rectange for every square in the datasets
	public void paint(Graphics window)
	{
      for (Square f : food) {
         window.setColor(f.getColor());
         window.fillRect(f.getX(), f.getY(), squareWIDTH, squareHEIGHT); 	
         window.setColor(Color.BLACK);
         window.drawRect(f.getX(), f.getY(), squareWIDTH, squareHEIGHT); 
      }
      for (Square s : snake) {
         window.setColor(s.getColor());
         window.fillRect(s.getX(), s.getY(), squareWIDTH, squareHEIGHT); 	
         window.setColor(Color.BLACK);
         window.drawRect(s.getX(), s.getY(), squareWIDTH, squareHEIGHT); 
      }
   }
   // these methods will give the runner/frame this panel's size
   public int getWIDTH(){
      return WIDTH;
   }
   public int getHEIGHT(){
      return HEIGHT;
   }
   // these methods will give the runner/frame the dir the snake is currently moving so the snake doesn't go backwards on itself
   public boolean getmovingUp(){
      return movingUp;
   }
   public boolean getmovingDown(){
      return movingDown;
   }
   public boolean getmovingRight(){
      return movingRight;
   }
   public boolean getmovingLeft(){
      return movingLeft;
   }
   // these methods will give the runner/frame the ability to change the dir the snake is moving
   public void setmovingUp(){
      movingUp = true;
      movingDown = false;
      movingRight = false;
      movingLeft = false;
   }
   public void setmovingDown(){
      movingUp = false;
      movingDown = true;
      movingRight = false;
      movingLeft = false;
   }
   public void setmovingRight(){
      movingUp = false;
      movingDown = false;
      movingRight = true;
      movingLeft = false;
   }
   public void setmovingLeft(){
      movingUp = false;
      movingDown = false;
      movingRight = false;
      movingLeft = true;
   }
   // adds a square to the end of the chosen dataset 
   public void grow(Square s, boolean snake){
      if (snake) {
         this.snake.add(s);
      } else {
         food.add(s);
      }
   }
   // checks if the "head" of the snake is in same spot as food
   // if in the same spot, removes this food and generates new random food
   // if not in the same spot, the snake shortens as it "moves"
   public void eatFood(){
      Square head = snake.get(snake.size()-1);
      Square yum = food.get(0);
      if(head.equals(yum)){
         food.remove(0);
         grow(new Square((squareWIDTH*(int)(Math.random()*(WIDTH/squareWIDTH-1))),squareHEIGHT*(int)(Math.random()*(HEIGHT/squareHEIGHT-1)), false), false);
      }
      if(!(head.equals(yum))){
         snake.remove(0);
      }
   }
   public void run()
   {
   	try
   	{
   		while(true)
   		{
   		   Thread.currentThread();
            Thread.sleep(200);
            // animation and snake movement speed
            if(movingUp == true){
               // if moving up, the snake extends up
               Square head = snake.get(snake.size()-1);
               grow(new Square(head.getX(), head.getY()-squareHEIGHT, true), true);
            }
            if(movingDown == true){
               Square head = snake.get(snake.size()-1);
               grow(new Square(head.getX(), head.getY()+squareHEIGHT, true), true);
            }
            if(movingRight == true){
               Square head = snake.get(snake.size()-1);
               grow(new Square(head.getX()+squareWIDTH, head.getY(), true), true);
            }
            if(movingLeft == true){
               Square head = snake.get(snake.size()-1);
               grow(new Square(head.getX()-squareWIDTH, head.getY(), true), true);
            }
            eatFood();
            playagain();
            repaint();
         }
      }catch(Exception e)
      {
      }
   }	
   // checks if the snake "head" overlaps its "body"
   public boolean checkSelfCollision(){
      Square head = snake.get(snake.size()-1);
      for (int i= 0; i<snake.size()-1; i++) {
          Square body = snake.get(i);
          if(body.equals(head))
              return true;
      }
      return false;
   }
   // checks if the snake is out of bounds
   public boolean checkOutOfBounds(){
      for (Square s : snake) {
         if(s.getX()>WIDTH || s.getX()<0 ||s.getY()>HEIGHT||s.getY()<0)
         return true;
      }
      return false;
   }
   // game over popup
   public void playagain(){
    if(checkSelfCollision() || checkOutOfBounds()){
       JFrame end = new JFrame();
       JOptionPane.showMessageDialog(end, "Game Over");
       String again = JOptionPane.showInputDialog(end, "Would you like to play again");
       if(again.equalsIgnoreCase("yes")||again.equalsIgnoreCase("y")){ // resets game to default
          snake.clear();
          food.clear();
          grow(new Square(0,0,true), true);
          grow(new Square(60,60,false), false);
          setmovingRight();
       } else {
          System.exit(0);
       }
   }
}
}