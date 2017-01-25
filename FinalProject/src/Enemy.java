import java.awt.*;
import java.net.URL;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

/**
 * This creates an enemy character at a random location that does not hit the user's position
 * implements the run() function to create a thread that randomly generates its' movement around the screen
 * @author thildahl20
 *
 */
public class Enemy extends Character implements Runnable{

	//Creates an enemy object in a single player game. Sets the image and randomizes the starting x and y values
	Enemy(int userX, int userY) {
	Toolkit toolkit = Toolkit.getDefaultToolkit();
	URL imgURL = getClass().getResource("images/dog1.png");
	img = toolkit.getImage(imgURL);
	img = img.getScaledInstance(60,60,Image.SCALE_SMOOTH);
	ImageIcon i = new ImageIcon(img);
	photo = new JLabel(i);

	//Continue to randomize the x and y until it does not start on the same position as the user
	do {
	Random r = new Random();
	x = r.nextInt(520);
	y = r.nextInt(470);
	}while (userX + 20 >= x - 20 && userX-20 <= x+20 && userY + 20 >= y - 20 && userY-20 <= y+20);

	repaint();
	}
	
	//Creates an enemy object in a 2 player game. Sets the image and randomizes the starting x and y values
	Enemy(int userX, int userY, int user2X, int user2Y) {
	Toolkit toolkit = Toolkit.getDefaultToolkit();
	URL imgURL = getClass().getResource("images/dog1.png");
	img = toolkit.getImage(imgURL);
	img = img.getScaledInstance(60,60,Image.SCALE_SMOOTH);
	ImageIcon i = new ImageIcon(img);
	photo = new JLabel(i);

	//Continue to randomize the x and y until it does not start on the same position as either of the users
	do {
	Random r = new Random();
	x = r.nextInt(520);
	y = r.nextInt(470);
	} while ((userX + 20 >= x - 20 && userX-20 <= x+20 && userY + 20 >= y - 20 && userY-20 <= y+20)||(user2X + 20 >= x - 20 && user2X-20 <= x+20 && user2Y + 20 >= y - 20 && user2Y-20 <= y+20));

	repaint();
	}

	/**
	 * When a new thread is created, generate random numbers that create motion for the enemy
	 */
	public void run() {
		//Loop forever: always moving
		while (true) {
			//Create a random number between 0 and 4
			Random rnd = new Random();
			int direction = rnd.nextInt(4);
			
			//Go in the same direction moving 1 pixel at a time 70 times
			for (int i = 0; i < 70; i++) {

			try {Thread.sleep(10);}
			catch  (InterruptedException ex){} 
			
			//Check the random number to pick a direction, and move that way
			if (direction == 0) {
				//Make sure the number does not exceed the bounds before moving that way
				if((x+1) <= 520) {
					x++;
					repaint();
				}
				else 
					break;
			}

			else if (direction == 1) {
				if((x-1) >= 0) {
					x--;
					repaint();
				}
				else 
					break;
			}

			else if (direction == 2) {
				if((y+1) <= 470) {
					y++;
					repaint();
				}
				else 
					break;
				}


			//direction is 3, the final option
			else {
				if((y-1) >= 0) {
					y--;
					repaint();
				}
				else 
					break;
				}
			
			}
		}
		}


}