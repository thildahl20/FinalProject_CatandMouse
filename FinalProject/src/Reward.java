import java.awt.*;
import java.net.URL;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

/**
 * This is a reward object, which is a character object
 * It also has an x and y to store location and an image and label
 * @author thildahl20
 */
public class Reward extends Character implements Runnable{

	//Creates a reward object. Sets the image and the JLabel with the image, then randomizes the x and y through reset()
	Reward() {
	Toolkit toolkit = Toolkit.getDefaultToolkit();
	URL imgURL = getClass().getResource("/images/mouse1.png");
	img = toolkit.getImage(imgURL);
	img = img.getScaledInstance(60,60,Image.SCALE_SMOOTH);
	ImageIcon i = new ImageIcon(img);
	photo = new JLabel(i);

	reset();
	}

	/**This generates random numbers for x and y 
	* Is also called to reset the rewards location if it has been hit by the user
	*/
	public void reset() {
	Random r = new Random();
	x = r.nextInt(520);
	y = r.nextInt(470);
	repaint();
	}

	/**
	 * When a new thread is created, generate random motion for the reward
	 */
	public void run() {
		//Loop forever: always moving
			while (true) {
				//Create a random number between 0 and 4
				Random rnd = new Random();
				int direction = rnd.nextInt(4);
				
				//Go in the same direction moving 1 pixel at a time 70 times: Makes it look like smooth motion
				for (int i = 0; i < 70; i++) {

				try {Thread.sleep(10);}
				catch  (InterruptedException ex){} 
				
				//Check the random number to pick a direction, and move that way : move right
				if (direction == 0) {
					//Make sure the number does not exceed the bounds before moving that way
					if((x+1) <= 520) {
						x++;
						repaint();
					}
					else 
						break;
				}
				//Move Left
				else if (direction == 1) {
					if((x-1) >= 0) {
						x--;
						repaint();
					}
					else 
						break;
				}
				//Move down
				else if (direction == 2) {
					if((y+1) <= 470) {
						y++;
						repaint();
					}
					else 
						break;
				}


				//direction is 3, the final option: up
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

