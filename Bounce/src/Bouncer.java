/**
 * CIS 120 HW10
 * (c) University of Pennsylvania
 * @version 2.0, Mar 2013
 */

import java.awt.*;

/** A basic game object displayed as a black square, starting in the 
 * upper left corner of the game court.
 *
 */
public class Bouncer extends GameObj {
	public static final int SIZE = 20;
	public static final int INIT_X = 0;
	public static final int INIT_Y = 0;
	public static final int INIT_VEL_X = 0;
	public static final int INIT_VEL_Y = 3;
	
	public int health_max;
	public int current_health;
	
	public int size;
	public int str;
	public boolean airborne = false;
	public boolean can_drop = false;
	public int total_jumps;
	public int jumps_left;
	public Color color;
	
	
    /** 
     * Note that because we don't do anything special
     * when constructing a bouncerman, we simply use the
     * superclass constructor called with the correct parameters 
     */
    public Bouncer(int courtWidth, int courtHeight, int x, int y){
        super(INIT_VEL_X, INIT_VEL_Y, x, y, 
        		SIZE, SIZE, courtWidth, courtHeight);
        
        this.size = SIZE;       
        
    }
    
    public void jump () {
    	
    	jumps_left = jumps_left - 1;
    	
    	//if you can't do a double jump then dont even fucking bother
    	
    	if (jumps_left < 0) {
    		return;
    	}		
    	    	
    	v_y = -20;
    	
    	
    }
    
    public void drop () {    	
    	
    	if (can_drop){
    		
    	jumps_left = total_jumps; //makes Down-Up Double Jump Mechanic
    	
    	v_y = 20;
    	}
    	
    }
       
    

    @Override
    public void draw(Graphics g) { 
		g.setColor(color);
        g.fillOval(pos_x, pos_y, width, height);
    }

}


