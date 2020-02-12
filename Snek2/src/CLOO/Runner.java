//Christopher Liu
package CLOO;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;

public class Runner extends JFrame implements KeyListener {
	private Grid game = new Grid();

	public Runner() {
		super("Snek");
		setSize(game.getWIDTH(), game.getHEIGHT());
		getContentPane().add(game);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		addKeyListener(this);
	}

	public static void main(String args[]) {
		Runner run = new Runner();
	}
//https://stackoverflow.com/questions/9333876/how-to-simply-implement-a-keylistener
//https://docs.oracle.com/javase/7/docs/api/java/awt/event/KeyEvent.html
	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// keyboard arrow keys change the direction of snake
		if(e.getKeyCode() == KeyEvent.VK_DOWN && game.getmovingUp() == false){
			game.setmovingDown();
		}
		if(e.getKeyCode() == KeyEvent.VK_UP && game.getmovingDown() == false){
			game.setmovingUp();
		}
		if(e.getKeyCode() == KeyEvent.VK_LEFT && game.getmovingRight() == false){
			game.setmovingLeft();
		}
		if(e.getKeyCode() == KeyEvent.VK_RIGHT && game.getmovingLeft() == false){
			game.setmovingRight();
		}
	}
	@Override
	public void keyReleased(KeyEvent e) {
	}

}