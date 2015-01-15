import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/** A basic game object displayed as a yellow circle, starting in the 
 * upper left corner of the game court.
 *
 */
@SuppressWarnings("unused")
public class NewLevel extends GameObj {

	public static final int SIZE = 20;
	public static final String img_file = "nextlevel.png";
	public static final int INIT_POS_X = 150;  
	public static final int INIT_POS_Y = 50; 
	public static final int INIT_VEL_X = 2;
	public static final int INIT_VEL_Y = 5;
	private int counter = 1;
	private static BufferedImage img;

	public NewLevel(int courtWidth, int courtHeight, int x, int y) {
		super(INIT_VEL_X, INIT_VEL_Y, x, y, 
				SIZE, SIZE, courtWidth, courtHeight);
		try {
			if (img == null) {
				img = ImageIO.read(new File(img_file));
			}
		} catch (IOException e) {
			
			System.out.println("Internal Error:" + e.getMessage());
		}
	}
	
	//returns a string of the next filename
	
	public String Levelswitch (int a) {
		String next = "Level"+ a +".txt";
		return next;
	}
	

	@Override
	public void draw(Graphics g) {
		g.drawImage(img, pos_x, pos_y, width, height, null); 
		}
	}