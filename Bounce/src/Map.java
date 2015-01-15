/**
 * CIS 120 HW10
 * (c) University of Pennsylvania
 * @version 2.0, Mar 2013
 */

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import javax.swing.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * GameCourt
 * 
 * This class holds the primary game logic of how different objects interact
 * with one another. Take time to understand how the timer interacts with the
 * different methods and how it repaints the GUI on every tick().
 * 
 */
@SuppressWarnings("serial")
public class Map extends JPanel {

	// the state of the game logic
	// private Square square;


	private Bouncer bouncerman; // Your main character
	public int CURRENT_HEALTH = 3;

	//reading in arrays and methods related to reading new levelmaps
	private GameObj[][] game_level;
	private NewLevel next_level;
	private Box[] box_ar; // array across the floor
	private static BufferedImage img;
	private Trouncerman[] tr_ar;
	private int box_ctr; //placeholder of box array
	private int tr_ctr; //placeholder of enemy array
	public boolean playing = false; // whether the game is running
	public int SCORE = 0;
	public int level_ctr = 1;
	public String current_level;

	private JLabel status; // Displays current powerup



	// Game constants
	public static final int COURT_WIDTH = 1000;
	public static final int COURT_HEIGHT = 500;
	public static final int BOUNCER_VELOCITY = 5;

	// Update interval for timer in milliseconds
	public static final int INTERVAL = 45;

	public Map(JLabel status) {
		// creates border around the court area, JComponent method
		setBorder(BorderFactory.createLineBorder(Color.BLACK));

		// The timer is an object which triggers an action periodically
		// with the given INTERVAL. 
		Timer timer = new Timer(INTERVAL, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tick();
			}
		});
		timer.start(); // MAKE SURE TO START THE TIMER!

		// Enable keyboard focus on the court area
		// When this component has the keyboard focus, key
		// events will be handled by its key listener.
		setFocusable(true);

		// this key listener allows the bouncer to move as long
		// as an arrow key is pressed
		addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {

				if (e.getKeyCode() == KeyEvent.VK_LEFT)
					bouncerman.v_x = -BOUNCER_VELOCITY;

				if (e.getKeyCode() == KeyEvent.VK_RIGHT)
					bouncerman.v_x = BOUNCER_VELOCITY;

				//replace constant movement with jump and drop mechanics

				if (e.getKeyCode() == KeyEvent.VK_DOWN)
					bouncerman.drop();

				if (e.getKeyCode() == KeyEvent.VK_UP)
					bouncerman.jump();
			}
		}

				);

		this.status = status;

	}

	/**
	 * (Re-)set the state of the game to its initial state.
	 */
	public void reset(String s) {
		
		//read in the new level
		
		LevelReader lvl_rdr = new LevelReader(s);
		game_level = lvl_rdr.GenerateLevel();
		
		// you could have as many as 1250 (25x50 array) of either, theyre effectively truncated by the counters
		box_ar = new Box[1250];
		tr_ar = new Trouncerman[1250];
		tr_ctr = 0;
		box_ctr = 0;

		//cast members of gameobj array to relevant charcaters
		
		for (int i = 0; i < 50; i++) {
			for (int j = 0; j < 25; j++) {
				if (game_level[j][i] instanceof Bouncer) {
					bouncerman = (Bouncer) game_level[j][i];
				}
				else if (game_level[j][i] instanceof NewLevel) {
					next_level = (NewLevel) game_level[j][i];					
				}

				else if (game_level[j][i] instanceof Box) {
					box_ar[box_ctr] = (Box) game_level[j][i];					
					box_ctr += 1;
				}
				else if (game_level[j][i] instanceof Trouncerman){
					tr_ar[tr_ctr] = (Trouncerman) game_level[j][i];					
					tr_ctr += 1;

				}

			}
		}

		//set initial values of bouncerman

		bouncerman.color = Color.GREEN;
		bouncerman.a_y = -2;
		bouncerman.a_x = 1;
		bouncerman.total_jumps = 1;
		bouncerman.jumps_left = 1;
		bouncerman.health_max = 3;
		bouncerman.current_health = CURRENT_HEALTH;

		playing = true;
		status.setText("None");

		// Make sure that this component has the keyboard focus
		requestFocusInWindow();
	}

	/**
	 * This method is called every time the timer defined in the constructor
	 * triggers.
	 */
	void tick() {
		if (playing) {

			requestFocusInWindow();

			// move all relevant players
			bouncerman.move();

			for (int j = 0; j< tr_ctr; j++) {
				if (tr_ar[j] != null) {
					tr_ar[j].move();
				}
			}


			//make bouncerman bounce off of walls

			bouncerman.bounce(bouncerman.hitWall());

			//make sure bouncer is airborne
			
			for (int i = 0; i < box_ctr; i++) {
				bouncerman.bounce(bouncerman.hitObj(box_ar[i]));
				if ((bouncerman.intersects(box_ar[i]) && bouncerman.v_y == 0)) {
					bouncerman.airborne = false;
					break;
				} 
				else {
					bouncerman.airborne = true;
				}
			}
			
			//make sure each trouncer in array is airborne

			for (int j = 0; j < tr_ctr; j++) {
				for(int i = 0; i < box_ctr; i++) {
					if (tr_ar[j] != null) {
						tr_ar[j].bounce(tr_ar[j].hitObj(box_ar[i]));
						tr_ar[j].bounce(tr_ar[j].hitWall());

						if (tr_ar[j].hitWall() instanceof Direction) {

						}

						if ((tr_ar[j].v_y == 0) && tr_ar[j].intersects(box_ar[i])) {
							tr_ar[j].airborne = false;
							break;
						} 
						else {						
							tr_ar[j].airborne = true;
						}
					}	
				}

			}

			/*
			 * if airborne - accelerate downward - no aerial friction If
			 * grounded: -have horizontal friction do work -reset his jump
			 * counter -make it so that he doesn't phase through the blocks
			 * beneath him by resetting position
			 */

			if (bouncerman.airborne) {
				bouncerman.a_x = 0; //no friction in air
				bouncerman.v_y = bouncerman.v_y - bouncerman.a_y;
				bouncerman.can_drop = true;
			}
			else {
				for (int i = 0; i < box_ctr; i++) {
					bouncerman.v_y = 0;
					bouncerman.a_x = 2; //ground friction doe
					//set bouncer to be on top of box if he starts 2 drop thru it
					if (bouncerman.intersects(box_ar[i])) {
						bouncerman.can_drop = false;
						bouncerman.pos_y = box_ar[i].pos_y - bouncerman.size;
					}
					//if he grounded reset his jump counter
					bouncerman.jumps_left = bouncerman.total_jumps;

				}
			}
			
			//same for toruncerman

			for (int j = 0; j < tr_ctr; j++){


				if (tr_ar[j] != null) {

					if (tr_ar[j].airborne) {

						tr_ar[j].a_x = 0; //no friction motherfukers!! slip n slide around real smooth-like

						tr_ar[j].a_y = -1;

						tr_ar[j].v_y = tr_ar[j].v_y - tr_ar[j].a_y;
					}
					else {

						tr_ar[j].jump();

						tr_ar[j].a_x = 0;

						for (int i = 0; i < box_ctr; i++) {				

							if (tr_ar[j].intersects(box_ar[i])) {

								tr_ar[j].pos_y = box_ar[i].pos_y - tr_ar[j].size;
							}
						}

						tr_ar[j].jumps_left = 1;

					}

					if (bouncerman.willIntersect(tr_ar[j])) {

						if (bouncerman.airborne) {
							SCORE += 100;
							bouncerman.delete(tr_ar[j]);
							tr_ar[j].alive = false;
							tr_ar[j] = null;
						} else {
							bouncerman.delete(tr_ar[j]);
							tr_ar[j].alive = false;
							tr_ar[j] = null;
							CURRENT_HEALTH -= 1;
							bouncerman.current_health = CURRENT_HEALTH;
						}

					}
				}
			}

			// stop bouncerman from oscillating between velocities 1 and -1
			// otherwise, make him deccelerate becasue friction
			if (bouncerman.v_x == 1 || bouncerman.v_x == -1
					|| bouncerman.v_x == 0) {
				bouncerman.v_x = 0;
			} else if (bouncerman.v_x < 1) {
				bouncerman.v_x = bouncerman.v_x + bouncerman.a_x;
			} else if (bouncerman.v_x > 1) {
				bouncerman.v_x = bouncerman.v_x - bouncerman.a_x;
			}

		}


		//condition for hitting a next level object
		
		if (next_level != null) {

			if (bouncerman.intersects(next_level)) {

				level_ctr += 1;	

				String img_file = "Level"+ level_ctr +".txt"; 				

				try {					
					img = ImageIO.read(new File(img_file));

					reset(next_level.Levelswitch(level_ctr));

				} catch (IOException e) {

					status.setText("NO MO FLOORS LEFT...YOU WIN MUTHAFUCKA...HIT PLAY AGAIN IF YOU WANT");

					playing = false;

				}



			}
		}
		
		//report current health
		
		if (playing) {
			if (SCORE == 0 && CURRENT_HEALTH == 3) {
				status.setText("Hit Instructions Before Playing!"); 
			}
			else {
			
			status.setText("Current Health: " + bouncerman.current_health
					+ "  Current Score: " + SCORE);
			}
		}

		//if you run out of health, you is dead

		if (bouncerman.current_health < 1) {
			status.setText("YOU LOSE");
			playing = false;

		}


		// update the display
		repaint();
	}



	@Override
	public void paintComponent(Graphics g) {
		
		super.paintComponent(g);

		// redraw all gameobjs in the arrays

		for (int i = 0; i < box_ctr; i++) {

			box_ar[i].draw(g);

		}

		for (int j = 0; j < tr_ctr; j++) {

			if (tr_ar[j] != null) {

				tr_ar[j].draw(g);
			}
		}
		//draw all objects there are one instance of separately
		if (bouncerman!= null) {
		bouncerman.draw(g);
		
		{
			
		}
	    if (next_level != null){
	    	next_level.draw(g);
	    }
		}
		
	}

	@Override
	public Dimension getPreferredSize() {
		return new Dimension(COURT_WIDTH, COURT_HEIGHT);
	}
}
