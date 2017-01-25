import java.awt.*;
import java.net.URL;

import javax.swing.*;

/**
 * Each object will have an image value, represented on a JLabel, located at an x and y value
 * Extended by user reward and enemy: character object is never created
 * @author thildahl20
 *
 */
public class Character extends JLabel{

Image img;
int x, y;
JLabel photo;
	
Character() {

}

//Get the x value of the object
public int getX() {
	return x; 
	}
//Get the y value of the object
public int getY() {
	return y; 
	}


}
