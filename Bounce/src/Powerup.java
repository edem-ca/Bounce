import java.awt.Color;
import java.awt.Graphics;

/** Unfinished code for implementing powerups
 *
 */
public class Powerup extends GameObj {

	public static final int SIZE = 20;       
	public static final int INIT_POS_X = 300;  
	public static final int INIT_POS_Y = 300; 
	public static final int INIT_VEL_X = 0;
	public static final int INIT_VEL_Y = 10;

	public Powerup(int courtWidth, int courtHeight) {
		super(INIT_VEL_X, INIT_VEL_Y, INIT_POS_X, INIT_POS_Y, 
				SIZE, SIZE, courtWidth, courtHeight);
	}

	@Override
	public void draw(Graphics g) {
		g.setColor(Color.BLACK);
		g.fillOval(pos_x, pos_y, width, height); 
	}



}