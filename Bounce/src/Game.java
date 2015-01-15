/**
 * CIS 120 HW10
 * (c) University of Pennsylvania
 * @version 2.0, Mar 2013
 */

// imports necessary libraries for Java swing
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/** 
 * Game
 * Main class that specifies the frame and widgets of the GUI
 */
public class Game implements Runnable {
    public void run(){
  

        // Top-level frame in which game components live
		  // Be sure to change "TOP LEVEL FRAME" to the name of your game
        final JFrame frame = new JFrame("Bounce");
        frame.setLocation(300,300);

		  // Status panel
        final JPanel status_panel = new JPanel();      
        frame.add(status_panel, BorderLayout.SOUTH);
        
        final JLabel status = new JLabel("Running...");
        status_panel.add(status);
        
        //instructions
        String instructions = "WELCOME TO BOUNCE!"+ "| Up = Jump |" + "| Down = Drop |" + "| Left/Right = Roll |";
        		
		
        final JPanel inst_panel = new JPanel();
        frame.add(inst_panel, BorderLayout.NORTH);
        final JLabel inst = new JLabel (instructions);
        inst_panel.add(inst);

        // Main playing area
        final Map court = new Map(status);
        frame.add(court, BorderLayout.CENTER);

        // Reset button
        final JPanel control_panel = new JPanel();
        frame.add(control_panel, BorderLayout.EAST);

        // When the button is pressed,
        // actionPerformed() will be called.
        final JButton reset = new JButton("Start Play");
        reset.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    court.reset("Level1.txt");
                    court.level_ctr = 1;
                    court.CURRENT_HEALTH = 3;
                    court.playing = true;
                    court.SCORE = 0;
                }
            }
        );
        
        control_panel.add(reset);
        
        
        final JButton instruct = new JButton ("Instructions");
        instruct.addActionListener (new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		JOptionPane.showMessageDialog(frame, ""
        				+ "\n BOUNCER"
        				+ "\n Rules:"
        				+ "\n You have 3HP: Dont fuck up"
        				+ "\n Get through all the level!s (dem colorful blocks)"
        				+ "\n Bounce on as many bad guys as you can"
        				+ "\n" + "\n"
        				+ "\n Controls:"
        				+ "\n Up to Jump"
        				+ "\n Down to Drop"
        				+ "\n Left and Right are ... self explanatory"
        				+ "\n You can climb up stacks of blocks by rolling into them"
        				+ "\n You bounce if you jump into them"
        				+ "\n You can drop through blocks after you jump!"
        				+ "\n Extra-Midair Jumps but hitting Down-Up Quickly"
        				+ "\n \n \n Features:"
        				+ "\n  Read in txt files to make new levels!"
        				+ "\n Gravity and Friction : Newton would be proud"
        				+ "\n Multiple Levels"
        				+ "\n \n \n Hit 'Play'to Begin!"
        					
        				);
        	}
        }
);
        control_panel.add(instruct);
        

        // Put the frame on the screen
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        // Start game
        court.reset("Level1.txt");
    }

    /*
     * Main method run to start and run the game
     */
    public static void main(String[] args){
        SwingUtilities.invokeLater(new Game());
    }
}
