//Christopher Liu
package CLOO;

import java.util.ArrayList;
import java.awt.Container;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Runner {

    private ArrayList<Point> snake = new ArrayList<Point>(); // the snake
	private JFrame frame;
	private ArrayList<Point> food = new ArrayList<Point>(); // food

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Runner window = new Runner();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Runner() {
		// default start points for snake and food
		snake.add(new Point(20,20)); 
		food.add(new Point(100,100));
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		// frame for main gui
		frame = new JFrame();
        frame.setBounds(0, 0, 400, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
        // panel for where the board draws
		JPanel thepanel = new JPanel();
        thepanel.setBounds(0, 0, 400, 400);
        frame.add(thepanel, new Board(snake, food));
        frame.getContentPane().add(thepanel);
		//up
		JButton btnUp = new JButton("U");
		btnUp.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
                Point front = snake.get(snake.size()-1); // current "head"/end of list
				snake.add(new Point(front.getXcord(),front.getYcord()-20)); // adds a new square to end
				for (Point f : food) { // checks if the current head is in the position of food
					if(f.equals(front)){
						Point end = snake.get(0);
						snake.add(0,new Point(end.getXcord(), end.getYcord())); // adds a copy of the "end"/front of list
						food.add(new Point(20*(int)(Math.random()*18), 20*(int)(Math.random()*18))); // adds food in random spot
						food.remove(0); // removes the "eaten" food
					}
				}
                snake.remove(0); //removes the end/front of list to give the effect that the block(s) moved
				frame.setContentPane((Container)thepanel.add(new Board(snake,food))); // updates gui
				playagain();
			}
		});
		btnUp.setBounds(200, 400, 50, 50);
		frame.getContentPane().add(btnUp);
		//left
		JButton btnLeft = new JButton("L");
		btnLeft.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
                Point front = snake.get(snake.size()-1);
				snake.add(new Point(front.getXcord()-20,front.getYcord()));
				for (Point f : food) {
					if(f.equals(front)){
						Point end = snake.get(0);
						snake.add(0,new Point(end.getXcord(), end.getYcord()));
						food.add(new Point(20*(int)(Math.random()*19), 20*(int)(Math.random()*19)));
						food.remove(0);
					}
				}
                snake.remove(0);
				frame.setContentPane((Container)thepanel.add(new Board(snake,food)));
				playagain();
			}
		});
		btnLeft.setBounds(150, 450, 50, 50);
		frame.getContentPane().add(btnLeft);
		//down
		JButton btnDown = new JButton("D");
		btnDown.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
                Point front = snake.get(snake.size()-1);
				snake.add(new Point(front.getXcord(),front.getYcord()+20));
				for (Point f : food) {
					if(f.equals(front)){
						Point end = snake.get(0);
						snake.add(0,new Point(end.getXcord(), end.getYcord()));
						food.add(new Point(20*(int)(Math.random()*19), 20*(int)(Math.random()*19)));
						food.remove(0);
					}
				}
                snake.remove(0);
				frame.setContentPane((Container)thepanel.add(new Board(snake,food)));
				playagain();
			}
		});
		btnDown.setBounds(200, 500, 50, 50);
		frame.getContentPane().add(btnDown);
		//right
		JButton btnRight = new JButton("R");
		btnRight.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
                Point front = snake.get(snake.size()-1);
				snake.add(new Point(front.getXcord()+20,front.getYcord()));
				for (Point f : food) {
					if(f.equals(front)){
						Point end = snake.get(0);
						snake.add(0,new Point(end.getXcord(), end.getYcord()));
						food.add(new Point(20*(int)(Math.random()*19), 20*(int)(Math.random()*19)));
						food.remove(0);
					}
				}
                snake.remove(0);
				frame.setContentPane((Container)thepanel.add(new Board(snake,food)));
				playagain();
			}
		});
		btnRight.setBounds(250, 450, 50, 50);
        frame.getContentPane().add(btnRight);
	}
	// checks if the "head"/end of list is in the same position as another body square
	public boolean checkSelfCollision(ArrayList<Point> input){
        Point head = input.get(input.size()-1);
        for (int i=0; i<input.size()-1; i++) {
            Point body = input.get(i);
            if(body.equals(head))
                return true;
        }
        return false;
	}
	// checks if the "head"/end of list is out of bounds
    public boolean checkOutOfBounds(ArrayList<Point> input){
		Point head = input.get(input.size()-1);
        if(head.getXcord()>400 || head.getXcord()<0 ||head.getYcord()>400||head.getYcord()<0)
            return true;
        return false;
    }
	public void playagain(){
		if(this.checkSelfCollision(snake) || this.checkOutOfBounds(snake)){
			JFrame end = new JFrame();
			JOptionPane.showMessageDialog(end, "Game Over");
			String again = JOptionPane.showInputDialog(end, "Would you like to play again");
			if(again.equalsIgnoreCase("yes")||again.equalsIgnoreCase("y")){ // resets game to default
				snake.clear();
				snake.add(new Point(20,20));
				food.clear();
				food.add(new Point(100,100));
			}
		}
	}
}
   //insert runnable thread for animation and repeat changes
