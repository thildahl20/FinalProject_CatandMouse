import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.net.URL;

import javax.swing.*;

/**
 * This creates a user character
 * @author thildahl20
 *
 */
public class User extends Character{

	/**
	 * Constructor for a 1 player game: has a preset image and x and y location for the 1 player
	 */
	User() {
	Toolkit toolkit = Toolkit.getDefaultToolkit();
	URL imgURL = getClass().getResource("/images/cat2.png");
	img = toolkit.getImage(imgURL);
	img = img.getScaledInstance(60,60,Image.SCALE_SMOOTH);
	ImageIcon i = new ImageIcon(img);
	photo = new JLabel(i);

	x = 250;
	y = 250;

	}
	
	/**
	 * Constructor for a multi-player game, needs to set the image and coordinates individually for that user
	 * @param image the image of the user
	 * @param xValue the x value of the user
	 * @param yValue the y value of the user
	 */
	User(Image image, int xValue, int yValue) {
	img = image;
	img = img.getScaledInstance(60,60,Image.SCALE_SMOOTH);
	ImageIcon i = new ImageIcon(img);
	photo = new JLabel(i);

	x = xValue;
	y = yValue;

	}
	
	/**
	 * This allows the user's location to be set somewhere specific
	 * Is called when I reset their locations/ reset the game
	 * @param xChange change user's x value to this
	 * @param yChange change user's y value to this
	 */
	public void setXandY(int xChange, int yChange) {
		x = xChange;
		y = yChange;
	}

	
}