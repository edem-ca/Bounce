import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.util.*;

@SuppressWarnings("unused")
public class LevelReader {
	
	private static int NUMROWS = 25;
	private static int NUMCOLS = 50;
	private char[][] loc = new char[NUMROWS][NUMCOLS];
	private GameObj[][] Level = new GameObj[NUMROWS][NUMCOLS];
	public static final int COURT_WIDTH = 1000;
	public static final int COURT_HEIGHT = 500;


	public LevelReader(String s) {
		try {
			@SuppressWarnings("resource")
			
			BufferedReader br = new BufferedReader(new FileReader(s));
			
			//read in and make a character array
			for (int i = 0; i < 25; i++) {
				String line = br.readLine();
				loc[i] = line.toCharArray();
			}
		} catch (IOException exception) {
			System.out.println("Error processing file: " + exception);
		}
	}
	
	public GameObj[][] GenerateLevel() {
		
		//read in character array and create a level of gameobjects based on that array
		for (int i = 0; i < 25; i++) {
						
			for (int j = 0; j < 50; j++){				
				if (loc[i][j] == 'B') {
					
					Level[i][j] = new Bouncer(COURT_WIDTH, COURT_HEIGHT, j*40, i*40);					
				}
				else if (loc[i][j] == '#') {
					Level[i][j] = new Box(COURT_WIDTH, COURT_HEIGHT, j*20, i*20);					
				}
				else if (loc[i][j] == 'T') {
					Level[i][j] = new Trouncerman(COURT_WIDTH, COURT_HEIGHT, j*20 , i*20);					
				}
				else if	(loc[i][j] == 'L') {
					Level[i][j] = new NewLevel(COURT_WIDTH, COURT_HEIGHT, j*20, i*20);
				}
			}
		}
		return Level;
	}

}