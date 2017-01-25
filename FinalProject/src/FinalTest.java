import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.*;

/**
 * This is the main class where the game frame is set up which contains a window 
 * -With Labels on top to represent the scores
 * -and with one or 2 users, a reward object and an increasing number of enemy objects
 * 
 * It will constantly check to see if either of the users have interacted with the reward or an enemy to either change the score and relocate the reward, or end the game
 * @author thildahl20
 *
 */
public class FinalTest extends JComponent implements KeyListener{

JFrame frame;
boolean multi;
JPanel top, center;
Image background;
JLabel score;
JLabel score2;
JLabel highScore;
int scoreValue;
int scoreValue2;
int highScoreValue;
User user;
User user2;
Reward reward;
ArrayList<Enemy> enemies;

/**
 * This is the constructor for the frame of the game
 * @param multip takes in whether it is building a 1 player game or a two player
 * 		  multip: if true, 2 player, else 1 player
 */
	FinalTest(boolean multip) {
		multi = multip;
		
		//Set up a frame that can not be resized
		frame = new JFrame("Cat and Mouse");
		frame.addKeyListener(this);
		frame.setFocusable(true);
		frame.setSize(600,600);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//Create a background image 
		frame.setLayout(new BorderLayout());
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		URL backURL = getClass().getResource("images/background.jpg");
		background = toolkit.getImage(backURL);
		background= background.getScaledInstance(600,600,Image.SCALE_SMOOTH);
		frame.setContentPane(new JLabel(new ImageIcon(background)));

		frame.setLayout(new BorderLayout());
		//Create 2 panels that are transparent(to see background image
		top = new JPanel(); 
		center = new JPanel();
		top.setOpaque(false);
		center.setOpaque(false);

		frame.add(top, BorderLayout.NORTH);
		frame.add(center, BorderLayout.CENTER);
		
		initialize();
}
	/**
	 * This builds most of the frame by setting up the objects and creating the threads that interact with the user
	 */
	public void initialize() {

		Toolkit toolkit = Toolkit.getDefaultToolkit();
		
		//Set values/strings/labels that represent the user's score and the high score
		scoreValue = 0;
		String s = String.format("Player 1: %d", scoreValue);
		score = new JLabel(s);
		//Change the text's size and color to make it more visible on the background (done on all 3 labels)
		score.setForeground(Color.orange);
		score.setFont(new Font("Calibri", Font.PLAIN, 30));
		top.add(score);
		
		//Set up the high score value and its label
		if (multi) highScoreValue = 500;
		else	   highScoreValue = 1000;
		
		String h = String.format("High Score: %d", highScoreValue);
		highScore = new JLabel(h);
		highScore.setForeground(Color.green);
		highScore.setFont(new Font("Calibri", Font.PLAIN, 30));
		top.add(highScore);
		
		//If it is a multiplayer game
		if (multi) {
			scoreValue2 = 0;
			s = String.format("Player 2: %d", scoreValue);
			score2 = new JLabel(s);
			score2.setForeground(Color.white);
			score2.setFont(new Font("Calibri", Font.PLAIN, 30));
			top.add(score2);
		}			
		
		//Create objects for the correct number of users and the reward
		//If single player
		if (!multi) 
		user = new User();
		//If multi/2 player
		else {
			//Creates different images for the 2 cats
			URL imgURL = getClass().getResource("/images/cat2.png");
			Image img1 = toolkit.getImage(imgURL);
			img1 = img1.getScaledInstance(60,60,Image.SCALE_SMOOTH);
			
			URL imgURL2 = getClass().getResource("/images/cat3.png");
			Image img2 = toolkit.getImage(imgURL2);
			img2 = img2.getScaledInstance(60,60,Image.SCALE_SMOOTH);
			
			//Sends a different image for each cat and a different starting location
			user = new User(img1, 200, 250);
			user2 = new User(img2, 300, 250);
		}
		
		reward = new Reward();
		enemies = new ArrayList<Enemy>();

		//Set up the panels with components, and add to the frame
		center.add(user.photo);
		if (multi) center.add(user2.photo);
		center.add(reward.photo);
		
		//Create a thread to control the reward's motion
		Thread r = new Thread(reward);
		r.start();
		
		frame.setVisible(true);
		
		/**
		 * This constantly checks the locations of the objects and repaints them in order to make sure everything is where they are supposed to be and that the user has not touched an enemy
		 * If they do touch an enemy, go through end game process
		 */
		while (true) {
			user.photo.setLocation(user.x,user.y);
			if (multi) user2.photo.setLocation(user2.x, user2.y);
			reward.photo.setLocation(reward.x,reward.y);
			
			//If the user hits the reward 
			if(user.getX() + 20 >= reward.getX() - 20 && user.getX()-20 <= reward.getX()+20 && user.getY()+20 >= reward.getY()-20 && user.getY()-20<=reward.getY()+20) {
			    //set the reward to a new random location
				reward.reset();
				
				//Create a new enemy, add it to the arraylist of enemies and its image to the frame and create a new thread to handle its motion
				Enemy en;
				
				//Make sure the enemy is not in the same spot as the user, by sending their x and y when constructing
				if (!multi) 
					en = new Enemy(user.getX(), user.getY());
				else
					en = new Enemy(user.getX(), user.getY(), user2.getX(), user2.getY());
				
				enemies.add(en);
				center.add(en.photo);
				Thread t = new Thread(en);
				t.start();

				//Increase the user's score for getting the reward
				scoreValue = scoreValue + 100;
				s = String.format("Player 1: %d", scoreValue);
				score.setText(s);
			}
			
			if (multi) {
			//If user 2 hits the reward
			if (user2.getX() + 20 >= reward.getX() - 20 && user2.getX()-20 <= reward.getX()+20 && user2.getY()+20 >= reward.getY()-20 && user2.getY()-20<=reward.getY()+20){
			    //set the reward to a new random location
				reward.reset();
				
				//Create a new enemy, add it to the arraylist of enemies and its image to the frame and create a new thread to handle its motion
				Enemy en;
				
				//Make sure the enemy is not in the same spot as the user, by sending their x and y when constructing
				if (!multi) 
					en = new Enemy(user.getX(), user.getY());
				else
					en = new Enemy(user.getX(), user.getY(), user2.getX(), user2.getY());
				
				enemies.add(en);
				center.add(en.photo);
				Thread t = new Thread(en);
				t.start();

				//Increase the user's score for getting the reward
				scoreValue2 = scoreValue2 + 100;
				s = String.format("Player 2: %d", scoreValue2);
				score2.setText(s);
			}
			}
			

			//Check each enemy, reset their x and y (changed thru thread), and if it hits the user end game
			for (Enemy e: enemies) {
				e.photo.setLocation(e.x,e.y);
			//If 2 players, check both
			if (multi){
				//If one of the players hits an enemy
				if(user.getX() + 20 >= e.getX() - 20 && user.getX()-20 <= e.getX()+20 && user.getY()+20 >= e.getY()-20 && user.getY()-20<=e.getY()+20
						|| user2.getX() + 20 >= e.getX() - 20 && user2.getX()-20 <= e.getX()+20 && user2.getY()+20 >= e.getY()-20 && user2.getY()-20<=e.getY()+20) {

					//If the user's score is greater than the high score, change the high score and tell them in the game over screen
					//This checks to see if only one person was higher, set to their score
					if(scoreValue > highScoreValue && scoreValue2 <= highScoreValue){
						highScoreValue = scoreValue;
						String txt = String.format("High Score: %d", highScoreValue);
						highScore.setText(txt);
						JOptionPane.showMessageDialog(null, "Game Over\nCongrats Player 1 on a new High Score!");
					}
					else if (scoreValue2 > highScoreValue && scoreValue <= highScoreValue) {
						highScoreValue = scoreValue2;
						String txt = String.format("High Score: %d", highScoreValue);
						highScore.setText(txt);
						JOptionPane.showMessageDialog(null, "Game Over\nCongrats Player 2 on a new High Score!");
					}
					//If both were higher, find the higher of the two and set to their score
					else if (scoreValue > highScoreValue && scoreValue > scoreValue2) {
						highScoreValue = scoreValue;
						String txt = String.format("High Score: %d", highScoreValue);
						highScore.setText(txt);
						JOptionPane.showMessageDialog(null, "Game Over\nCongrats Player 1 on a new High Score!");
					}
					else if (scoreValue2 > highScoreValue && scoreValue2 > scoreValue) {
						highScoreValue = scoreValue2;
						String txt = String.format("High Score: %d", highScoreValue);
						highScore.setText(txt);
						JOptionPane.showMessageDialog(null, "Game Over\nCongrats Player 2 on a new High Score!");
					}
					else //just show the game over screen
						JOptionPane.showMessageDialog(null,  "Game Over");
					
					//Reset the score back to 0
					scoreValue = 0;
					scoreValue2 = 0;
					score.setText("Player 1: 0");
					if (multi) score2.setText("Player 2: 0");

					//Remove all of the enemies from the screen and reset the arraylist of enemies to empty
					for(Enemy en:enemies){
						center.remove(en);
						center.remove(en.photo);
						frame.remove(en.photo);
					}
					reward.reset();
					user.setXandY(200, 250);
					user2.setXandY(300, 250);
					enemies = new ArrayList<Enemy>();	
				}
			}
				//If only 1
				else {
					if(user.getX() + 20 >= e.getX() - 20 && user.getX()-20 <= e.getX()+20 && user.getY()+20 >= e.getY()-20 && user.getY()-20<=e.getY()+20) {
						//If the user's score is greater than the high score, change the high score and tell them in the game over screen
						if(scoreValue > highScoreValue){
							highScoreValue = scoreValue;
							String txt = String.format("High Score: %d", highScoreValue);
							highScore.setText(txt);
							JOptionPane.showMessageDialog(null, "Game Over\nCongrats on a new High Score!");
						}
						else //just show the game over screen
							JOptionPane.showMessageDialog(null,  "Game Over");
						
						//Reset the score back to 0
						scoreValue = 0;
						score.setText("Player 1: 0");

						//Remove all of the enemies from the screen and reset the arraylist of enemies to empty
						for(Enemy en:enemies){
							center.remove(en);
							center.remove(en.photo);
							frame.remove(en.photo);
						}
						reward.reset();
						user.setXandY(250, 250);
						enemies = new ArrayList<Enemy>();

					}
				}	
			frame.repaint();
			}
		
				
		}
	}

/**
 * Creates a game using the selected number of players
 * @param args
 */
	public static void main(String args[]) {
		
		//Since I made a two player variant, I have the user pick 1/2 players before constructing the game
		System.out.printf("Do you want to play a game with one or two players?");
		Scanner in = new Scanner(System.in);
		int player = in.nextInt();
		
		//Set 1 or 2 players
		boolean multiPlayer;
		if (player == 1)
			multiPlayer = false;
		else
			multiPlayer = true;
		
		//Create the game
		FinalTest g = new FinalTest(multiPlayer);

}


	public void keyTyped(KeyEvent e) {

	}
	
	//Whenever one of the appropriate keys is pressed, move the user 20 frames (make sure they don't go past the border)
	public void keyPressed(KeyEvent e) {
		//If the arrow keys are pressed:controls player 1
		if (e.getKeyCode() == KeyEvent.VK_RIGHT && user.x<520) {
			for (int i = 0; i<20; i++) user.x++;

		}
		else if (e.getKeyCode() == KeyEvent.VK_LEFT && user.x>0) {
			for (int i = 0; i<20; i++) user.x--;

		}
		else if (e.getKeyCode() == KeyEvent.VK_UP && user.y>20) {
			for (int i = 0; i<20; i++) user.y--;

		}
		else if (e.getKeyCode() == KeyEvent.VK_DOWN && user.y<450) {
			for (int i = 0; i<20; i++) user.y++;

		}
		//If the a,s,w,d keys are pressed: controls player 2
		else if (e.getKeyCode() == KeyEvent.VK_D && user2.x<520) {
			for (int i = 0; i<20; i++) user2.x++;

		}
		else if (e.getKeyCode() == KeyEvent.VK_A && user2.x>0) {
			for (int i = 0; i<20; i++) user2.x--;

		}
		else if (e.getKeyCode() == KeyEvent.VK_W && user2.y>20) {
			for (int i = 0; i<20; i++) user2.y--;

		}
		else if (e.getKeyCode() == KeyEvent.VK_S && user2.y<450) {
			for (int i = 0; i<20; i++) user2.y++;

		}
	}

	public void keyReleased(KeyEvent e) {
	}
	
}
