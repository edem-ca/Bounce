/**
 * CIS 120 HW10
 * (c) University of Pennsylvania
 * @version 2.0, Mar 2013
 */

import java.awt.*;

/** Enemies/adversaries in this game, also extending the GameObj Class
 *
 */
public class Trouncerman extends GameObj {

	public static final int SIZE = 20;       
	public static final int INIT_POS_X = 150;  
	public static final int INIT_POS_Y = 50; 
	public static final int INIT_VEL_X = 2;
	public static final int INIT_VEL_Y = 5;
	public boolean alive = true;
	public boolean airborne = true;
	public int size;
	public int jumps_left;
	
	public Trouncerman(int courtWidth, int courtHeight, int x, int y) {
		super(INIT_VEL_X, INIT_VEL_Y, x, y, 
				SIZE, SIZE, courtWidth, courtHeight);
		this.size = SIZE;
	}
	
	public void bounce(Direction d) {
		if (d == null) return;
		switch (d) {
		case UP:    v_y = Math.abs(v_y); break;  
		case DOWN:  v_y = -Math.abs(v_y); break;
		case LEFT:  v_x = Math.abs(v_x); break;
		case RIGHT: v_x = -Math.abs(v_x); break;
		}
	}
	
public void jump () {
    	
    	jumps_left = jumps_left - 1;
    	//if you can't do a double jump then don even bother
    	if (jumps_left < 0) {
    		return;
    	}		    	
    	v_y = -20;
    	
    	
    }
	

	@Override
	public void draw(Graphics g) {
		
		if (alive) {
		g.setColor(Color.BLACK);
		g.fillOval(pos_x, pos_y, width, height);
		}
		if (!alive) {
			return;
		
		}
		
	}



}