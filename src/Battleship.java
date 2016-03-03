/**
 * @ Team Sun and Moon
 * @ Chukwudi Derek Uche
 * @ Kyle Ohanian
 * @ Will Jayne
 * @ Jake Brabec
 * @ Starter Code By Guocheng
 *
 * 2016-01-30
 * For: Purdue Hackers - Battleship
 * Battleship Client
 */

import java.io.*;
import java.net.Socket;
import java.net.InetAddress;
import java.util.Random;

public class Battleship {
	public static String API_KEY = "395568420";
	public static String GAME_SERVER = "battleshipgs.purduehackers.com";
    ArrayList<String> setLocations = new ArrayList<String>();
    
    public static final String myLetters = "ABCDEFGH";
    public static final String numbers = "01234567";

	//////////////////////////////////////  PUT YOUR CODE HERE //////////////////////////////////////
    PrintWriter printWriter;
    String currentOpponentID;
	char[] letters;
	int[][] grid;
	int[][] probability_grid;
	int totalProbability;
	Matching match = null;

	public static class Matching {
		int x, y, current_x, current_y;

		int num_Attempts = 0;
		boolean checked = false;
		boolean vert = false;

		public Matching(int x, int y) {
			this.x = x;
			this.y = y;
		}

		public void increment() {
			num_Attempts++;
		}

		public void check() {

		}

	}

    void saveLog() {
        if(currentOpponentID == null)
            return;
        System.out.println("Saving log for: " + currentOpponentID);
        File file = new File("logs/"+currentOpponentID+".txt");
        file.getParentFile().mkdirs();
        try {
            printWriter = new PrintWriter("logs/"+currentOpponentID+".txt","UTF-8");
            printWriter.println(currentOpponentID);
            //Store all confirmed hits
            for(int i = 0; i < grid.length; i++) {
                String temp = "";
                for(int j = 0; j < grid[i].length; j++) {
                    temp += grid[j][i]+",";
                }
                printWriter.println(temp);
            }
            printWriter.flush();
            printWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Resets game logic
     */
    void resetGame() {
        saveLog();
        System.out.print("Clearing Grid...");
        // Fill Grid With -1s
        for(int i = 0; i < grid.length; i++)
            for(int j = 0; j < grid[i].length; j++)
                grid[i][j] = -1;
    }
	void placeShips(String opponentID) {
        resetGame();
        currentOpponentID = opponentID;


        // Place Ships
        
        
        /* EDIT: create a randomnizer and come up with a combo for each ship.
         when locations are set, add the locations to an arraylist, and check
         the list whenever a location is randomnized.
         */
        
        
        setCarrierPosition();
        setBattleshipPosition();
        setCruiserPosition();
        setSubmarinePosition();
        setDestroyerPosition();
	}
    
    /* The next 5 methods will come up with randomnly generated
     locations for the different ships. Because of the sizes, we also
     have to take account of the spaces that the ship is over, which can be
     more than two spaces.
     */
    
    void setDestroyerPosition() {
        boolean swag = true;
        boolean checker = true;
        while(swag) {
            checker = true;
            Random randomnizer = new Random();
            char letterLocation = myLetters.charAt(randomnizer.nextInt(letters.length));
            
            
            int numberDestroyer = (int)(Math.random()*7);
            String letterLocationDuo = Character.toString(letterLocation);
            String number = Integer.toString(numberDestroyer);
            String numberPlusOne = Integer.toString(numberDestroyer + 1);
            String position = letterLocationDuo+number;
            String positionPlusOne = letterLocationDuo+numberPlusOne;
            for(int i = 0; i <setLocations.size();i++) {
                if(setLocations.get(i).equals(position) || setLocations.get(i).equals(positionPlusOne)) {
                    checker = false;
                }
            }
            
            
            
            if(checker = true) {
                setLocations.add(position);
                setLocations.add(positionPlusOne);
                placeDestroyer(position,positionPlusOne);
                System.out.println(position+positionPlusOne);
                swag = false;
            }
        }
    }
    
    void setSubmarinePosition() {
        boolean swag = true;
        boolean checker = true;
        while(swag) {
            checker = true;
            Random randomnizer = new Random();
            char letterLocation = myLetters.charAt(randomnizer.nextInt(letters.length));
            
            
            int numberDestroyer = (int)(Math.random()*6);
            String letterLocationDuo = Character.toString(letterLocation);
            String number = Integer.toString(numberDestroyer);
            String numberPlusOne = Integer.toString(numberDestroyer+1);
            String numberPlusTwo = Integer.toString(numberDestroyer+2);
            
            String position = letterLocationDuo+number;
            String positionPlusOne = letterLocationDuo+numberPlusOne;
            String positionPlusTwo = letterLocationDuo+numberPlusTwo;
            for(int i = 0; i <setLocations.size();i++) {
                if(setLocations.get(i).equals(position) || setLocations.get(i).equals(positionPlusOne) || setLocations.get(i).equals(positionPlusTwo)) {
                    checker = false;
                }
            }
            
            
            
            if(checker = true) {
                setLocations.add(position);
                setLocations.add(positionPlusOne);
                setLocations.add(positionPlusTwo);
                System.out.println(position+positionPlusOne+positionPlusTwo);
                placeSubmarine(position,positionPlusTwo);
                swag = false;
            }
        }
    }
    
    void setCruiserPosition() {
        boolean swag = true;
        boolean checker = true;
        while(swag) {
            checker = true;
            Random randomnizer = new Random();
            char letterLocation = myLetters.charAt(randomnizer.nextInt(letters.length));
            
            
            int numberDestroyer = (int)(Math.random()*6);
            String letterLocationDuo = Character.toString(letterLocation);
            String number = Integer.toString(numberDestroyer);
            String numberPlusOne = Integer.toString(numberDestroyer+1);
            String numberPlusTwo = Integer.toString(numberDestroyer+2);
            
            String position = letterLocationDuo+number;
            String positionPlusOne = letterLocationDuo+numberPlusOne;
            String positionPlusTwo = letterLocationDuo+numberPlusTwo;
            for(int i = 0; i <setLocations.size();i++) {
                if(setLocations.get(i).equals(position) || setLocations.get(i).equals(positionPlusOne) || setLocations.get(i).equals(positionPlusTwo)) {
                    checker = false;
                }
            }
            
            
            
            if(checker = true) {
                setLocations.add(position);
                setLocations.add(positionPlusOne);
                setLocations.add(positionPlusTwo);
                System.out.println(position+positionPlusOne+positionPlusTwo);
                placeCruiser(position,positionPlusTwo);
                swag = false;
            }
        }
    }
    
    void setBattleshipPosition() {
        boolean swag = true;
        boolean checker = true;
        while(swag) {
            checker = true;
            Random randomnizer = new Random();
            char letterLocation = myLetters.charAt(randomnizer.nextInt(letters.length));
            
            
            int numberDestroyer = (int)(Math.random()*5);
            String letterLocationDuo = Character.toString(letterLocation);
            String number = Integer.toString(numberDestroyer);
            String numberPlusOne = Integer.toString(numberDestroyer+1);
            String numberPlusTwo = Integer.toString(numberDestroyer+2);
            String numberPlusThree = Integer.toString(numberDestroyer+3);
            
            String position = letterLocationDuo+number;
            String positionPlusOne = letterLocationDuo+numberPlusOne;
            String positionPlusTwo = letterLocationDuo+numberPlusTwo;
            String positionPlusThree = letterLocationDuo+numberPlusThree;
            for(int i = 0; i <setLocations.size();i++) {
                if(setLocations.get(i).equals(position) || setLocations.get(i).equals(positionPlusOne) || setLocations.get(i).equals(positionPlusTwo) || setLocations.get(i).equals(positionPlusThree)) {
                    checker = false;
                }
            }
            
            
            
            if(checker = true) {
                setLocations.add(position);
                setLocations.add(positionPlusOne);
                setLocations.add(positionPlusTwo);
                setLocations.add(positionPlusThree);
                System.out.println(position+positionPlusOne+positionPlusTwo+positionPlusThree);
                placeBattleship(position,positionPlusThree);
                swag = false;
            }
        }
    }
    
    
    void setCarrierPosition() {
        boolean swag = true;
        boolean checker = true;
        while(swag) {
            checker = true;
            Random randomnizer = new Random();
            char letterLocation = myLetters.charAt(randomnizer.nextInt(letters.length));
            
            
            int numberDestroyer = (int)(Math.random()*4);
            String letterLocationDuo = Character.toString(letterLocation);
            String number = Integer.toString(numberDestroyer);
            String numberPlusOne = Integer.toString(numberDestroyer+1);
            String numberPlusTwo = Integer.toString(numberDestroyer+2);
            String numberPlusThree = Integer.toString(numberDestroyer+3);
            String numberPlusFour = Integer.toString(numberDestroyer+4);
            
            String position = letterLocationDuo+number;
            String positionPlusOne = letterLocationDuo+numberPlusOne;
            String positionPlusTwo = letterLocationDuo+numberPlusTwo;
            String positionPlusThree = letterLocationDuo+numberPlusThree;
            String positionPlusFour = letterLocationDuo+numberPlusFour;
            for(int i = 0; i <setLocations.size();i++) {
                if(setLocations.get(i).equals(position) || setLocations.get(i).equals(positionPlusOne) || setLocations.get(i).equals(positionPlusTwo) || setLocations.get(i).equals(positionPlusThree) || setLocations.get(i).equals(positionPlusFour)) {
                    checker = false;
                }
            }
            
            
            
            if(checker = true) {
                setLocations.add(position);
                setLocations.add(positionPlusOne);
                setLocations.add(positionPlusTwo);
                setLocations.add(positionPlusThree);
                setLocations.add(positionPlusFour);
                System.out.println(position+positionPlusOne+positionPlusTwo+positionPlusThree+positionPlusFour);
                placeCarrier(position,positionPlusFour);
                swag = false;
            }
        }
    }
    //The end of the place randomnizer


	void makeMove() {
		Random rand = new Random();
		int random = rand.nextInt(totalProbability);
		int new_probability = totalProbability;
		int x = -1;
		int y = -1;

		outerloop:
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				x = i;
				y = j;
				new_probability -= probability_grid[x][y];
				if (new_probability <= 0) break outerloop;
			}
		}

		System.out.println(totalProbability);
		totalProbability -= probability_grid[x][y];
		probability_grid[x][y] = 0;

		//if on first row
		if (x != 0) {
			probability_grid[x-1][y] -= 2;
			totalProbability -= 2;
		}

		if (x != 7) {
			probability_grid[x+1][y] -= 2;
			totalProbability -= 2;
		}

		if (y != 0) {
			probability_grid[x][y-1] -= 2;
			totalProbability -= 2;
		}

		if (y != 7) {
			probability_grid[x][y+1] -= 2;
			totalProbability -= 2;
		}

		String wasHitSunkOrMiss =
			placeMove(this.letters[x] + String.valueOf(y));

		if (wasHitSunkOrMiss.equals("Hits")) {
			this.grid[x][y] = 1;
			if (match == null) {

			}
		} else if (wasHitSunkOrMiss.equals("Sunk")) {
			this.grid[x][y] = 1;
			match = null;
		} else this.grid[x][y] = 0;

		return;
	}
	////////////////////////////////////// ^^^^^ PUT YOUR CODE ABOVE HERE ^^^^^ //////////////////////////////////////

	Socket socket;
	String[] destroyer, submarine, cruiser, battleship, carrier;

	String dataPassthrough;
	String data;
	BufferedReader br;
	PrintWriter out;
	Boolean moveMade = false;

	public Battleship() {
		this.grid = new int[8][8];
		this.probability_grid = new int[8][8];

		for(int i = 0; i < grid.length; i++)
			for(int j = 0; j < grid[i].length; j++) {
				grid[i][j] = -1;
				probability_grid[i][j] = 8;
				totalProbability = 8 * 8 * 8;
			}

		this.letters = new char[] {'A','B','C','D','E','F','G','H'};

		destroyer = new String[] {"A0", "A0"};
		submarine = new String[] {"A0", "A0"};
		cruiser = new String[] {"A0", "A0"};
		battleship = new String[] {"A0", "A0"};
		carrier = new String[] {"A0", "A0"};
	}

	void connectToServer() {
		try {
			InetAddress addr = InetAddress.getByName(GAME_SERVER);
			socket = new Socket(addr, 23345);
			br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			out = new PrintWriter(socket.getOutputStream(), true);

			out.print(API_KEY);
			out.flush();
			data = br.readLine();
		} catch (Exception e) {
			System.out.println("Error: when connecting to the server...");
			socket = null;
		}

		if (data == null || data.contains("False")) {
			socket = null;
			System.out.println("Invalid API_KEY");
			System.exit(1); // Close Client
		}
	}

	public void gameMain() {
		while(true) {
			try {
				if (this.dataPassthrough == null) {
					this.data = this.br.readLine();
				}
				else {
					this.data = this.dataPassthrough;
					this.dataPassthrough = null;
				}
			} catch (IOException ioe) {
				System.out.println("IOException: in gameMain");
				ioe.printStackTrace();
			}
			if (this.data == null) {
				try { this.socket.close(); }
				catch (IOException e) { System.out.println("Socket Close Error"); }
				return;
			}

			if (data.contains("Welcome")) {
				String[] welcomeMsg = this.data.split(":");
				placeShips(welcomeMsg[1]);
				if (data.contains("Destroyer")) { // Only Place Can Receive Double Message, Pass Through
					this.dataPassthrough = "Destroyer(2):";
				}
			} else if (data.contains("Destroyer")) {
				this.out.print(destroyer[0]);
				this.out.print(destroyer[1]);
				out.flush();
			} else if (data.contains("Submarine")) {
				this.out.print(submarine[0]);
				this.out.print(submarine[1]);
				out.flush();
			} else if (data.contains("Cruiser")) {
				this.out.print(cruiser[0]);
				this.out.print(cruiser[1]);
				out.flush();
			} else if (data.contains("Battleship")) {
				this.out.print(battleship[0]);
				this.out.print(battleship[1]);
				out.flush();
			} else if (data.contains("Carrier")) {
				this.out.print(carrier[0]);
				this.out.print(carrier[1]);
				out.flush();
			} else if (data.contains( "Enter")) {
				this.moveMade = false;
				this.makeMove();
			} else if (data.contains("Error" )) {
				System.out.println("Error: " + data);
                saveLog();
				System.exit(1); // Exit sys when there is an error
			} else if (data.contains("Die" )) {
				System.out.println("Error: Your client was disconnected using the Game Viewer.");
                saveLog();
                System.exit(1); // Close Client
			} else {
				System.out.println("Recieved Unknown Response:" + data);
                saveLog();
                System.exit(1); // Exit sys when there is an unknown response
			}
		}
	}

	void placeDestroyer(String startPos, String endPos) {
		destroyer = new String[] {startPos.toUpperCase(), endPos.toUpperCase()};
	}

	void placeSubmarine(String startPos, String endPos) {
		submarine = new String[] {startPos.toUpperCase(), endPos.toUpperCase()};
	}

	void placeCruiser(String startPos, String endPos) {
		cruiser = new String[] {startPos.toUpperCase(), endPos.toUpperCase()};
	}

	void placeBattleship(String startPos, String endPos) {
		battleship = new String[] {startPos.toUpperCase(), endPos.toUpperCase()};
	}

	void placeCarrier(String startPos, String endPos) {
		carrier = new String[] {startPos.toUpperCase(), endPos.toUpperCase()};
	}

	String placeMove(String pos) {
		if(this.moveMade) { // Check if already made move this turn
			System.out.println("Error: Please Make Only 1 Move Per Turn.");
			System.exit(1); // Close Client
		}
		this.moveMade = true;

		this.out.print(pos);
		out.flush();
		try { data = this.br.readLine(); }
		catch(Exception e) { System.out.println("No response after from the server after place the move"); }

		if (data.contains("Hit")) return "Hit";
		else if (data.contains("Sunk")) return "Sunk";
		else if (data.contains("Miss")) return "Miss";
		else {
			this.dataPassthrough = data;
			return "Miss";
		}
	}

	public static void main(String[] args) {
		Battleship bs = new Battleship();
		while(true) {
			bs.connectToServer();
			if (bs.socket != null) {
                System.out.println("Connected to server");
                bs.gameMain();
            }
		}
	}
}
